package com.nullpointerbay.turbochat.service;

import com.nullpointerbay.turbochat.model.Message;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by charafau on 2017/02/12.
 */

public interface MessageApiService {
    @GET("/messages")
    Observable<List<Message>> getMessages();
}
