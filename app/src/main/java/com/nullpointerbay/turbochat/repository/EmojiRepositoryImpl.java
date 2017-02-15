package com.nullpointerbay.turbochat.repository;

import android.content.Context;

import com.nullpointerbay.turbochat.R;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;

public class EmojiRepositoryImpl implements EmojiRepository {

    public static final String EMOJI_PREFIX = "emoji_";
    private final Context context;

    public EmojiRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Single<List<String>> getEmojiList(String teamUuid) {

        final String[] stringArray = context.getResources().getStringArray(R.array.emoji_name_array);
        for (int i = 0; i < stringArray.length; i++) {
            String s = stringArray[i];
            stringArray[i] = EMOJI_PREFIX + s;
        }

        return Single.just(Arrays.asList(stringArray));
    }
}
