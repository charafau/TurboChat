package com.nullpointerbay.turbochat.parsers;

import org.junit.Test;

import java.util.regex.Matcher;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;


public class MentionParserTest {

    @Test
    public void shouldMatchMention() throws Exception {

        String message = "hello @alex";

        final Matcher linkParser = MentionParser.getMatcher(message);
        assertTrue(linkParser.find());

    }

    @Test
    public void shouldNotMatchWhenNoMentions() throws Exception {

        String message = "hello alex";

        final Matcher linkParser = MentionParser.getMatcher(message);
        assertFalse(linkParser.find());

    }
}