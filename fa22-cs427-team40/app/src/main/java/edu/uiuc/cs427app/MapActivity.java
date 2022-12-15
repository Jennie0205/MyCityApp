package edu.uiuc.cs427app;
// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private String username;
    private String cityName;
    private LatLng coordinate;
    private UserModelList userList;
    private String email;

    /**
     * Gets latitude and longitude given city name
     * @param context Application Context
     * @param cityName Name of City
     * @return LatLng object which contains the latitude and longitude of the city.
     */
    private LatLng getLocationFromAddress(Context context, String cityName) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(cityName, 5);
            if (address.size() == 0) {
                Log.e("mapError", "City: "+cityName+" not found.");
                coordinate = new LatLng(0, 0);
                return coordinate;
            }

            Address location = address.get(0);
            coordinate = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return coordinate;
    }

    /**
     * Sets the theme, title, locations, and makes on click listeners for all buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        cityName = intent.getStringExtra("cityName");
        email = intent.getStringExtra("email");
        userList = (UserModelList) getIntent().getSerializableExtra("UserList");

        this.setTheme(userList.getUserByEmail(email).getTheme());
        setContentView(R.layout.map);

        coordinate = getLocationFromAddress(this, cityName);
        TextView latitude = (TextView) findViewById(R.id.latitude);
        latitude.setText("Latitude: "+coordinate.latitude);

        TextView longitude = (TextView) findViewById(R.id.longitude);
        longitude.setText("Longitude: "+coordinate.longitude);

        TextView cityText = (TextView) findViewById(R.id.city_name);
        cityText.setText("City Name: "+cityName);
        this.setTitle("Team 40-" + username);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
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
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker at city's location,
        // and move the map's camera to the same location.
        googleMap.addMarker(new MarkerOptions()
                .position(coordinate)
                .title("Marker in "+cityName));
        googleMap.setMinZoomPreference(10);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
    }
}