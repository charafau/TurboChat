package com.nullpointerbay.turbochat.di;


import com.nullpointerbay.turbochat.fragment.TeamFragment;
import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import dagger.Component;

@ViewScope
@Component(dependencies = TurboChatComponent.class,
        modules = ViewModule.class)
public interface ViewComponent {

    void inject(TeamFragment teamFragment);

    TeamViewModel getTeamViewModel();
}
