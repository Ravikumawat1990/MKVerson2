package com.app.elixir.makkhankitchen.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.elixir.makkhankitchen.R;


/**
 * Created by Elixir on 02-Aug-2016.
 */
public class FragCategory extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragCategory";
    private Activity thisActivity;
    LinearLayout layout_home, layout_service, layout_mYAccount, layout_alert, layout_ContactUs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragcategoryservice, container, false);

        thisActivity = getActivity();


        setHasOptionsMenu(false);
        init(rootView);
        return rootView;
    }


    public void init(View rootView) {

        layout_home = (LinearLayout) rootView.findViewById(R.id.layoutHome);
        layout_service = (LinearLayout) rootView.findViewById(R.id.layoutService);
        layout_mYAccount = (LinearLayout) rootView.findViewById(R.id.layoutMyAccount);
        layout_alert = (LinearLayout) rootView.findViewById(R.id.layoutAlerts);
        layout_ContactUs = (LinearLayout) rootView.findViewById(R.id.layoutContact);

        layout_home.setOnClickListener(this);
        layout_service.setOnClickListener(this);
        layout_mYAccount.setOnClickListener(this);
        layout_alert.setOnClickListener(this);
        layout_ContactUs.setOnClickListener(this);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.foodcat, menu);
        if (menu.findItem(R.id.profile) != null) {
            menu.findItem(R.id.profile).setVisible(false);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutHome:

                FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                FragHome fragNotification = new FragHome();
                t.setCustomAnimations(0, R.anim.fadeout);
                t.replace(R.id.container, fragNotification).addToBackStack("FragCategory");
                t.commit();
                break;
            case R.id.layoutService:
                FragmentTransaction tt = getActivity().getSupportFragmentManager().beginTransaction();
                FragServices fragNotification1 = new FragServices();
                tt.setCustomAnimations(0, R.anim.fadeout);
                tt.replace(R.id.container, fragNotification1).addToBackStack("FragCategory");
                tt.commit();
                break;
            case R.id.layoutMyAccount:
                break;
            case R.id.layoutAlerts:
                break;
            case R.id.layoutContact:
                break;

        }

    }
}