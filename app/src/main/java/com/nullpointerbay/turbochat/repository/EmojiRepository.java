package com.nullpointerbay.turbochat.repository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by charafau on 2017/02/15.
 */

public interface EmojiRepository {

    Single<List<String>> getEmojiList(String teamUuid);

}
