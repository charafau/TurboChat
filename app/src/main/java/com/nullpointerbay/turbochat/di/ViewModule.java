package com.nullpointerbay.turbochat.di;

import com.nullpointerbay.turbochat.cache.MessageCache;
import com.nullpointerbay.turbochat.repository.EmojiRepository;
import com.nullpointerbay.turbochat.repository.MessageRepository;
import com.nullpointerbay.turbochat.repository.TeamRepository;
import com.nullpointerbay.turbochat.repository.UserRepository;
import com.nullpointerbay.turbochat.utils.UserResolver;
import com.nullpointerbay.turbochat.viewmodel.EmojiViewModel;
import com.nullpointerbay.turbochat.viewmodel.MessageViewModel;
import com.nullpointerbay.turbochat.viewmodel.ProfileViewModel;
import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {


    @Provides
    public TeamViewModel provideTeamViewModel(TeamRepository teamRepository, UserResolver userResolver) {
        return new TeamViewModel(teamRepository, userResolver);
    }

    @Provides
    public MessageViewModel provideMessageViewModel(MessageRepository messageRepository, MessageCache messageCache) {
        return new MessageViewModel(messageRepository, messageCache);
    }

    @Provides
    public ProfileViewModel provideProfileViewModel(UserRepository userRepository) {
        return new ProfileViewModel(userRepository);
    }

    @Provides
    public EmojiViewModel provideEmojiViewModel(EmojiRepository emojiRepository) {
        return new EmojiViewModel(emojiRepository);
    }

}
