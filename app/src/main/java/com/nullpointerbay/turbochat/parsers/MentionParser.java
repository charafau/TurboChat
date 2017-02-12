package com.nullpointerbay.turbochat.parsers;

import android.content.Context;
import android.text.SpannableString;

import com.nullpointerbay.turbochat.spans.MentionLinkSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by charafau on 2017/02/12.
 */

public class MentionParser implements ItemParser {

    private final Context context;

    public MentionParser(Context context) {
        this.context = context;
    }

    @Override
    public void insert(SpannableString message) {
        Pattern pattern = Pattern.compile("@([A-Za-z0-9_-]+)");
        final Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            final MentionLinkSpan mentionLinkSpan = new MentionLinkSpan(context, matcher.group());
            message.setSpan(mentionLinkSpan, matcher.start(), matcher.end(), 0);
        }
    }
}
