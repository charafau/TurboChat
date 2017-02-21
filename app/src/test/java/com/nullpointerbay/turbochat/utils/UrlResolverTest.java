package com.nullpointerbay.turbochat.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import okhttp3.OkHttpClient;

import static org.junit.Assert.assertEquals;


public class UrlResolverTest {

    String html = "<!doctype html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "<title>Website Title</title>\n" +
            "<meta name=\"description\" content=\"The HTML5 Herald\">\n" +
            "</head>\n" +
            "<body>\n" +
            "body here\n" +
            "</body>\n" +
            "</html>";


    @Mock
    OkHttpClient okHttpClient;
    private UrlResolver urlResolver;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        urlResolver = new UrlResolver(okHttpClient);
    }

    @Test
    public void shouldFetchTitleFromHtml() throws Exception {
        final StringBuilder sb = urlResolver.parseTitleFromHtml(html);

        assertEquals("Website Title", sb.toString());

    }
}