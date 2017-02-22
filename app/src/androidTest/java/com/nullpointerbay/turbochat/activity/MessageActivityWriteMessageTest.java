package com.nullpointerbay.turbochat.activity;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.nullpointerbay.turbochat.R;
import com.nullpointerbay.turbochat.RxIdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.plugins.RxJavaPlugins;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MessageActivityWriteMessageTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @BeforeClass
    public static void setUp() throws Exception {
        RxIdlingResource rxIdlingResource = new RxIdlingResource();
        Espresso.registerIdlingResources(rxIdlingResource);
        RxJavaPlugins.setScheduleHandler(rxIdlingResource);

    }

    @Test
    public void messageActivityWriteMessage() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_teams), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.txt_user), withText("Yui Kanazawa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_messages),
                                        2),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Yui Kanazawa")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.txt_message), withText("I see @alex and @bruno are having fun here (notbad)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_messages),
                                        2),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("I see @alex and @bruno are having fun here (notbad)")));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_comment), isDisplayed()));
        appCompatEditText.perform(replaceText("hello @bruno"), closeSoftKeyboard());

        onView(withId(R.id.recycler_messages)).perform(ViewActions.swipeUp());


        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.edit_comment), withText("hello @bruno"), isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.txt_user), withText("Alex Smith"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_messages), 6),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Alex Smith")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.txt_message), withText("hello @bruno"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_messages),
                                        6),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("hello @bruno")));

    }
}
