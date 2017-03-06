package edu.milton.miltonmobileandroid.freefinder;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import biweekly.util.IOUtils;
import edu.milton.miltonmobileandroid.R;

public class FinderFragment extends Fragment {

    ArrayList<String> people = new ArrayList<>();
    String api_url = "http://freefinder.ma1geek.org/getSchedules.php";
    ActionBar actionBar;
    int year;
    int month;
    int day;
    PagerAdapter pagerAdapter;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.freefinder_main, container, false);
        sharedPreferences = getActivity().getSharedPreferences("ivan.is.awesome.freefinder", Context.MODE_PRIVATE);
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
        }
        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        for(int x=0; x<5; x++){
            pagerAdapter.addFragment(new FreeFragment(x, this.getContext()));
        }

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        actionBar.setTitle("Monday");
                        break;
                    case 1:
                        actionBar.setTitle("Tuesday");
                        break;
                    case 2:
                        actionBar.setTitle("Wednesday");
                        break;
                    case 3:
                        actionBar.setTitle("Thursday");
                        break;
                    case 4:
                        actionBar.setTitle("Friday");
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        boolean saved = sharedPreferences.getBoolean("SAVED", false);
        if(!saved){
            SpecificData data = new SpecificData();
            data.execute();
        }
        Calendar cal = Calendar.getInstance();
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        actionBar.setTitle("Monday");
        SpecificData data = new SpecificData();
        data.execute();
    return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void setFrees(String str){
        StringTokenizer token = new StringTokenizer(str, "-");
        for(int i = 0; i<2; i++){
            token.nextElement();
        }
        for(int x =0; x<5; x++){
            String tempName = token.nextElement().toString();
            char[] seq = tempName.toCharArray();
            for(int z=0; z<seq.length; z++){
                if (String.valueOf(seq[z]).equals("1")){
                    //class
                    FreeFragment frag = (FreeFragment)pagerAdapter.getItem(x);
                    frag.setButtons(z, 1);
                    pagerAdapter.setmFragment(frag, x);
                } else {
                    //free
                    FreeFragment frag = (FreeFragment)pagerAdapter.getItem(x);
                    frag.setButtons(z, 0);
                    pagerAdapter.setmFragment(frag, x);
                }
            }
        }
    }
    private void initPaging() {

    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();

        PagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public void addFragment(Fragment f) {
            mFragments.add(f);
            notifyDataSetChanged();
        }

        public void setmFragment(Fragment f, int x){
            mFragments.set(x, f);
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            switch (position) {
                case 0:
                    return mFragments.get(0);
                case 1:
                    return mFragments.get(1);
                case 2:
                    return mFragments.get(2);
                case 3:
                    return mFragments.get(3);
                case 4:
                    return mFragments.get(4);

            }
            return null;
        }
    }
    public class SpecificData extends AsyncTask<Void, Integer, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try {
                URL url = new URL(api_url);
                URLConnection con = url.openConnection();
                String encoding = con.getContentEncoding();
                encoding = encoding == null ? "UTF-8" : encoding;
                InputStream input = con.getInputStream();
                //String in_raw = IOUtils.toString(input, encoding);
                String in_raw = "";
                JSONObject result_object = new JSONObject(in_raw);
                JSONArray schedules = result_object.getJSONArray("Schedule");
                for(int x=0; x<schedules.length(); x++){
                    JSONObject ind = schedules.getJSONObject(x);
                    if(ind.getString("Firstname").equals("Ivan")){
                        people.add(ind.getString("Firstname")+"-"+ind.getString("Lastname")+"-"+ind.getString("Monday")+"-"+ind.getString("Tuesday")+"-"+ind.getString("Wednesday")+"-"+ind.getString("Thursday")+"-"+ind.getString("Friday"));
                    }
                }
                return people;
            } catch (Exception e) {
                e.printStackTrace();
                return people;
            }
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            setFrees(result.get(0));
            boolean saved = sharedPreferences.getBoolean("SAVED", false);
            if(!saved){
                sharedPreferences.edit().putBoolean("SAVED", true).apply();
            }
            sharedPreferences.edit().putString("SAVED_FREES", result.get(0)).apply();
        }
    }
}
