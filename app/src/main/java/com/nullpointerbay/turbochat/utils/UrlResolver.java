package com.nullpointerbay.turbochat.utils;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by charafau on 2017/02/20.
 */

public class UrlResolver {

    public Observable<String> getLinkTitle(String url) {

        return Observable.create(subscriber -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            final Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                final String body = response.body().string();

                StringBuilder sb = new StringBuilder();
                sb.append(body, body.indexOf("<title>") + 7, body.lastIndexOf("</title>"));

                subscriber.onNext(sb.toString());
                subscriber.onComplete();
            } else {
                subscriber.onError(new IOException("Error " + response.message()));
            }

        });

    }

}
