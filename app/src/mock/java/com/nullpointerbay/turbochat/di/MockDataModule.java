package com.nullpointerbay.turbochat.di;

import com.nullpointerbay.turbochat.repository.TeamRepository;
import com.nullpointerbay.turbochat.repository.TeamRepositoryImpl;
import com.nullpointerbay.turbochat.utils.MockUserResolverImpl;
import com.nullpointerbay.turbochat.utils.UserResolver;

import dagger.Module;
import dagger.Provides;

@Module
public class MockDataModule {

    @Provides
    public UserResolver provideMockUserResolver() {
        return new MockUserResolverImpl();
    }

    @Provides
    public TeamRepository provideMockTeamRepository() {
        return new TeamRepositoryImpl();
    }

}
