package com.nullpointerbay.turbochat.parsers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;

import com.nullpointerbay.turbochat.spans.CustomLinkSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LinkParser implements ItemParser {

    private final Context context;

    public LinkParser(Context context) {
        this.context = context;
    }

    @NonNull
    public static Matcher getLinkParser(String message) {
        final Pattern pattern = Pattern.compile("(((?:(?i:http|https|rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?(?:(([a-zA-Z0-9[ -\uD7FF豈-\uFDCFﷰ-\uFFEF\uD800\uDC00-\uD83F\uDFFD\uD840\uDC00-\uD87F\uDFFD\uD880\uDC00-\uD8BF\uDFFD\uD8C0\uDC00-\uD8FF\uDFFD\uD900\uDC00-\uD93F\uDFFD\uD940\uDC00-\uD97F\uDFFD\uD980\uDC00-\uD9BF\uDFFD\uD9C0\uDC00-\uD9FF\uDFFD\uDA00\uDC00-\uDA3F\uDFFD\uDA40\uDC00-\uDA7F\uDFFD\uDA80\uDC00-\uDABF\uDFFD\uDAC0\uDC00-\uDAFF\uDFFD\uDB00\uDC00-\uDB3F\uDFFD\uDB44\uDC00-\uDB7F\uDFFD&&[^ [ - ]\u2028\u2029 　]]](?:[a-zA-Z0-9[ -\uD7FF豈-\uFDCFﷰ-\uFFEF\uD800\uDC00-\uD83F\uDFFD\uD840\uDC00-\uD87F\uDFFD\uD880\uDC00-\uD8BF\uDFFD\uD8C0\uDC00-\uD8FF\uDFFD\uD900\uDC00-\uD93F\uDFFD\uD940\uDC00-\uD97F\uDFFD\uD980\uDC00-\uD9BF\uDFFD\uD9C0\uDC00-\uD9FF\uDFFD\uDA00\uDC00-\uDA3F\uDFFD\uDA40\uDC00-\uDA7F\uDFFD\uDA80\uDC00-\uDABF\uDFFD\uDAC0\uDC00-\uDAFF\uDFFD\uDB00\uDC00-\uDB3F\uDFFD\uDB44\uDC00-\uDB7F\uDFFD&&[^ [ - ]\u2028\u2029 　]]\\-]{0,61}[a-zA-Z0-9[ -\uD7FF豈-\uFDCFﷰ-\uFFEF\uD800\uDC00-\uD83F\uDFFD\uD840\uDC00-\uD87F\uDFFD\uD880\uDC00-\uD8BF\uDFFD\uD8C0\uDC00-\uD8FF\uDFFD\uD900\uDC00-\uD93F\uDFFD\uD940\uDC00-\uD97F\uDFFD\uD980\uDC00-\uD9BF\uDFFD\uD9C0\uDC00-\uD9FF\uDFFD\uDA00\uDC00-\uDA3F\uDFFD\uDA40\uDC00-\uDA7F\uDFFD\uDA80\uDC00-\uDABF\uDFFD\uDAC0\uDC00-\uDAFF\uDFFD\uDB00\uDC00-\uDB3F\uDFFD\uDB44\uDC00-\uDB7F\uDFFD&&[^ [ - ]\u2028\u2029 　]]]){0,1}\\.)+(xn\\-\\-[\\w\\-]{0,58}\\w|[a-zA-Z[ -\uD7FF豈-\uFDCFﷰ-\uFFEF\uD800\uDC00-\uD83F\uDFFD\uD840\uDC00-\uD87F\uDFFD\uD880\uDC00-\uD8BF\uDFFD\uD8C0\uDC00-\uD8FF\uDFFD\uD900\uDC00-\uD93F\uDFFD\uD940\uDC00-\uD97F\uDFFD\uD980\uDC00-\uD9BF\uDFFD\uD9C0\uDC00-\uD9FF\uDFFD\uDA00\uDC00-\uDA3F\uDFFD\uDA40\uDC00-\uDA7F\uDFFD\uDA80\uDC00-\uDABF\uDFFD\uDAC0\uDC00-\uDAFF\uDFFD\uDB00\uDC00-\uDB3F\uDFFD\uDB44\uDC00-\uDB7F\uDFFD&&[^ [ - ]\u2028\u2029 　]]]{2,63})|((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9]))))(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9[ -\uD7FF豈-\uFDCFﷰ-\uFFEF\uD800\uDC00-\uD83F\uDFFD\uD840\uDC00-\uD87F\uDFFD\uD880\uDC00-\uD8BF\uDFFD\uD8C0\uDC00-\uD8FF\uDFFD\uD900\uDC00-\uD93F\uDFFD\uD940\uDC00-\uD97F\uDFFD\uD980\uDC00-\uD9BF\uDFFD\uD9C0\uDC00-\uD9FF\uDFFD\uDA00\uDC00-\uDA3F\uDFFD\uDA40\uDC00-\uDA7F\uDFFD\uDA80\uDC00-\uDABF\uDFFD\uDAC0\uDC00-\uDAFF\uDFFD\uDB00\uDC00-\uDB3F\uDFFD\uDB44\uDC00-\uDB7F\uDFFD&&[^ [ - ]\u2028\u2029 　]]\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$|^))");
        return pattern.matcher(message);
    }

    @Override
    public void insert(SpannableString message) {
        final Matcher matcher = getLinkParser(message.toString());
        while (matcher.find()) {
            final CustomLinkSpan customLinkSpan = new CustomLinkSpan(context, matcher.group());
            message.setSpan(customLinkSpan, matcher.start(), matcher.end(), 0);
        }
    }
}
