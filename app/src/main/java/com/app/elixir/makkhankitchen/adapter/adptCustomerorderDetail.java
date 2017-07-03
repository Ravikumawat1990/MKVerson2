package com.app.elixir.makkhankitchen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.OrderDeliveryDetailArraySubSub;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListenerOrderSuumnery;
import com.app.elixir.makkhankitchen.mtplview.MtplTextView;
import com.app.elixir.makkhankitchen.utils.CM;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

/**
 * Created by Elixir on 09-Aug-2016.
 */
public class adptCustomerorderDetail extends RecyclerView.Adapter<adptCustomerorderDetail.MyViewHolder> {


    private ArrayList<OrderDeliveryDetailArraySubSub> dataSet;
    Context context;
    public OnItemClickListenerOrderSuumnery listener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        MtplTextView itemName, catName, txtAddonsVale, txtAddonsName, txtItemQuantity, txtItemPrice;
        ImageView placeImage, imageView;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = (MtplTextView) itemView.findViewById(R.id.orderSummeryItemName);
            itemName.setSelected(true);
            catName = (MtplTextView) itemView.findViewById(R.id.orderSummeryProductCat);
            catName.setSelected(true);
            txtAddonsName = (MtplTextView) itemView.findViewById(R.id.textViewAddOn);
            txtAddonsVale = (MtplTextView) itemView.findViewById(R.id.addonstext);
            txtItemQuantity = (MtplTextView) itemView.findViewById(R.id.orderSummeryItemQuanti);
            txtItemPrice = (MtplTextView) itemView.findViewById(R.id.orderSummeryItemPrice);
            placeImage = (ImageView) itemView.findViewById(R.id.list_image);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            imageView = (ImageView) itemView.findViewById(R.id.placeholder1);

        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {

            }
        }
    }


    public void SetOnItemClickListener(OnItemClickListenerOrderSuumnery mItemClickListener) {

        this.listener = mItemClickListener;
    }

    public adptCustomerorderDetail(Context context, ArrayList<OrderDeliveryDetailArraySubSub> data) {
        this.dataSet = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptercustomerdetail, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView itemName = holder.itemName;
        TextView catName = holder.catName;
        TextView addonsName = holder.txtAddonsName;
        TextView txtAddonsVale = holder.txtAddonsVale;
        TextView txtItemQuantity = holder.txtItemQuantity;
        TextView txtItemPrice = holder.txtItemPrice;
        ImageView imageView = holder.placeImage;
        final ImageView placeHolder = holder.imageView;

        itemName.setText(dataSet.get(listPosition).cProductName);
        catName.setText(dataSet.get(listPosition).cCategoryName);
        txtAddonsVale.setText("Addons");
        try {
            addonsName.setText(android.text.Html.fromHtml(dataSet.get(listPosition).cAddons).toString());
        } catch (Exception e) {

        }
        txtItemQuantity.setText(dataSet.get(listPosition).Qty);
        txtItemPrice.setText(CM.getSp(context, "currency", "").toString() + " " + dataSet.get(listPosition).nSubTotalAmt);

        if (dataSet.get(listPosition).cProductImagePath != null) {
            Glide.with(context).load(dataSet.get(listPosition).cProductImagePath)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            placeHolder.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            placeHolder.setVisibility(View.GONE);
                            return false;
                        }
                    }).error(R.drawable.longplace)
                    .into(imageView);
            //  Animation scale = AnimationUtils.loadAnimation(context, R.anim.zoom);
            // placeHolderView.startAnimation(scale);
        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}