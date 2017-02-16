package com.nullpointerbay.turbochat.parsers;

import android.content.Context;
import android.support.annotation.NonNull;
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

    @NonNull
    public static Matcher getMatcher(String message) {
        Pattern pattern = Pattern.compile("@([A-Za-z0-9_-]+)");
        return pattern.matcher(message);
    }

    @Override
    public void insert(SpannableString message) {
        final Matcher matcher = getMatcher(message.toString());
        while (matcher.find()) {
            final MentionLinkSpan mentionLinkSpan = new MentionLinkSpan(context, matcher.group());
            message.setSpan(mentionLinkSpan, matcher.start(), matcher.end(), 0);
        }
    }
}
