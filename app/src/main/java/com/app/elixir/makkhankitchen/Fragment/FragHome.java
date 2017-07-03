package com.app.elixir.makkhankitchen.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.elixir.makkhankitchen.Model.DetailModel;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.adapter.adapter_home;

import java.util.ArrayList;


public class FragHome extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragEditProfile";
    private Activity thisActivity;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    ArrayList<DetailModel> detailModels;

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(thisActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initData();
        adapter_home adapter_home = new adapter_home(thisActivity, detailModels);
        mRecyclerView.setAdapter(adapter_home);

    }


    public void initData() {
        detailModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DetailModel detailModel = new DetailModel();
            detailModel.setSerNo(String.valueOf(i));
            detailModel.setCompName("R" + String.valueOf(i));
            detailModel.setDate(String.valueOf(i) + "-05-2017");
            detailModel.setReportType("XYZ");
            detailModel.setStatus("Done");
            detailModels.add(detailModel);
        }

    }


    @Override
    public void onClick(View view) {

    }
}
