package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.service.MockTeamApiService;
import com.nullpointerbay.turbochat.service.TeamApiService;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class MockTeamRepositoryImpl implements TeamRepository {

    private final NetworkBehavior networkBehavior;
    private final MockTeamApiService mockTeamApiService;

    public MockTeamRepositoryImpl(Retrofit retrofit, MockRetrofit mockRetrofit, NetworkBehavior networkBehavior) {
        this.networkBehavior = networkBehavior;
        final BehaviorDelegate<TeamApiService> delegate = mockRetrofit.create(TeamApiService.class);

        mockTeamApiService = new MockTeamApiService(delegate);
    }

    @Override
    public Single<List<Team>> getTeams(User user) {
        return mockTeamApiService.getTeams();
    }
}
