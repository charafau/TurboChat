package com.nullpointerbay.turbochat.spans;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by charafau on 2017/02/12.
 */

public class MentionLinkSpan extends ClickableSpan {

    private final Context context;
    private final String mentionUser;

    public MentionLinkSpan(Context context, String mentionUser) {
        this.context = context;
        this.mentionUser = mentionUser;
    }


    @Override
    public void onClick(View view) {
        //TODO: open profile activity
    }
}
