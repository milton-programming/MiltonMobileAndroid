package edu.milton.miltonmobileandroid.freefinder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.*;
import android.widget.Button;

import java.util.StringTokenizer;

import edu.milton.miltonmobileandroid.R;


@SuppressLint("ValidFragment")
public class FreeFragment extends Fragment{

    Button[] select = new Button[8];
    boolean[] sel = new boolean[8];
    int pos;
    SharedPreferences sharedPreferences;
    Context context;


    public FreeFragment(int position, Context context) {
        pos = position;
        this.context = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sharedPreferences = getActivity().getSharedPreferences("ivan.is.awesome.freefinder", Context.MODE_PRIVATE);
    }
    public void setButtons(int a, int b){
        if(b==1){
            select[a].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.takenColor));
        } else {
            select[a].setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.freeColor));
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.freefinder_fragment, container, false);
        select[0] = (Button) v.findViewById(R.id.b1);
        select[1] = (Button) v.findViewById(R.id.b2);
        select[2] = (Button) v.findViewById(R.id.b3);
        select[3] = (Button) v.findViewById(R.id.b4);
        select[4] = (Button) v.findViewById(R.id.b5);
        select[5] = (Button) v.findViewById(R.id.b6);
        select[6] = (Button) v.findViewById(R.id.b7);
        select[7] = (Button) v.findViewById(R.id.b8);
        for(int x=0; x<select.length; x++){
            final int it = x;
            select[x].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!sel[it]) {
                        v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.freeColor));
                        sel[it]=true;
                    }else{
                        v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.takenColor));
                        sel[it]=false;
                    }
                }
            });
        }
        v.requestFocus();
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray("select", sel);
    }
    public void setFrees(String str) {
        StringTokenizer token = new StringTokenizer(str, "-");
        for (int i = 0; i < 2; i++) {
            token.nextElement();
        }
        for (int x = -1; x < pos; x++) {
            String tempName = token.nextElement().toString();
            char[] seq = tempName.toCharArray();
            for (int z = 0; z < seq.length; z++) {
                if (String.valueOf(seq[z]).equals("1")) {
                    setButtons(z, 1);
                } else {
                    setButtons(z, 0);
                }
            }
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boolean saved = sharedPreferences.getBoolean("SAVED", false);
        if(saved){
            setFrees(sharedPreferences.getString("SAVED_FREES", ""));
        }
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            sel = savedInstanceState.getBooleanArray("select");
            for(int x=0; x<select.length;x++){
                assert sel != null;
                if(!sel[x]){
                    select[x].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.freeColor));
                }else{
                    select[x].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.takenColor));
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

}
