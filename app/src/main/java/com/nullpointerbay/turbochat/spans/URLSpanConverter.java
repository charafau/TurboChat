package com.nullpointerbay.turbochat.spans;

import android.content.Context;
import android.text.style.URLSpan;

/**
 * Created by charafau on 2017/02/12.
 */

public class URLSpanConverter implements SpanUtils.SpanConverter<URLSpan, CustomLinkSpan> {

    private final Context context;

    public URLSpanConverter(Context context) {
        this.context = context;
    }

    @Override
    public CustomLinkSpan convert(URLSpan span) {
        final CustomLinkSpan customLinkSpan = new CustomLinkSpan(context, span.getURL());
        return customLinkSpan;
    }
}
