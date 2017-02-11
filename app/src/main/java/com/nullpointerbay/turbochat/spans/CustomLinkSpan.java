package com.nullpointerbay.turbochat.spans;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

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
        Log.d("gowno", "click");
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        context.startActivity(browserIntent);
    }
}