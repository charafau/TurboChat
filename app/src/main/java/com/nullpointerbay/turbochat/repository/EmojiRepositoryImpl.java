package com.nullpointerbay.turbochat.repository;

import android.content.Context;

import com.nullpointerbay.turbochat.R;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;

public class EmojiRepositoryImpl implements EmojiRepository {

    private final Context context;

    public EmojiRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Single<List<String>> getEmojiList(String teamUuid) {

        final String[] stringArray = context.getResources().getStringArray(R.array.emoji_name_array);

        return Single.just(Arrays.asList(stringArray));
    }
}
