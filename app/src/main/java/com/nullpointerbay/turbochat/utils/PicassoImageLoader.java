package com.nullpointerbay.turbochat.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by charafau on 2017/02/12.
 */

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void loadImage(Context context, String url, ImageView target) {
        Picasso.with(context).load(url).into(target);
    }

    @Override
    public void loadImageWithCircleTransformation(Context context, String url, ImageView target) {
        Picasso.with(context).load(url).transform(new CircleTransform()).into(target);

    }
}
