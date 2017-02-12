package com.nullpointerbay.turbochat.di;


import com.nullpointerbay.turbochat.fragment.MessageFragment;
import com.nullpointerbay.turbochat.fragment.TeamFragment;
import com.nullpointerbay.turbochat.viewmodel.MessageViewModel;
import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import dagger.Component;

@ViewScope
@Component(dependencies = TurboChatComponent.class,
        modules = ViewModule.class)
public interface ViewComponent {

    void inject(TeamFragment teamFragment);

    void inject(MessageFragment messageFragment);

    TeamViewModel getTeamViewModel();

    MessageViewModel getMessageViewModel();
}
