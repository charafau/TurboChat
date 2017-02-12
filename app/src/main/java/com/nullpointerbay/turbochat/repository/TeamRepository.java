package com.nullpointerbay.turbochat.repository;

import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.model.User;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by charafau on 2017/02/12.
 */

public interface TeamRepository {

    Single<List<Team>> getTeams(User user);

}
