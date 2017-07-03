package com.app.elixir.makkhankitchen.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.app.elixir.makkhankitchen.Model.OrderDeliveryDetailArraySubSub;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListenerOrderSuumnery;
import com.app.elixir.makkhankitchen.mtplview.MtplButton;
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
public class adptorderDetail extends RecyclerView.Adapter<adptorderDetail.MyViewHolder> {


    private ArrayList<OrderDeliveryDetailArraySubSub> dataSet;
    Context context;
    public OnItemClickListenerOrderSuumnery listener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final MtplTextView mtplTextViewTotal;
        private final MtplTextView mtplTextViewDisAmt, totletxt;
        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        CardView cardView;
        MtplTextView textViewItemName, textViewItemPrice, textViewItemQuanti, orderSummeryStrickItemPrice, mtplTextViewDicount, textViewAddOn, itemTotal;
        MtplButton orderSummeryRemoveButton, orderSummeryEditButton;
        private final ImageView imageView;
        private final ProgressBar progressBar;

        ImageView imageViewPlace;
        MtplTextView orderSummeryProductCat, mtplTextViewDisAmtAfter;
        LinearLayout catLay;
        LinearLayout dicLayout, dicLayoutAfter, addonsLayout, totalLay;
        private final LinearLayout proTaxLay;
        private final MtplTextView txtproTax, addonstext;

        public MyViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            textViewItemName = (MtplTextView) itemView.findViewById(R.id.orderSummeryItemName);
            textViewItemPrice = (MtplTextView) itemView.findViewById(R.id.orderSummeryItemPrice);
            textViewItemQuanti = (MtplTextView) itemView.findViewById(R.id.orderSummeryItemQuanti);
            addonstext = (MtplTextView) itemView.findViewById(R.id.addonstext);
            orderSummeryRemoveButton = (MtplButton) itemView.findViewById(R.id.orderSummeryRemoveButton);
            mtplTextViewDisAmt = (MtplTextView) itemView.findViewById(R.id.discountAmt);
            addonsLayout = (LinearLayout) itemView.findViewById(R.id.addonsLayout);
            catLay = (LinearLayout) itemView.findViewById(R.id.catLay);
            proTaxLay = (LinearLayout) itemView.findViewById(R.id.proTaxLay);
            txtproTax = (MtplTextView) itemView.findViewById(R.id.proTax);
            dicLayout = (LinearLayout) itemView.findViewById(R.id.discLayout);
            dicLayoutAfter = (LinearLayout) itemView.findViewById(R.id.discLayoutAfter);
            totalLay = (LinearLayout) itemView.findViewById(R.id.totalLay);
            itemTotal = (MtplTextView) itemView.findViewById(R.id.itemTotal);
            mtplTextViewDisAmtAfter = (MtplTextView) itemView.findViewById(R.id.discountAmtAfter);
            textViewAddOn = (MtplTextView) itemView.findViewById(R.id.textViewAddOn);
            imageView = (ImageView) itemView.findViewById(R.id.list_image);
            mtplTextViewTotal = (MtplTextView) itemView.findViewById(R.id.totleItem);
            orderSummeryProductCat = (MtplTextView) itemView.findViewById(R.id.orderSummeryProductCat);
            totletxt = (MtplTextView) itemView.findViewById(R.id.totletxt);

