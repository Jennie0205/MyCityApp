package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * This class is for getting city names from user input
 */
public class GetCityNameActivity extends AppCompatActivity implements View.OnClickListener{

    private static ListView listView;
    private static ListViewAdapter adapter;
    private String username;
    private String email;
    private UserModelList userList;
    private static ArrayList<String> cities;

    EditText input;
    ImageView enter;

    /**
     * This function gets theme, user information and makes a list for the cities.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        username = intent.getStringExtra("username");
        userList = (UserModelList) getIntent().getSerializableExtra("UserList");
        cities = userList.getUserByEmail(email).getLocations();
        this.setTheme(userList.getUserByEmail(email).getTheme());
        setContentView(R.layout.activity_getcityname);

        listView = findViewById(R.id.listview);
        input = findViewById(R.id.input);
        enter = findViewById(R.id.add);
        Button doneBtn = findViewById(R.id.getCityDone);

        this.setTitle("Team 40-" + username);

        doneBtn.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Getting the citi name in String
             * @param adapterView viewgroup for activity_getcityname
             * @param view default GUI component class for activity_getcityname
             * @param i index of the city
             * @param l N/A
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = cities.get(i);
                makeToast(name);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /**
             * Remove the city from the list
             * @param adapterView viewgroup for activity_getcityname
             * @param view default GUI component class for activity_getcityname
             * @param i index of the city
             * @param l N/A
             * @return false
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                makeToast("Removed: "+ cities.get(i));
                removeItem(i);
                return false;
            }
        });

        adapter = new ListViewAdapter(getApplicationContext(), cities);
        listView.setAdapter(adapter);

        enter.setOnClickListener(new View.OnClickListener() {
            /**
             * Activate button for adding and get city name for adding
             * @param view default GUI component class for activity_getcityname
             */
            @Override
            public void onClick(View view) {
                String text = input.getText().toString();
                if (text == null || text.length() == 0) {
                    makeToast("Enter an cities");

                } else {
                    addItem(text);
                    input.setText("");
                    makeToast("Added: " + text);
                }
            }
        });

    }

    /**
     * Remove city from  the list
     * @param remove city name index that needs to be removed
     */
    public static void removeItem(int remove){
        cities.remove(remove);
        listView.setAdapter(adapter);
    }

    /**
     * Add city to the list
     * @param item city name index for newly added item
     */
    public void addItem(String item){
        cities.add(item);
        listView.setAdapter(adapter);
    }

    Toast t;

    /**
     * Make a text for display
     * @param s text that will be displayed in the pop-up window
     */
    private void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.show();
    }

    /**
     * Navigate to the main activity
     * @param view default GUI component class for activity_getcityname
     */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("UserList", userList);
        startActivity(intent);
    }


}
