package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.repository.EmojiRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by charafau on 2017/02/15.
 */

public class EmojiViewModel {

    private final EmojiRepository emojiRepository;

    public EmojiViewModel(EmojiRepository emojiRepository) {
        this.emojiRepository = emojiRepository;
    }

    public Single<List<String>> getEmojis(Team team) {
        return emojiRepository.getEmojiList(team.getUuid());
    }
}
