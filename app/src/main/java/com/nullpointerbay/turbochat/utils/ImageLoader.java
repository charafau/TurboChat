package com.nullpointerbay.turbochat.utils;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by charafau on 2017/02/12.
 */

public interface ImageLoader {

    void loadImage(Context context, String url, ImageView target);

    void loadImageWithCircleTransformation(Context context, String url, ImageView target);

}
