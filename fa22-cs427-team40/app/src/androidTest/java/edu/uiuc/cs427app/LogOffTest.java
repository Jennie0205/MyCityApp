package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test suite verifies the functionality of the user log off feature.
 *
 */
@RunWith(AndroidJUnit4.class)
public class LogOffTest {
    static Intent initUser;
    static {
        initUser = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        initUser.putExtra("email", "xz45@illinois.edu");
        initUser.putExtra("username", "xz45");

        UserModel userModel = new UserModel("xz45@illinois.edu");
        UserModelList userList = new UserModelList();
        userList.addUser(userModel);
        initUser.putExtra("UserList", userList);
    }
    @Rule
    public ActivityScenarioRule<MainActivity> activityActivityScenarioRule
            = new ActivityScenarioRule<>(initUser);

    /**
     * Tests user logoff feature
     * @throws InterruptedException
     */
    @Test
    public void testLogOff() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.buttonSignOut)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.login)).check(matches(withText("SIGN IN OR REGISTER")));
    }
}
