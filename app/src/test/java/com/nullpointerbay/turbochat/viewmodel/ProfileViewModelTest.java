package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.model.User;
import com.nullpointerbay.turbochat.repository.UserRepository;
import com.nullpointerbay.turbochat.testutils.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class ProfileViewModelTest {

    @Mock
    UserRepository userRepository;
    private ProfileViewModel profileViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        profileViewModel = new ProfileViewModel(userRepository);
    }



    @Test
    public void shouldReturnUserWhenPassUserNick() throws Exception {

        final User user = TestUtils.createMockUser();

        when(userRepository.getUser(user.getNick())).thenReturn(Flowable.just(user));

        final TestSubscriber<User> userTestSubscriber = new TestSubscriber<>();
        final Flowable<User> userByNick = profileViewModel.getUserByNick(user.getNick());
        userByNick.subscribe(userTestSubscriber);

        userTestSubscriber.assertNoErrors();
        userTestSubscriber.assertResult(user);

    }

    @Test
    public void shouldReturnEmptyWhenUserIsNotFound() throws Exception {

        when(userRepository.getUser(anyString())).thenReturn(Flowable.empty());

        final TestSubscriber<User> userTestSubscriber = new TestSubscriber<>();
        final Flowable<User> userByNick = profileViewModel.getUserByNick("non existing user");
        userByNick.subscribe(userTestSubscriber);

        userTestSubscriber.assertNoErrors();
        userTestSubscriber.assertNoValues();
    }
}