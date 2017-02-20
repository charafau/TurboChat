package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.repository.TeamRepository;
import com.nullpointerbay.turbochat.utils.UserResolver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.when;


public class TeamViewModelTest {

    @Mock
    TeamRepository teamRepository;
    @Mock
    UserResolver userResolver;
    private TeamViewModel teamViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        teamViewModel = new TeamViewModel(teamRepository, userResolver);

    }

    private User createMockUser() {
        return new User(1L, "alex", "Alex Smith", "u_alex");

    }

    @Test
    public void shouldReturnTeamsForLoggedInUser() throws Exception {

        final User mockUser = createMockUser();

        List<Team> teams = new ArrayList<>();
        teams.add(new Team("some random uuid", "team A", "photo url"));

        when(userResolver.getLoggedInUser()).thenReturn(mockUser);
        when(teamRepository.getTeams(mockUser)).thenReturn(Single.just(teams));

        final TestSubscriber<List<Team>> testSubscriber = new TestSubscriber<>();
        final Single<List<Team>> listSingle = teamRepository.getTeams(userResolver.getLoggedInUser());


        listSingle.toFlowable().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertResult(teams);

    }


}