            orderSummeryRemoveButton.setOnClickListener(this);
            imageViewPlace = (ImageView) itemView.findViewById(R.id.placeholder1);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressnar);
            //     mtplTextViewDicount = (MtplTextView) itemView.findViewById(R.id.orderSummerydiccount);
            // mtplTextViewDicount.setVisibility(View.VISIBLE);
            orderSummeryEditButton = (MtplButton) itemView.findViewById(R.id.orderSummeryEditButton);
            orderSummeryEditButton.setVisibility(View.GONE);
            // orderSummeryStrickItemPrice = (MtplTextView) itemView.findViewById(R.id.orderSummeryStrickItemPrice);
            cardView = (CardView) itemView.findViewById(R.id.placeCard);
            orderSummeryRemoveButton.setOnClickListener(this);
            orderSummeryEditButton.setOnClickListener(this);
           // boolean isLoggedIn = Singleton.getInstance().isUserLoggedIn();

        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.orderSummeryEditButton:
                    listener.onItemClick("", "", "edit", "");
                    break;
                case R.id.orderSummeryRemoveButton:
                    //listener.onItemClick(dataSet.get(getAdapterPosition()).nOrderID, dataSet.get(getAdapterPosition()).nOrderID, "remove");
                    break;
            }
        }
    }


    public void SetOnItemClickListener(OnItemClickListenerOrderSuumnery mItemClickListener) {

        this.listener = mItemClickListener;
    }

    public adptorderDetail(Context context, ArrayList<OrderDeliveryDetailArraySubSub> data) {
        this.dataSet = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterordersummary, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


        MtplTextView itemName = holder.textViewItemName;
        MtplTextView itemPrice = holder.textViewItemPrice;
        MtplTextView itemQuanti = holder.textViewItemQuanti;
        MtplButton removeItem = holder.orderSummeryRemoveButton;
        MtplTextView discount = holder.mtplTextViewDicount;
        ImageView imageView = holder.imageView;
        final ImageView imageView1 = holder.imageViewPlace;
        MtplTextView mtplTextViewAddOn = holder.textViewAddOn;
        MtplTextView itemToal = holder.mtplTextViewTotal;
        mtplTextViewAddOn.setSelected(true);
        final ProgressBar progressBar = holder.progressBar;
        MtplTextView mtplTextViewStrikeOf = holder.orderSummeryStrickItemPrice;
        MtplTextView mtplTextViewDis = holder.mtplTextViewDisAmt;
        itemName.setText(Html.fromHtml(dataSet.get(listPosition).cProductName + ""));
        LinearLayout addonsLayout = holder.addonsLayout;
        MtplTextView orderSummeryProductCat = holder.orderSummeryProductCat;
        MtplTextView addonstext = holder.addonstext;


        LinearLayout catLay = holder.catLay;

        LinearLayout totalLay = holder.totalLay;
        LinearLayout linearLayoutDis = holder.dicLayout;
        LinearLayout linearLayoutDisAfter = holder.dicLayoutAfter;
        MtplTextView itemTotal = holder.itemTotal;
        MtplTextView mtplTextViewDisAfter = holder.mtplTextViewDisAmtAfter;
        MtplTextView totletxt = holder.totletxt;
        LinearLayout taxLay = holder.proTaxLay;
        MtplTextView txttax = holder.txtproTax;
        catLay.setVisibility(View.GONE);
        Double perPrice = 0.00;
        try {
            if (dataSet.get(listPosition).nAddonTotalAmt != null) {
                perPrice = Double.parseDouble(dataSet.get(listPosition).nRate.toString()) + Double.parseDouble((dataSet.get(listPosition).nAddonTotalAmt.toString()));
            } else {
                perPrice = Double.parseDouble(dataSet.get(listPosition).nRate);
            }
        } catch (Exception e) {
            e.getMessage();

        }
        Double dis = 0.0;
        if (!CM.getSp(context, "bIsDiscountAfterTax", "").toString().equals("")) {
            if (CM.getSp(context, "bIsDiscountAfterTax", "").toString().equals("true")) {
                linearLayoutDis.setVisibility(View.GONE);
                if (dataSet.get(listPosition).nDiscountAmt != null && !dataSet.get(listPosition).nDiscountAmt.toString().equals("null")) {
                    dis = Double.parseDouble(dataSet.get(listPosition).nDiscountAmt);
                    mtplTextViewDisAfter.setText(CM.getSp(context, "currency", "").toString() + dataSet.get(listPosition).nDiscountAmt);
                } else {
                    dis = 0.0;
                    linearLayoutDisAfter.setVisibility(View.GONE);
                }
            } else {
                linearLayoutDisAfter.setVisibility(View.GONE);
                if (dataSet.get(listPosition).nDiscountAmt != null && !dataSet.get(listPosition).nDiscountAmt.toString().equals("null")) {
                    dis = Double.parseDouble(dataSet.get(listPosition).nDiscountAmt);
                    mtplTextViewDis.setText(CM.getSp(context, "currency", "").toString() + dataSet.get(listPosition).nDiscountAmt);
                } else {
                    dis = 0.0;
                    linearLayoutDis.setVisibility(View.GONE);
                }

            }

        } else {
            linearLayoutDisAfter.setVisibility(View.GONE);
            linearLayoutDis.setVisibility(View.GONE);

        }

        Double tax = 0.0;
        if (dataSet.get(listPosition).ProductTaxAmt != null) {
            if (dataSet.get(listPosition).ProductTaxAmt.toString().equals("0.00")) {
                taxLay.setVisibility(View.GONE);
                tax = 0.0;
            } else {
                txttax.setText(CM.getSp(context, "currency", "").toString() + "" + dataSet.get(listPosition).ProductTaxAmt.toString());
                tax = Double.parseDouble(dataSet.get(listPosition).ProductTaxAmt);
            }
        } else {
            taxLay.setVisibility(View.GONE);
        }

        Double gtot = 0.0;
        try {
            if (!CM.getSp(context, "bIsDiscountAfterTax", "").toString().equals("")) {
                if (CM.getSp(context, "bIsDiscountAfterTax", "").toString().equals("true")) {
                    gtot = Double.parseDouble(String.valueOf(perPrice)) - dis + tax;
                    itemTotal.setText(CM.getSp(context, "currency", "").toString() + String.valueOf(gtot));
                } else {
                    gtot = Double.parseDouble(String.valueOf(perPrice)) + tax - dis;
                    itemTotal.setText(CM.getSp(context, "currency", "").toString() + String.valueOf(gtot));
                }
            } else {
                totalLay.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }

        itemQuanti.setText(dataSet.get(listPosition).Qty);
        if (dataSet.get(listPosition).cAddons != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    mtplTextViewAddOn.setText((Html.fromHtml(dataSet.get(listPosition).cAddons, Html.FROM_HTML_MODE_LEGACY)));
                    addonstext.setText("AddOns");
                } catch (Exception e) {
                    addonsLayout.setVisibility(View.GONE);


                }
            } else {
                try {
                    mtplTextViewAddOn.setText(Html.fromHtml((dataSet.get(listPosition).cAddons)));
                    addonstext.setText("AddOns");
                } catch (Exception e) {
                    addonsLayout.setVisibility(View.GONE);
                }
            }

        } else {
            addonsLayout.setVisibility(View.GONE);
        }

        try {
            Double tot = Double.parseDouble(dataSet.get(listPosition).nSubTotalAmt) * Double.parseDouble(dataSet.get(listPosition).Qty);
            itemToal.setText(CM.getSp(context, "currency", "").toString() + String.valueOf(tot));
        } catch (Exception e) {

        }


        try {
            itemPrice.setText(CM.getSp(context, "currency", "").toString() + perPrice);
        } catch (Exception e) {
        }
        if (dataSet.get(listPosition).nDiscountAmt != null && !dataSet.get(listPosition).nDiscountAmt.toString().equals("null")) {
            if (dataSet.get(listPosition).nDiscountAmt.equals("0.00")) {
                mtplTextViewDis.setText(CM.getSp(context, "currency", "").toString() + "0.00");
            } else {
                mtplTextViewDis.setText(CM.getSp(context, "currency", "").toString() + dataSet.get(listPosition).nDiscountAmt);
            }
        } else {
            mtplTextViewDis.setVisibility(View.GONE);
        }
        if (dataSet.get(listPosition).Qty != null) {
            totletxt.setText("Total for " + dataSet.get(listPosition).Qty + " Item");
        } else {
            totletxt.setText("Total for " + "--" + " Item");
        }


        if (dataSet.get(listPosition).cProductImagePath != null) {


            Glide.with(context).load(dataSet.get(listPosition).cProductImagePath)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            imageView1.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            imageView1.setVisibility(View.GONE);
                            return false;
                        }
                    }).error(R.drawable.placeholder)
                    .into(imageView);
        }

        removeItem.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}