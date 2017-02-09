package com.nullpointerbay.turbochat.di;

import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {

    @Provides
    public TeamViewModel provideTeamViewModel() {
        return new TeamViewModel();
    }

}
