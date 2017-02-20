package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.cache.MessageCache;
import com.nullpointerbay.turbochat.model.Link;
import com.nullpointerbay.turbochat.repository.MessageRepository;
import com.nullpointerbay.turbochat.utils.UrlResolver;
import com.nullpointerbay.turbochat.utils.UserResolver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class MessageViewModelTest {

    @Mock
    MessageRepository messageRepository;
    @Mock
    MessageCache messageCache;
    @Mock
    UserResolver userResolver;
    @Mock
    UrlResolver urlResolver;
    private MessageViewModel messageViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        messageViewModel = new MessageViewModel(messageRepository, messageCache, userResolver, urlResolver);
    }

    @Test
    public void shouldFindLinksInMessageAndPutEmptyTitle() throws Exception {
        String message = "hello @alex http://youtube.com/q=look there you go http://twitter.com";

        final List<Link> links = messageViewModel.findLinks(message);

        final List<Link> expectedLinks = Arrays.asList(
                new Link("http://youtube.com/q=look", ""),
                new Link("http://twitter.com", ""));

        assertEquals(expectedLinks, links);

    }
}