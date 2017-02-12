package com.nullpointerbay.turbochat.viewmodel;


import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.repository.TeamRepository;
import com.nullpointerbay.turbochat.utils.UserResolver;

import java.util.List;

import io.reactivex.Single;
import timber.log.Timber;

public class TeamViewModel {

    private final TeamRepository teamRepository;
    private final UserResolver userResolver;

    public TeamViewModel(TeamRepository teamRepository, UserResolver userResolver) {
        this.teamRepository = teamRepository;
        this.userResolver = userResolver;
    }

    public Single<List<Team>> getTeams() {
        Timber.d("user resolver " + userResolver.getLoggedInUser());
        return teamRepository.getTeams(userResolver.getLoggedInUser());
    }

}
