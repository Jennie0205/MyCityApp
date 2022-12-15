package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * Displays the settings page which allows for selection of theme
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup themesRadioGroupMain;
    private String username;
    private String email;
    private UserModelList userList;

    /**
     * Sets theme, title, and on click listener for the done button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gets theme from intent and sets it
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        userList = (UserModelList) getIntent().getSerializableExtra("UserList");
        this.setTheme(userList.getUserByEmail(email).getTheme());

        setContentView(R.layout.settings);

        this.setTitle("Team 40-" + username);
        themesRadioGroupMain = findViewById(R.id.themesRadioGroupMain);

        Button toMainBtn = findViewById(R.id.donebtn);
        toMainBtn.setOnClickListener(this);

        themesRadioGroupMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * Sets the theme to the radio button chosen
             * @param radioGroup the radio group of theme options
             * @param rbtid id of the specific radio button chosen
             */
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int rbtid) {
                switch (rbtid){
                    case R.id.rbt1:
                        userList.getUserByEmail(email).setTheme(R.style.Theme_MyFirstApp);
                        break;
                    case R.id.rbt2:
                        userList.getUserByEmail(email).setTheme(R.style.Theme_MyFirstAppVar);
                        break;
                    case R.id.rbt3:
                        userList.getUserByEmail(email).setTheme(R.style.Theme_Illini);
                        break;
                    case R.id.rbt4:
                        userList.getUserByEmail(email).setTheme(R.style.Theme_IlliniVar);
                        break;
                }
            }
        });

    }

    /**
     * On click method for the done button.
     * Saves and adds the theme to the intent and opens the main activity page.
     * @param view current view
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.donebtn:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                intent.putExtra("UserList", userList);
                startActivity(intent);
                break;
        }
    }

}
