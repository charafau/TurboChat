package com.nullpointerbay.turbochat.spans;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;

import com.nullpointerbay.turbochat.activity.WebViewActivity;

/**
 * Created by charafau on 2017/02/12.
 */

public class CustomLinkSpan extends ClickableSpan {


    private final Context context;
    private final String url;

    public CustomLinkSpan(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    public void onClick(View view) {
        WebViewActivity.start(context, url);
    }
}
