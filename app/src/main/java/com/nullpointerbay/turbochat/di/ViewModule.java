package com.nullpointerbay.turbochat.di;

import com.nullpointerbay.turbochat.repository.MessageRepository;
import com.nullpointerbay.turbochat.repository.TeamRepository;
import com.nullpointerbay.turbochat.repository.UserRepository;
import com.nullpointerbay.turbochat.utils.UserResolver;
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
    public MessageViewModel provideMessageViewModel(MessageRepository messageRepository) {
        return new MessageViewModel(messageRepository);
    }

    @Provides
    public ProfileViewModel provideProfileViewModel(UserRepository userRepository) {
        return new ProfileViewModel(userRepository);
    }

}
