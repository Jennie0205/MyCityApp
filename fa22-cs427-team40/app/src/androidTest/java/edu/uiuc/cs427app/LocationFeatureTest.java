package edu.uiuc.cs427app;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.view.View;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

/**
 * This test suite verifies the functionality of the location feature with two cities.
 *
 */
@RunWith(AndroidJUnit4.class)
public class LocationFeatureTest {

    static UserModelList userList;
    static Intent intent;

    static {
        UserModel user = new UserModel("weih7@illinois.edu");
        userList.addUser(user);

        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra("username", "weih7");
        intent.putExtra("email", "weih7@illinois.edu");
        intent.putExtra("UserList", userList);
    }

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule =
            new ActivityScenarioRule<>(intent);

    /**
     * Test whether Los Angeles name, longitude/latitude are displayed correctly
     * @throws InterruptedException
     */
    @Test
    public void testLosAngelesLocation() throws InterruptedException{
        // Type city name and then press the Done button
        Thread.sleep(1500);
        onView(withId(R.id.buttonEditLocation)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.input)).perform(typeText("Los Angeles"), closeSoftKeyboard());
        Thread.sleep(1500);
        onView(withId(R.id.add)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.getCityDone)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.map)).perform(click());
        Thread.sleep(2000);

        // Check whether the city name, longitude and latitude are displayed correctly
        onView(withId(R.id.city_name)).check(matches(withText("City Name: Los Angeles")));
        onView(withId(R.id.longitude)).check(matches(withText("Longitude: -118.24368489999999")));
        onView(withId(R.id.latitude)).check(matches(withText("Latitude: 34.0522342")));
        Thread.sleep(1500);
        onView(withContentDescription("Google Map")).perform(click());
    }

    /**
     * Test whether San Francisco name, longitude/latitude are displayed correctly
     * @throws InterruptedException
     */
    @Test
    public void testSanFranciscoLocation() throws InterruptedException{
        // Type city name and then press the Done button
        Thread.sleep(1500);
        onView(withId(R.id.buttonEditLocation)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.input)).perform(typeText("San Francisco"), closeSoftKeyboard());
        Thread.sleep(1500);
        onView(withId(R.id.add)).perform(click());

        Thread.sleep(1500);
        onView(withId(R.id.getCityDone)).perform(click());
        Thread.sleep(1500);

        onView(usingIndex(withId(R.id.map), 1)).perform(click());

        Thread.sleep(1500);

        // Check whether the city name, longitude and latitude are displayed correctly for San Francisco
        onView(withId(R.id.city_name)).check(matches(withText("City Name: San Francisco")));
        onView(withId(R.id.longitude)).check(matches(withText("Longitude: -122.41941550000001")));
        onView(withId(R.id.latitude)).check(matches(withText("Latitude: 37.7749295")));
        onView(withContentDescription("Google Map")).perform(click());
    }

    /**
     * Provide helper function to locate the button according to its index
     *
     * Taken from: https://stackoverflow.com/questions/29378552/in-espresso-how-to-avoid-ambiguousviewmatcherexception-when-multiple-views-matc
     * @param matcher
     * @param idx
     * @return Matcher<View> at the current index
     */
    public static Matcher<View> usingIndex(final Matcher<View> matcher, final int idx) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("using index: ");
                description.appendValue(idx);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == idx;
            }
        };
    }

}
