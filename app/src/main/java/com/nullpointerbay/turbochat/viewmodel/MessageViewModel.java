package com.nullpointerbay.turbochat.viewmodel;

import android.support.annotation.NonNull;

import com.nullpointerbay.turbochat.cache.MessageCache;
import com.nullpointerbay.turbochat.model.Link;
import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.parsers.EmojiParser;
import com.nullpointerbay.turbochat.parsers.LinkParser;
import com.nullpointerbay.turbochat.parsers.MentionParser;
import com.nullpointerbay.turbochat.repository.MessageRepository;
import com.nullpointerbay.turbochat.utils.UrlResolver;
import com.nullpointerbay.turbochat.utils.UserResolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

import static android.R.attr.id;


public class MessageViewModel {

    private final MessageRepository messageRepository;
    private final MessageCache messageCache;
    private final UserResolver userResolver;
    private final UrlResolver urlResolver;

    private PublishSubject<Message> localMessageStream = PublishSubject.create();
    private PublishSubject<Message> apiSendMessageStream = PublishSubject.create();


    public MessageViewModel(MessageRepository messageRepository, MessageCache messageCache,
                            UserResolver userResolver, UrlResolver urlResolver) {
        this.messageRepository = messageRepository;
        this.messageCache = messageCache;
        this.userResolver = userResolver;
        this.urlResolver = urlResolver;
    }

    public Observable<List<Message>> getMessages() {
        final Observable<List<Message>> network = messageRepository.getMessages()
                .doOnNext(messageCache::writeToCache);

        final Observable<List<Message>> cache = messageCache.getMessages().filter(messages -> !messages.isEmpty());

        return Observable.concat(cache, network);
    }

    public void sendMessage(String message, List<String> pressedEmojis, Scheduler apiMessageSendScheduler) {


        List<String> mentions = findMentions(message);
        List<Link> links = findLinks(message);
        List<String> emojis = findEmojis(message, pressedEmojis);

        Timber.d("mentions %s", mentions.toString());
        Timber.d("links %s", links.toString());
        Timber.d("emojis %s", emojis.toString());

        final User loggedInUser = userResolver.getLoggedInUser();

        final Message m = new Message(id, message, mentions,
                emojis, links, loggedInUser);

        localMessageStream.onNext(m);

        Observable.just(links)
                .subscribeOn(apiMessageSendScheduler)
                .flatMapIterable(link -> link)
                .map(link -> urlResolver.getLinkTitle(link.getUrl()))
                .toList()
                .map(lnks -> new Message(id, message, mentions, emojis, lnks, loggedInUser))
                .flatMap(messageRepository::sendMessage)
                .subscribe(serverMessage -> {
                            messageCache.addMessage(serverMessage);
                            apiSendMessageStream.onNext(serverMessage);
                        },
                        throwable -> apiSendMessageStream.onError(throwable));

    }

    List<String> findEmojis(String message, List<String> pressedEmojis) {

        final Set<String> emojis = new HashSet<>();

        for (String addedEmoji : pressedEmojis) {
            final Matcher matcher = EmojiParser.getEmojiParser(message, addedEmoji);
            while (matcher.find()) {
                emojis.add(message.substring(matcher.start() + 1, matcher.end() - 1));
            }
        }

        return new ArrayList<>(emojis);
    }

    List<Link> findLinks(String message) {

        final List<Link> links = new ArrayList<>();

        final Matcher matcher = LinkParser.getLinkParser(message);
        while (matcher.find()) {
            links.add(new Link(message.substring(matcher.start(), matcher.end()), ""));
        }

        return links;
    }

    @NonNull
    List<String> findMentions(String message) {
        final List<String> mentions = new ArrayList<>();

        final Matcher matcher = MentionParser.getMatcher(message);
        while (matcher.find()) {
            mentions.add(message.substring(matcher.start() + 1, matcher.end()));
        }

        return mentions;
    }

    public Observable<Message> localMessageStream() {
        return localMessageStream;
    }

    public Observable<Message> apiSendMessageStream() {
        return apiSendMessageStream;
    }
}
