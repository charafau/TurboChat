package com.nullpointerbay.turbochat.service;

import com.nullpointerbay.turbochat.model.Team;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface TeamApiService{

    @GET("/teams")
    Single<List<Team>> getTeams();

}
