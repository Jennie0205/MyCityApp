package edu.uiuc.cs427app;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import androidx.navigation.ui.AppBarConfiguration;
import edu.uiuc.cs427app.databinding.ActivityMainBinding;
import edu.uiuc.cs427app.ui.login.LoginActivity;
import edu.uiuc.cs427app.DetailsActivity;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Displays all locations on main activity page
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private static String username;
    private static String email;
    private static UserModelList userList;
    private static ArrayList<String> locations;
    private static ListView cityView;
    private static MainCityAdapter mainCityAdapter;

    private static final String TAG = "MyActivity";

    /**
     * Sets the theme, title, locations, and makes on click listeners for all buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        userList = (UserModelList) getIntent().getSerializableExtra("UserList");
        this.setTheme(userList.getUserByEmail(email).getTheme());
        locations = userList.getUserByEmail(email).getLocations();
        setContentView(R.layout.activity_main);

        cityView = findViewById(R.id.citylist);
        mainCityAdapter = new MainCityAdapter(getBaseContext(), locations);
        cityView.setAdapter(mainCityAdapter);
        Button settingsBtn = findViewById(R.id.settingsbtn);
        Button logOut = findViewById(R.id.buttonSignOut);
        Button editLocation = findViewById(R.id.buttonEditLocation);
        settingsBtn.setOnClickListener(this);
        logOut.setOnClickListener(this);
        editLocation.setOnClickListener(this);

        this.setTitle("Team 40-" + username);
    }

    /**
     * OnClick Listener for Weather button
     * @param idx Index of city name in city list
     * @param context
     */
    public static void getWeather(int idx, Context context) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("cityName", locations.get(idx));
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("UserList", userList);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * OnClick Listener for Map button
     * @param idx Index of city name in city list
     * @param context
     */
    public static void getMap(int idx, Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra("cityName", locations.get(idx));
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("UserList", userList);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Signs out user from app
     */
    private void signOut() {
        GoogleSignInOptions gso = new
                GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
    }

    /**
     * Handles the user clicking any of the buttons
     * @param view current view
     */
    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        switch (view.getId()) {
            case R.id.settingsbtn:
                // Navigate to SettingsActivity
                intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                intent.putExtra("UserList", userList);
                startActivity(intent);
                break;

            case R.id.buttonSignOut:
                // Sign out and navigate to LoginActivity
                signOut();
                intent = new Intent(this, LoginActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("UserList", userList);
                startActivity(intent);
                break;

            case R.id.buttonEditLocation:
                // Navigate to GetCityNameActivity
                intent = new Intent(this, GetCityNameActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                intent.putExtra("UserList", userList);
                startActivity(intent);
                break;

        }
    }
}

