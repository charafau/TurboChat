package com.nullpointerbay.turbochat.utils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;


/**
 * This class is only for testing purposes,
 * loading User images from drawable in Mock flavour
 */
public class MockImageLoader implements ImageLoader {

    @Override
    public void loadImage(Context context, String url, ImageView target) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(url, "drawable", context.getPackageName());
        target.setImageResource(resourceId);
    }
}
