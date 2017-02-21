package com.nullpointerbay.turbochat.parsers;

import org.junit.Test;

import java.util.regex.Matcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by charafau on 2017/02/22.
 */
public class EmojiParserTest {

    @Test
    public void shouldMatchEmojiPattern() throws Exception {

        String message = "hello (love) alex";

        final Matcher love = EmojiParser.getEmojiParser(message, "love");
        assertTrue(love.find());
    }

    @Test
    public void shouldNotFindEmojiWhenNotPassed() throws Exception {

        String message = "hello (love) alex";

        final Matcher love = EmojiParser.getEmojiParser(message, "");
        assertFalse(love.find());

    }

    @Test
    public void shouldNotFindWhenNoEmojiInMessage() throws Exception {

        String message = "hello alex";

        final Matcher love = EmojiParser.getEmojiParser(message, "love");
        assertFalse(love.find());

    }
}