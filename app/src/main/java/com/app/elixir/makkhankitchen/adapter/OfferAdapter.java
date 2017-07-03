package com.app.elixir.makkhankitchen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.OfferModel;
import com.app.elixir.makkhankitchen.R;

import java.util.List;

/**
 * Created by NetSupport on 01-03-2017.
 */

public class OfferAdapter extends
        RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    private List<OfferModel> offerModels;

    /**
     * View holder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView countryText;
        public TextView popText;

        public MyViewHolder(View view) {
            super(view);
            countryText = (TextView) view.findViewById(R.id.countryName);
            popText = (TextView) view.findViewById(R.id.pop);
        }
    }

    public OfferAdapter(List<OfferModel> countryList) {
        this.offerModels = countryList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OfferModel c = offerModels.get(position);
        holder.countryText.setText(c.getOfferTile());
        holder.popText.setText(String.valueOf(c.getOfferTile()));
    }

    @Override
    public int getItemCount() {
        return offerModels.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offerrow, parent, false);
        return new MyViewHolder(v);
    }
}
