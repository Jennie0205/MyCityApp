package edu.uiuc.cs427app;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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

/**
 * This test suite verifies the functionality of the user login feature.
 *
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {
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
     * Test whether the user is logged in properly by checking the action_bar text.
     * @throws InterruptedException
     */
    @Test
    public void testUserLogin() throws InterruptedException {
        // Check if title of MainActivity contains username, confirming that login was successful.
        Thread.sleep(2000);;
        onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText("Team 40-xz45")));
        Thread.sleep(2000);;

    }
}
