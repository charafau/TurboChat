package com.nullpointerbay.turbochat.utils;

import android.support.annotation.NonNull;

import com.nullpointerbay.turbochat.model.Link;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;


public class UrlResolver {

    private final OkHttpClient okHttpClient;

    public UrlResolver(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Link getLinkTitle(String url) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            final Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                final String body = response.body().string();

                StringBuilder sb = parseTitleFromHtml(body);
                return new Link(url, sb.toString());

            } else {
                return new Link(url, "");
            }
        } catch (IOException e) {
            Timber.e(e);
        }

        return new Link(url, "");

    }

    @NonNull
    StringBuilder parseTitleFromHtml(String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(body, body.indexOf("<title>") + 7, body.lastIndexOf("</title>"));
        return sb;
    }

}
