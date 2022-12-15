package edu.uiuc.cs427app;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;


@RunWith(AndroidJUnit4.class)
public class MockLocationTest {

    static UserModelList userList;
    static Intent intent;
    static Context context;
    private static MockLocationHelper mockNetwork;
    private static MockLocationHelper mockGps;

    static {
        UserModel user = new UserModel("weih7@illinois.edu");
        userList.addUser(user);

        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("username", "weih7");
        intent.putExtra("email", "weih7@illinois.edu");
        intent.putExtra("UserList", userList);
    }

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule =
            new ActivityScenarioRule<>(intent);

    /**
     * Test whether New York City name, longitude/latitude are displayed correctly
     * @throws InterruptedException
     */
    @Test
    public void MockLocation() throws InterruptedException{
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
        onView(usingIndex(withId(R.id.map), 0)).perform(click());
        Thread.sleep(3000);

        context = getInstrumentation().getTargetContext();
        try {
            if (mockNetwork != null){
                mockNetwork.shutdown();
            }
            if (mockGps != null){
                mockGps.shutdown();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.d("Espresso", "mock cannot be executed");
        }

        try {
            double lat = 12.45345;
            double lng = 34.53234;
            mockNetwork = new MockLocationHelper(LocationManager.NETWORK_PROVIDER, context);
            mockGps = new MockLocationHelper(LocationManager.GPS_PROVIDER, context);
            mockNetwork.updateLocation(lat, lng);
            mockGps.updateLocation(lat, lng);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Thread.sleep(5000);
        mockNetwork.shutdown();
        mockGps.shutdown();
    }

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
