package com.nullpointerbay.turbochat.parsers;

import android.content.Context;
import android.text.SpannableString;
import android.util.Patterns;

import com.nullpointerbay.turbochat.spans.MentionLinkSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by charafau on 2017/02/12.
 */

public class LinkParser implements ItemParser {

    private final Context context;

    public LinkParser(Context context) {
        this.context = context;
    }


    @Override
    public void insert(SpannableString message) {
        final Pattern pattern = Patterns.WEB_URL;
        final Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            final MentionLinkSpan mentionLinkSpan = new MentionLinkSpan(context, matcher.group());
            message.setSpan(mentionLinkSpan, matcher.start(), matcher.end(), 0);
        }
    }
}
