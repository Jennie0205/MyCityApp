package edu.uiuc.cs427app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

/**
 * This class shows the list of locations
 */
public class ListViewAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;

    /**
     *
     * Constructor for ListviewAdapter
     * @param context context to show the item
     * @param items cities on the list
     */
    public ListViewAdapter(Context context, ArrayList<String> items){
        super(context, R.layout.list_row, items);
        this.context = context;
        list = items;
    }

    /**
     * Make a view to show list
     * @param position index of the city
     * @param convertView row that contains the city chosen by the user
     * @param parent view that contains list with all cities
     * @return list of cities with index and details, remove buttons
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row, null);

            TextView number = convertView.findViewById(R.id.number);
            number.setText(position + 1 + ".");

            TextView name = convertView.findViewById(R.id.name);
            name.setText(list.get(position));

            ImageView remove = convertView.findViewById(R.id.remove);
            remove.setOnClickListener(new View.OnClickListener() {

                /**
                 * Remove city from the list when the view is clicked
                 * @param view view of the View class
                 */
                @Override
                public void onClick(View view) {
                    GetCityNameActivity.removeItem(position);
                }
            });

            // Milestone 4:
            ImageView details = convertView.findViewById(R.id.details);
            details.setOnClickListener(new View.OnClickListener() {

                /**
                 * Show details of the city when the city name is clicked
                 * @param view view of the View class
                 */
                @Override
                public void onClick(View view) {
                    //getCityName.removeItem(position);
                    //Fill Show details
                }
            });
        }
        return convertView;
    }
}
















