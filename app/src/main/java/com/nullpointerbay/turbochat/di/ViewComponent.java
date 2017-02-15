package com.nullpointerbay.turbochat.di;


import com.nullpointerbay.turbochat.fragment.EmojiFragment;
import com.nullpointerbay.turbochat.fragment.MessageFragment;
import com.nullpointerbay.turbochat.fragment.ProfileFragment;
import com.nullpointerbay.turbochat.fragment.TeamFragment;
import com.nullpointerbay.turbochat.viewmodel.EmojiViewModel;
import com.nullpointerbay.turbochat.viewmodel.MessageViewModel;
import com.nullpointerbay.turbochat.viewmodel.ProfileViewModel;
import com.nullpointerbay.turbochat.viewmodel.TeamViewModel;

import dagger.Component;

@ViewScope
@Component(dependencies = TurboChatComponent.class,
        modules = ViewModule.class)
public interface ViewComponent {

    void inject(TeamFragment teamFragment);

    void inject(MessageFragment messageFragment);

    void inject(ProfileFragment profileFragment);

    void inject(EmojiFragment emojiFragment);

    TeamViewModel getTeamViewModel();

    MessageViewModel getMessageViewModel();

    ProfileViewModel getProfileViewModel();

    EmojiViewModel getEmojiViewModel();
}
