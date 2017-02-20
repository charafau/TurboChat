package com.nullpointerbay.turbochat.viewmodel;

import com.nullpointerbay.turbochat.model.Team;
import com.nullpointerbay.turbochat.repository.EmojiRepository;
import com.nullpointerbay.turbochat.testutils.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.when;


public class EmojiViewModelTest {

    @Mock
    EmojiRepository emojiRepository;
    private EmojiViewModel emojiViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        emojiViewModel = new EmojiViewModel(emojiRepository);
    }

    @Test
    public void shouldReturnEmojiListForGivenTeam() throws Exception {

        final Team team = TestUtils.createTeam();

        final List<String> emojiList = Arrays.asList("awwyeah", "love", "lol");

        when(emojiRepository.getEmojiList("random uuid")).thenReturn(Single.just(emojiList));

        final TestSubscriber<List<String>> subscriber = new TestSubscriber<>();
        final Single<List<String>> emojiViewModelEmojis = emojiViewModel.getEmojis(team);

        emojiViewModelEmojis.toFlowable().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertResult(emojiList);
    }
}