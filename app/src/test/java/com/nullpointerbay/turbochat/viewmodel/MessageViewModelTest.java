package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.cache.MessageCache;
import com.nullpointerbay.turbochat.model.Link;
import com.nullpointerbay.turbochat.model.Message;
import com.nullpointerbay.turbochat.repository.MessageRepository;
import com.nullpointerbay.turbochat.testutils.TestUtils;
import com.nullpointerbay.turbochat.utils.UrlResolver;
import com.nullpointerbay.turbochat.utils.UserResolver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

import static android.R.attr.id;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MessageViewModelTest {

    @Mock
    MessageRepository messageRepository;
    @Mock
    MessageCache messageCache;
    @Mock
    UserResolver userResolver;
    @Mock
    UrlResolver urlResolver;
    Scheduler sendScheduler;
    private MessageViewModel messageViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        messageViewModel = new MessageViewModel(messageRepository, messageCache, userResolver, urlResolver);
        sendScheduler = Schedulers.trampoline();
    }

    @Test
    public void shouldFindLinksInMessageAndPutEmptyTitle() throws Exception {
        String message = "hello @alex http://youtube.com/q=look there @yui you go http://twitter.com";

        final List<Link> links = messageViewModel.findLinks(message);

        final List<Link> expectedLinks = asList(
                new Link("http://youtube.com/q=look", ""),
                new Link("http://twitter.com", ""));

        assertEquals(expectedLinks, links);

    }

    @Test
    public void shouldFindMentions() throws Exception {
        String message = "hello @alex http://youtube.com/q=look there @yui you go http://twitter.com";

        final List<String> mentions = messageViewModel.findMentions(message);

        assertEquals(asList("alex", "yui"), mentions);
    }

    @Test
    public void shouldFindEmojis() throws Exception {

        String message = "hello (love) (coffee) there @yui you (hello) go http://twitter.com";

        final List<String> actualEmojis = messageViewModel.findEmojis(message,
                asList("love", "coffee", "hello"));

        assertEquals(asList("love", "coffee", "hello"), actualEmojis);

    }

    @Test
    public void shouldReturnValuesFromNonEmptyList() throws Exception {

        final TestScheduler testScheduler = new TestScheduler();
        List<Message> emptyList = Collections.emptyList();

        when(messageCache.getMessages()).thenReturn(Observable.just(emptyList));

        List<Message> apiMessages = new ArrayList<>();
        apiMessages.add(TestUtils.createMessage());

        final Observable<List<Message>> apiObservable = Observable
                .fromArray(apiMessages).delay(10, TimeUnit.SECONDS, testScheduler);

        when(messageRepository.getMessages()).thenReturn(apiObservable);

        final TestSubscriber<List<Message>> userTestSubscriber = new TestSubscriber<>();
        final Observable<List<Message>> messageViewModelMessages = messageViewModel.getMessages();


        messageViewModelMessages.toFlowable(BackpressureStrategy.LATEST)
                .subscribeOn(testScheduler).subscribe(userTestSubscriber);

        userTestSubscriber.assertNoValues();
        userTestSubscriber.assertNotComplete();


        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        userTestSubscriber.assertNoErrors();
        userTestSubscriber.assertValueCount(0);
        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);
        userTestSubscriber.assertComplete();
        userTestSubscriber.assertValueCount(1);
        userTestSubscriber.assertResult(apiMessages);
    }

    @Test
    public void shouldGetDataFromCacheWhenNotEmpty() throws Exception {

        List<Message> cacheList = new ArrayList<>();
        cacheList.add(TestUtils.createMessage());

        when(messageCache.getMessages()).thenReturn(Observable.just(cacheList));

        List<Message> apiMessages = new ArrayList<>();
        apiMessages.add(TestUtils.createMessage());

        final Observable<List<Message>> apiObservable = Observable.just(apiMessages);

        when(messageRepository.getMessages()).thenReturn(apiObservable);

        final TestSubscriber<List<Message>> userTestSubscriber = new TestSubscriber<>();
        final Observable<List<Message>> messageViewModelMessages = messageViewModel.getMessages();


        messageViewModelMessages.toFlowable(BackpressureStrategy.LATEST).subscribe(userTestSubscriber);

        userTestSubscriber.assertNoErrors();
        userTestSubscriber.assertValueCount(2);
        userTestSubscriber.assertValues(cacheList, apiMessages);
    }

    @Test
    public void shouldPassValuesFromApiToCache() throws Exception {

        List<Message> cacheList = new ArrayList<>();

        when(messageCache.getMessages()).thenReturn(Observable.just(cacheList));

        List<Message> apiMessages = new ArrayList<>();
        apiMessages.add(TestUtils.createMessage());

        final Observable<List<Message>> apiObservable = Observable.just(apiMessages);

        when(messageRepository.getMessages()).thenReturn(apiObservable);

        final TestSubscriber<List<Message>> userTestSubscriber = new TestSubscriber<>();
        final Observable<List<Message>> messageViewModelMessages = messageViewModel.getMessages();

        messageViewModelMessages.toFlowable(BackpressureStrategy.LATEST).subscribe(userTestSubscriber);

        verify(messageCache).writeToCache(apiMessages);

    }

    @Test
    public void shouldReturnLocalStream() throws Exception {

        final TestSubscriber<Message> userTestSubscriber = new TestSubscriber<>();
        final Observable<Message> messageViewModelMessages = messageViewModel.localMessageStream();
        messageViewModelMessages.toFlowable(BackpressureStrategy.LATEST).subscribe(userTestSubscriber);

        userTestSubscriber.assertNoErrors();
        userTestSubscriber.assertNoValues();

    }

    @Test
    public void shouldReturnApiStream() throws Exception {

        final TestSubscriber<Message> userTestSubscriber = new TestSubscriber<>();
        final Observable<Message> messageViewModelMessages = messageViewModel.apiSendMessageStream();
        messageViewModelMessages.toFlowable(BackpressureStrategy.LATEST).subscribe(userTestSubscriber);

        userTestSubscriber.assertNoErrors();
        userTestSubscriber.assertNoValues();

    }

    @Test
    public void shouldPassMessageToLocalStreamWhenSendMessage() throws Exception {

        String message = "hello @alex http://youtube.com/q=look (love) there @yui you go http://twitter.com";

        final List<Link> expectedLinks = asList(
                new Link("http://youtube.com/q=look", ""),
                new Link("http://twitter.com", ""));

        when(userResolver.getLoggedInUser()).thenReturn(TestUtils.createMockUser());

        final TestSubscriber<Message> userTestSubscriber = new TestSubscriber<>();
        final Observable<Message> messageViewModelMessages = messageViewModel.localMessageStream();
        messageViewModelMessages.toFlowable(BackpressureStrategy.LATEST).subscribe(userTestSubscriber);
        final Message m = new Message(id, message, Arrays.asList("alex", "yui"),
                Arrays.asList("love"), expectedLinks, TestUtils.createMockUser());

        messageViewModel.sendMessage(message, Arrays.asList("love"), sendScheduler);
        userTestSubscriber.assertValue(m);

    }

    @Test
    public void shouldDownloadLinkTitle() throws Exception {

        String message = "hello @alex http://youtube.com/q=look (love) there @yui you go http://twitter.com";

        when(userResolver.getLoggedInUser()).thenReturn(TestUtils.createMockUser());
        when(urlResolver.getLinkTitle("http://youtube.com/q=look")).thenReturn(new Link("http://youtube.com/q=look", "youtube"));
        when(urlResolver.getLinkTitle("http://twitter.com")).thenReturn(new Link("http://twitter.com", "twitter"));

        messageViewModel.sendMessage(message, Arrays.asList("love"), sendScheduler);


        final List<Link> expectedLinks = asList(
                new Link("http://youtube.com/q=look", "youtube"),
                new Link("http://twitter.com", "twitter"));

        final Message outputMessage = new Message(id, message, Arrays.asList("alex", "yui"),
                Arrays.asList("love"), expectedLinks, TestUtils.createMockUser());

        verify(messageRepository).sendMessage(outputMessage);

    }

    @Test
    public void shouldPassMessageToCacheAndApiStream() throws Exception {


        String message = "hello @alex http://youtube.com/q=look (love) there @yui you go http://twitter.com";

        when(userResolver.getLoggedInUser()).thenReturn(TestUtils.createMockUser());
        when(urlResolver.getLinkTitle("http://youtube.com/q=look")).thenReturn(new Link("http://youtube.com/q=look", "youtube"));
        when(urlResolver.getLinkTitle("http://twitter.com")).thenReturn(new Link("http://twitter.com", "twitter"));


        final TestSubscriber<Message> userTestSubscriber = new TestSubscriber<>();
        final Observable<Message> messageViewModelMessages = messageViewModel.apiSendMessageStream();
        messageViewModelMessages.toFlowable(BackpressureStrategy.LATEST).subscribe(userTestSubscriber);

        final List<Link> expectedLinks = asList(
                new Link("http://youtube.com/q=look", "youtube"),
                new Link("http://twitter.com", "twitter"));
        final Message outputMessage = new Message(id, message, Arrays.asList("alex", "yui"),
                Arrays.asList("love"), expectedLinks, TestUtils.createMockUser());

        when(messageRepository.sendMessage(outputMessage)).thenReturn(Single.just(outputMessage));

        messageViewModel.sendMessage(message, Arrays.asList("love"), sendScheduler);

        userTestSubscriber.assertValue(outputMessage);
        verify(messageCache).addMessage(outputMessage);

    }
}