package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.Message;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by charafau on 2017/02/12.
 */

public interface MessageRepository {
    Single<List<Message>> getMessages();
}
