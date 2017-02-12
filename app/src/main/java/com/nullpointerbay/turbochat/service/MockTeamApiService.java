package com.nullpointerbay.turbochat.service;

import com.nullpointerbay.turbochat.model.Team;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import io.reactivex.Single;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by charafau on 2017/02/12.
 */

public class MockTeamApiService implements TeamApiService {

    private final BehaviorDelegate<TeamApiService> delegate;

    public MockTeamApiService(BehaviorDelegate<TeamApiService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Single<List<Team>> getTeams() {
        final List<Team> teams = Arrays.asList(
                new Team(UUID.randomUUID().toString(), "Java developer team", ""),
                new Team(UUID.randomUUID().toString(), "Android developer team", ""),
                new Team(UUID.randomUUID().toString(), "Kotlin developer team", "")
        );

        return delegate.returningResponse(teams).getTeams();
    }
}
