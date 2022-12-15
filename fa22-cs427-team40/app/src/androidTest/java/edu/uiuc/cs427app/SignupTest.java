package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import edu.uiuc.cs427app.ui.login.LoginActivity;

/**
 * This test suite verifies the functionality of the user sign up feature.
 *
 */
@RunWith(AndroidJUnit4.class)
public class SignupTest {
    static Intent initUser;
    static {
        initUser = new Intent(ApplicationProvider.getApplicationContext(), SettingsActivity.class);
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
     * Tests user signup feature
     * @throws InterruptedException
     */
    @Test
    public void testUserSignup() throws InterruptedException {
        // Check if username shows up on title in SettingsActivity
        Thread.sleep(2000);
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText("Team 40-xz45")));
        Thread.sleep(2000);

        // Select theme and hit Done button
        onView(withId(R.id.rbt4)).perform(click());
        onView(withId(R.id.donebtn)).perform(click());

        // Check if title of MainActivity contains username, confirming that login was successful
        Thread.sleep(2000);
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText("Team 40-xz45")));
    }
}
