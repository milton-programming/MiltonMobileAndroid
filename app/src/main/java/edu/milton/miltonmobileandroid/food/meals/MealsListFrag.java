package edu.milton.miltonmobileandroid.food.meals;
import android.annotation.SuppressLint;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import edu.milton.miltonmobileandroid.util.JsonHttp;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import edu.milton.miltonmobileandroid.R;

@SuppressLint({ "SimpleDateFormat", "ValidFragment" })
public class MealsListFrag extends ListFragment implements
        LoaderCallbacks<Cursor> {

    private static final String READ_MEALS_URL = "http://flik.ma1geek.org/getMeals.php";

    private final String LOG_TAG = this.getClass().toString();

    private ArrayList<MealsMenuItem> Foods = new ArrayList<>();

    private String date;
    private int dateShift;
    private Context context;


    public MealsListFrag(int position, Context context) {
        dateShift = position;
        this.context = context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Foods.size() > 0) {
            return;
        }
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, dateShift);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(c.getTime());
        final RequestParams params = new RequestParams();
        params.add("date",date);
        params.add("version","2"); //using the second version of the api
        Log.d("date", date);
        // date = "2013-11-23";
        // use to demonstrate if there are no items for current date
        AsyncHttpClient client = new AsyncHttpClient();
        client.setUserAgent(JsonHttp.USER_AGENT);

        client.get(context, READ_MEALS_URL,params,new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(LOG_TAG,"The response is: " + responseString);
            }

            public void onSuccess(int statusCode, Header[] headers, final JSONObject jsonEntrees) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // get food
                        try {
                            Iterator<String> itr = jsonEntrees.keys();
                            while(itr.hasNext()) {

                                String mealtimename = itr.next();
                                JSONObject mealTime = jsonEntrees.getJSONObject(mealtimename);
                                if(mealtimename.equals("Lunch") || mealtimename.equals("Dinner")){
                                    Foods.add(new MealsMenuItem(true, mealtimename));
                                    Iterator<String> itr2 = mealTime.keys();
                                    while(itr2.hasNext()) {
                                        String mealtypename = itr2.next();
                                        Foods.add(new MealsMenuItem(true, mealtypename));
                                        JSONArray meals = mealTime.getJSONArray(mealtypename);
                                        for (int i = 0; i < meals.length(); i++) {
                                            JSONObject c = meals.getJSONObject(i);
                                            Foods.add(new MealsMenuItem(false, c));
                                        }
                                    }
                                }
                            }
                            Log.v(LOG_TAG, "Foods is this big: " + Foods.size());
                            Log.d(LOG_TAG,"setting the adapter");
                            MealsListAdapter adapter = new MealsListAdapter(getActivity(), Foods,date);
                            setListAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }});
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data

    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        // TODO Auto-generated method stub
        int x = 0;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
        int x = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.food_meals_listfrag, null);
    }
}