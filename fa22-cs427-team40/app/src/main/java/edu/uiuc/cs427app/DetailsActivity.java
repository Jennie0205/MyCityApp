package edu.uiuc.cs427app;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.*;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

/**
 * This class displays weather and map data for a given location
 */
public class DetailsActivity extends AppCompatActivity{
    private String cityName;
    private String username;
    private String email;
    private UserModelList userList;
    private LatLng coordinate;

    final String APP_ID = "248033a3b4bcf6b9b518b0622aa36c8d";
    final String WEATHER_URL= "https://api.openweathermap.org/data/2.5/weather";

    TextView NameOfCity, weatherState, Temperature, humidity, wind, dateAndTime;
    ImageView weatherIcon;

    /**
     * Customizes text based on the location and sets on click listeners for the map button.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Process the Intent payload that has opened this Activity and show the information accordingly
        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        username = intent.getStringExtra("username");
        userList = (UserModelList) getIntent().getSerializableExtra("UserList");
        email = intent.getStringExtra("email");
        this.setTheme(userList.getUserByEmail(email).getTheme());

        getWeatherForNewCity(cityName);

        setContentView(R.layout.activity_details);

        // Initializing the GUI elements
        weatherState = findViewById(R.id.weatherCondition);
        Temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        NameOfCity = findViewById(R.id.cityName);
        wind = findViewById(R.id.wind);

        weatherIcon = findViewById(R.id.weatherIcon);
        dateAndTime = findViewById(R.id.date_and_time);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle("Team 40-" + username);
    }

    /**
     * On Click listener for back button
     * @param item MenuItem
     * @return true or false depending if the back button is selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get the weather for city given city name
     * @param city Name of City
     */
    private void getWeatherForNewCity(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",APP_ID);
        getWeatherApiCall(params);
    }

    /**
     * Makes API call to OpenWeatherMap and updates UI on success.
     * @param params RequestParams
     */
    private void getWeatherApiCall(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler()
        {
            /**
             * OnSuccess listener for API call. Updates UI.
             * @param statusCode
             * @param headers
             * @param response
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(DetailsActivity.this,"Data Get Success",Toast.LENGTH_SHORT).show();
                weatherDataParser weatherD=weatherDataParser.fromJson(response);
                updateUI(weatherD);
            }

            /**
             * OnFailure Listener for API Call
             * @param statusCode
             * @param headers
             * @param throwable
             * @param errorResponse
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
            }
        });

    }

    /**
     * Updates the values in the UI with API results.
     * @param weather WeatherDataParser
     */
    private void updateUI(weatherDataParser weather){
        Picasso.get().load("https://openweathermap.org/img/wn/" + weather.getIcon() + "@4x.png").into(weatherIcon);
        Temperature.setText(weather.getmTemperature());
        NameOfCity.setText(weather.getMcity());
        weatherState.setText(weather.getmWeatherType());
        humidity.setText("humidity: " + weather.getmHumidity() + "%");
        wind.setText("wind speed: " + weather.getWindCondition() + " m/sec");

        long offset = weather.getOffset();
        offset += Instant.now().getEpochSecond();
        Date date = new Date(offset * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy - h:mm a", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);

       dateAndTime.setText(formattedDate);
    }
}
