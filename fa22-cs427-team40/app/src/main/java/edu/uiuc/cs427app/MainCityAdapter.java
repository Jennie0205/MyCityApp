package edu.uiuc.cs427app;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

/**
 * This class shows the list of locations
 */
public class MainCityAdapter extends ArrayAdapter<String> {
    ArrayList<String> cityList;
    Context context;

    /**
     *
     * Constructor for MainCityAdapter
     * @param context context to show the item
     * @param cities cities on the list
     */
    public MainCityAdapter(Context context, ArrayList<String> cities){
        super(context, R.layout.list_row, cities);
        this.context = context;
        cityList = cities;
    }

    /**
     * Make a view to show list
     * @param cityIdx index of the city
     * @param convertView row that contains the city chosen by the user
     * @param parent view that contains list with all cities
     * @return list of cities with index and details, remove buttons
     */
    @NonNull
    @Override
    public View getView(int cityIdx, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.city_list_main, null);

            TextView name = convertView.findViewById(R.id.city);
            name.setText(cityList.get(cityIdx));

            ImageView weather = convertView.findViewById(R.id.weather);
            weather.setOnClickListener(new View.OnClickListener() {

                /**
                 * Remove city from the list when the view is clicked
                 * @param view view of the View class
                 */
                @Override
                public void onClick(View view) {
                    MainActivity.getWeather(cityIdx, context);
                }
            });

            ImageView mapBtn = convertView.findViewById(R.id.map);
            mapBtn.setOnClickListener(new View.OnClickListener() {

                /**
                 * Show details of the city when the city name is clicked
                 * @param view view of the View class
                 */
                @Override
                public void onClick(View view) {
                    //Fill Show details
                    MainActivity.getMap(cityIdx, context);
                }
            });
        }
        return convertView;
    }
}
