package com.nullpointerbay.turbochat.viewmodel;

import android.support.annotation.NonNull;

import com.nullpointerbay.turbochat.model.Link;
import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.parsers.EmojiParser;
import com.nullpointerbay.turbochat.parsers.LinkParser;
import com.nullpointerbay.turbochat.parsers.MentionParser;
import com.nullpointerbay.turbochat.repository.MessageRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

import static android.R.attr.id;


public class MessageViewModel {

    private final MessageRepository messageRepository;

    PublishSubject<Message> messagePublishSubject = PublishSubject.create();

    public MessageViewModel(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Single<List<Message>> getMessages() {
        return messageRepository.getMessages();
    }

    public void sendMessage(String message, List<String> pressedEmojis) {


        List<String> mentions = findMentions(message);
        List<Link> links = findLinks(message);
        List<String> emojis = findEmojis(message, pressedEmojis);

        Timber.d("mentions %s", mentions.toString());
        Timber.d("links %s", links.toString());
        Timber.d("emojis %s", emojis.toString());

        final User userYui = new User(3L, "yui", "Yui Kanazawa", "u_yui");

        final Message m = new Message(id, message, mentions,
                emojis, links, userYui);

        messagePublishSubject.onNext(m);

    }

    private List<String> findEmojis(String message, List<String> pressedEmojis) {

        final Set<String> emojis = new HashSet<>();

        for (String addedEmoji : pressedEmojis) {
            final Matcher matcher = EmojiParser.getEmojiParser(message.toString(), addedEmoji);
            while (matcher.find()) {
                emojis.add(message.substring(matcher.start() + 1, matcher.end() - 1));
            }
        }

        return new ArrayList<>(emojis);
    }

    private List<Link> findLinks(String message) {

        final List<Link> links = new ArrayList<>();

        final Matcher matcher = LinkParser.getLinkParser(message.toString());
        while (matcher.find()) {
            links.add(new Link(message.substring(matcher.start(), matcher.end()), ""));
        }

        return links;
    }

    @NonNull
    private List<String> findMentions(String message) {
        final List<String> mentions = new ArrayList<>();

        final Matcher matcher = MentionParser.getMatcher(message);
        while (matcher.find()) {
            mentions.add(message.substring(matcher.start(), matcher.end()));
        }

        return mentions;
    }

    public Observable<Message> messageStream() {
        return messagePublishSubject;
    }
}
