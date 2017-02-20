package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.model.User;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by charafau on 2017/02/21.
 */

public class TeamRepositoryImpl implements TeamRepository {
    @Override
    public Single<List<Team>> getTeams(User user) {
        return null;
    }
}
