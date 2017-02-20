package com.nullpointerbay.turbochat.utils;

import com.nullpointerbay.turbochat.model.Link;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by charafau on 2017/02/20.
 */

public class UrlResolver {

    public Link getLinkTitle(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();


        try {
            final Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                final String body = response.body().string();

                StringBuilder sb = new StringBuilder();
                sb.append(body, body.indexOf("<title>") + 7, body.lastIndexOf("</title>"));
                return new Link(url, sb.toString());

            } else {
                return new Link(url, "");
            }
        } catch (IOException e) {
            Timber.e(e);
        }

        return new Link(url, "");

    }

}
