package com.nullpointerbay.turbochat.di;

import com.nullpointerbay.turbochat.repository.MessageRepository;
import com.nullpointerbay.turbochat.repository.MessageRepositoryImpl;
import com.nullpointerbay.turbochat.repository.TeamRepository;
import com.nullpointerbay.turbochat.repository.TeamRepositoryImpl;
import com.nullpointerbay.turbochat.utils.ImageLoader;
import com.nullpointerbay.turbochat.utils.PicassoImageLoader;
import com.nullpointerbay.turbochat.utils.UserResolver;
import com.nullpointerbay.turbochat.utils.UserResolverImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    public UserResolver provideUserResolver() {
        return new UserResolverImpl();
    }

    @Provides
    public TeamRepository provideTeamRepository() {
        return new TeamRepositoryImpl();
    }

    @Provides
    public MessageRepository provideMessageRepository() {
        return new MessageRepositoryImpl();
    }

    @Provides
    public ImageLoader provideImageLoader() {
        return new PicassoImageLoader();
    }

}
