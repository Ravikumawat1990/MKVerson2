package com.app.elixir.makkhankitchen.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.elixir.makkhankitchen.R;


public class FragServices extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragServices";
    private Activity thisActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_home, container, false);
        thisActivity = getActivity();

        setHasOptionsMenu(true);
        init(rootView);
        return rootView;
    }

    public void init(View view) {


    }


    @Override
    public void onClick(View view) {

    }
}
