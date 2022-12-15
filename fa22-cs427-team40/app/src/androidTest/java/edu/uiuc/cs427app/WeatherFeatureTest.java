package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.startsWith;

import android.content.Intent;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test suite verifies the functionality of the weather feature with two cities.
 *
 */
@RunWith(AndroidJUnit4.class)
public class WeatherFeatureTest {

    static UserModelList userList;
    static Intent intent;

    static {    //add chicago and champaign to user locations and add intent
        UserModel user = new UserModel("ajatar2@illinois.edu");
        user.getLocations().add("Chicago");
        user.getLocations().add("Champaign");
        userList.addUser(user);

        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra("username", "ajatar2");
        intent.putExtra("email", "ajatar2@illinois.edu");
        intent.putExtra("UserList", userList);
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(intent);

    /**
     * Tests weather button on main page for location Chicago
     * @throws InterruptedException
     */
    @Test
    public void testWeatherChicago() throws InterruptedException {
        Thread.sleep(2000);
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.weather)), 0))).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.cityName)).check(matches(withText("Chicago")));
        onView(withId(R.id.humidity)).check(matches(withText(startsWith("humidity:"))));
    }

    /**
     * Tests weather button on main page for location Champaign
     * @throws InterruptedException
     */
    @Test
    public void testWeatherChampaign() throws InterruptedException {
        Thread.sleep(2000);
        onView(allOf(getElementFromMatchAtPosition(allOf(withId(R.id.weather)), 1))).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.cityName)).check(matches(withText("Champaign")));
        onView(withId(R.id.humidity)).check(matches(withText(startsWith("humidity:"))));
    }

    /**
     * Matches with nth element which matches with matcher. Starts at 0th position.
     * Inspiration from: https://stackoverflow.com/questions/32387137/espresso-match-first-element-found-when-many-are-in-hierarchy
     * @param matcher matcher to match with
     * @param n position of element
     * @return Matcher<View> corresponding to nth element matching with matcher
     */
    private static Matcher<View> getElementFromMatchAtPosition(final Matcher<View> matcher, final int n) {
        return new BaseMatcher<View>() {
            int counter = 0;
            @Override
            public boolean matches(final Object item) {
                if (matcher.matches(item)) {
                    if(counter == n) {
                        counter++;
                        return true;
                    }
                    counter++;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("Element at position " + n);
            }
        };
    }

}