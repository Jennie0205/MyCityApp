package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test suite verifies the functionality of adding and removing a new city.
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddRemoveCityTest {

    static UserModelList userList;
    static Intent intent;

    static {
        UserModel user = new UserModel("jy77@illinois.edu");
        userList.addUser(user);

        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra("username", "jy77");
        intent.putExtra("email", "jy77@illinois.edu");
        intent.putExtra("UserList", userList);
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(intent);

    /**
     * Tests add city feature
     * @throws InterruptedException
     */
    @Test
    public void testAddCity() throws InterruptedException {
        onView(withId(R.id.buttonEditLocation)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.input)).perform(typeText("San Jose"), closeSoftKeyboard());
        Thread.sleep(2000);
        onView(withId(R.id.add)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.name)).check(matches(withText("San Jose")));
        Thread.sleep(2000);
        onView(withId(R.id.remove)).perform(click());
        Thread.sleep(2000);

    }

    /**
     * Tests remove city feature
     * @throws InterruptedException
     */
    @Test
    public void testRemoveCity() throws InterruptedException {
        onView(withId(R.id.buttonEditLocation)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.input)).perform(typeText("Cupertino"), closeSoftKeyboard());
        Thread.sleep(2000);
        onView(withId(R.id.add)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.remove)).perform(click());
        Thread.sleep(2000);
        onView(withText("Cupertino")).check(doesNotExist());
        Thread.sleep(2000);
    }
}
