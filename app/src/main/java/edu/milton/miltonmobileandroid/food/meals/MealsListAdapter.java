package edu.milton.miltonmobileandroid.food.meals;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import edu.milton.miltonmobileandroid.R;
import edu.milton.miltonmobileandroid.util.JsonHttp;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;

public class MealsListAdapter extends ArrayAdapter<Object> {
    private final Context context;
    private ArrayList<?> Values;
    private final String LOG_TAG = this.getClass().toString();
    private String date;


    public MealsListAdapter(Context context, ArrayList<?> Values, String date) {
        super(context, R.layout.food_meals_foodview, (ArrayList<Object>) Values);
        this.context = context;
        this.Values = Values;
        this.date = date;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        Log.d(LOG_TAG, "getting the view for position " + pos);
        View rowView;

        final MealsMenuItem rowItem = (MealsMenuItem) Values.get(pos);

        if (!rowItem.isHeading()) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.food_meals_foodview, parent,
                    false);
            TextView textView = (TextView) rowView
                    .findViewById(R.id.food_text_view);
            textView.setText(rowItem.getItemName());
            textView.setTextSize(12);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            // textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextIsSelectable(false);
            textView.getPaint().setAntiAlias(true);
            rowView.getLayoutParams().height = 100;
        } else {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.food_meals_foodview, parent,
                    false);
            TextView textView = (TextView) rowView
                    .findViewById(R.id.food_text_view);
            textView.setText(rowItem.getItemName());
            textView.setTextSize(18);
            textView.getPaint().setAntiAlias(true);
            if(textView.getText().equals("Lunch") || textView.getText().equals("Dinner")){
                rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.milton_blue));
                textView.setTextColor(ContextCompat.getColor(context, R.color.milton_orange));
            }else {
                textView.setTextColor(ContextCompat.getColor(context, R.color.milton_blue));
            }
            textView.setTextIsSelectable(false);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }

        // Change icon based on name

        return rowView;
    }
}