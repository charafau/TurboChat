package com.nullpointerbay.turbochat.parsers;


import org.junit.Test;

import java.util.regex.Matcher;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class LinkParserTest {


    @Test
    public void shouldMatchLinkInMessage() throws Exception {

        String message = "hello, check this htttp://www.google.com qwerty";

        final Matcher linkParser = LinkParser.getLinkParser(message);
        assertTrue(linkParser.find());
    }

    @Test
    public void shouldNotMatchWhenNoLinkInMessage() throws Exception {

        String message = "hello, check this link";

        final Matcher linkParser = LinkParser.getLinkParser(message);
        assertFalse(linkParser.find());

    }
}