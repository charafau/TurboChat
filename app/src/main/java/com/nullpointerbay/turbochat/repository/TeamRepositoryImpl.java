package com.nullpointerbay.turbochat.repository;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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

public class TeamRepositoryImpl implements TeamRepository {

    private final NetworkBehavior networkBehavior = NetworkBehavior.create();
    private final MockTeamApiService mockTeamApiService;

    public TeamRepositoryImpl() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://example.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        final MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior).build();

        final BehaviorDelegate<TeamApiService> delegate = mockRetrofit.create(TeamApiService.class);

        mockTeamApiService = new MockTeamApiService(delegate);
    }

    @Override
    public Single<List<Team>> getTeams(User user) {
        return mockTeamApiService.getTeams();
    }
}
