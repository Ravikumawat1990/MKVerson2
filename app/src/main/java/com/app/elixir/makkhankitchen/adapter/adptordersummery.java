package com.app.elixir.makkhankitchen.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListenerOrderSuumnery;
import com.app.elixir.makkhankitchen.mtplview.MtplButton;
import com.app.elixir.makkhankitchen.mtplview.MtplTextView;
import com.app.elixir.makkhankitchen.pojo.OrderSummeryPojoArray;
import com.app.elixir.makkhankitchen.utils.CM;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Elixir on 09-Aug-2016.
 */
public class adptordersummery extends RecyclerView.Adapter<adptordersummery.MyViewHolder> {


    private ArrayList<OrderSummeryPojoArray> dataSet;
    Context context;
    public OnItemClickListenerOrderSuumnery listener;

    int count = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final MtplTextView mtplTextViewTotal;
        private final LinearLayout proTaxLay;
        private final MtplTextView txtproTax;
        private final MtplTextView totletxt;
        private final LinearLayout totalLay;
        private final MtplTextView addonstext;
        public MtplTextView mtplTextViewProductCat;
        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        CardView cardView;
        MtplTextView textViewItemName, textViewItemPrice, textViewItemQuanti, orderSummeryStrickItemPrice, mtplTextViewDicount, txtViewAddOn, itemTotal;
        MtplButton orderSummeryRemoveButton, orderSummeryEditButton;
        private final ImageView imageViewPlace;
        private final ProgressBar progressBar;
        ImageView imageView;
        LinearLayout dicLayout, dicLayoutAfter, addonsLayout;
        MtplTextView mtplTextViewDisAmt, mtplTextViewDisAmtAfter;
        ImageButton btnIncrement, btnDecrement;

        public MyViewHolder(View itemView) {
            super(itemView);

            btnIncrement = (ImageButton) itemView.findViewById(R.id.btnIncrement);
            btnDecrement = (ImageButton) itemView.findViewById(R.id.btnDecrement);

            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            textViewItemName = (MtplTextView) itemView.findViewById(R.id.orderSummeryItemName);
            textViewItemName.setSelected(true);
            textViewItemPrice = (MtplTextView) itemView.findViewById(R.id.orderSummeryItemPrice);
            textViewItemQuanti = (MtplTextView) itemView.findViewById(R.id.orderSummeryItemQuanti);
            orderSummeryRemoveButton = (MtplButton) itemView.findViewById(R.id.orderSummeryRemoveButton);
            mtplTextViewProductCat = (MtplTextView) itemView.findViewById(R.id.orderSummeryProductCat);
            mtplTextViewProductCat.setSelected(true);
            mtplTextViewDisAmt = (MtplTextView) itemView.findViewById(R.id.discountAmt);
            mtplTextViewDisAmtAfter = (MtplTextView) itemView.findViewById(R.id.discountAmtAfter);
            itemTotal = (MtplTextView) itemView.findViewById(R.id.itemTotal);
            totletxt = (MtplTextView) itemView.findViewById(R.id.totletxt);
            addonstext = (MtplTextView) itemView.findViewById(R.id.addonstext);


            dicLayout = (LinearLayout) itemView.findViewById(R.id.discLayout);
            dicLayoutAfter = (LinearLayout) itemView.findViewById(R.id.discLayoutAfter);
            totalLay = (LinearLayout) itemView.findViewById(R.id.totalLay);


            addonsLayout = (LinearLayout) itemView.findViewById(R.id.addonsLayout);
            proTaxLay = (LinearLayout) itemView.findViewById(R.id.proTaxLay);
            txtproTax = (MtplTextView) itemView.findViewById(R.id.proTax);

            mtplTextViewTotal = (MtplTextView) itemView.findViewById(R.id.totleItem);


            txtViewAddOn = (MtplTextView) itemView.findViewById(R.id.textViewAddOn);
            imageView = (ImageView) itemView.findViewById(R.id.list_image);
            orderSummeryRemoveButton.setOnClickListener(this);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressnar);
            //     mtplTextViewDicount = (MtplTextView) itemView.findViewById(R.id.orderSummerydiccount);
            // mtplTextViewDicount.setVisibility(View.VISIBLE);
            orderSummeryEditButton = (MtplButton) itemView.findViewById(R.id.orderSummeryEditButton);
            //orderSummeryStrickItemPrice = (MtplTextView) itemView.findViewById(R.id.orderSummeryStrickItemPrice);
            cardView = (CardView) itemView.findViewById(R.id.placeCard);
            imageViewPlace = (ImageView) itemView.findViewById(R.id.placeholder1);
            orderSummeryRemoveButton.setOnClickListener(this);
            orderSummeryEditButton.setOnClickListener(this);
            btnIncrement.setOnClickListener(this);
            btnDecrement.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            count = Integer.parseInt(textViewItemQuanti.getText().toString());
            switch (view.getId()) {
                case R.id.orderSummeryEditButton:
                    listener.onItemClick(dataSet.get(getAdapterPosition()).nCartID, dataSet.get(getAdapterPosition()).nCartDetailID, "edit", dataSet.get(getAdapterPosition()).nCompanyProductID);
                    break;
                case R.id.orderSummeryRemoveButton:
                    listener.onItemClick(dataSet.get(getAdapterPosition()).nCartID, dataSet.get(getAdapterPosition()).nCartDetailID, "remove", dataSet.get(getAdapterPosition()).nCompanyProductID);
                    break;
                case R.id.btnDecrement:

                    if (!dataSet.get(getAdapterPosition()).getQty().equals("")) {
                        count++;
                        // int quantity = Integer.parseInt(dataSet.get(getAdapterPosition()).getQty());
                        //int totquantity = quantity + count;
                        if (count >= 1) {
                            textViewItemQuanti.setText(String.valueOf(count));

                        }
                    }

                    break;
                case R.id.btnIncrement:

                    if (!dataSet.get(getAdapterPosition()).getQty().equals("")) {
                        count--;
                        // int quantity = Integer.parseInt(textViewItemQuanti.getText().toString());
                        // int totquantity = quantity + count;
                        if (count >= 1) {
                            textViewItemQuanti.setText(String.valueOf(count));
                        }
                    }


                    break;
            }
        }
    }


    public void SetOnItemClickListener(OnItemClickListenerOrderSuumnery mItemClickListener) {

        this.listener = mItemClickListener;
    }

    public adptordersummery(Context context, ArrayList<OrderSummeryPojoArray> data) {
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
        MtplTextView mtplTextViewProductCat = holder.mtplTextViewProductCat;
        LinearLayout addonsLayout = holder.addonsLayout;
        LinearLayout totalLay = holder.totalLay;
        MtplTextView addonstext = holder.addonstext;


        MtplTextView mtplTextViewAddOn = holder.txtViewAddOn;
        mtplTextViewAddOn.setSelected(true);
        final ProgressBar progressBar = holder.progressBar;
        MtplTextView mtplTextViewStrikeOf = holder.orderSummeryStrickItemPrice;
        LinearLayout linearLayoutDis = holder.dicLayout;
        LinearLayout linearLayoutDisAfter = holder.dicLayoutAfter;
        MtplTextView mtplTextViewDis = holder.mtplTextViewDisAmt;
        MtplTextView mtplTextViewDisAfter = holder.mtplTextViewDisAmtAfter;
        MtplTextView itemTotal = holder.itemTotal;
        MtplTextView totletxt = holder.totletxt;


        LinearLayout taxLay = holder.proTaxLay;
        MtplTextView txttax = holder.txtproTax;

        DecimalFormat form = new DecimalFormat("0.00");
        MtplTextView itemToal = holder.mtplTextViewTotal;
        itemName.setText(dataSet.get(listPosition).cProductName);


        ImageButton btnIncrement = holder.btnIncrement;
        ImageButton btnDecrement = holder.btnDecrement;


        Double perPrice = 0.00;
        try {
            if (dataSet.get(listPosition).nAddonTotalAmt != null) {
                perPrice = Double.parseDouble(dataSet.get(listPosition).nRate) + Double.parseDouble(dataSet.get(listPosition).nAddonTotalAmt);
            } else {
                perPrice = Double.parseDouble(dataSet.get(listPosition).nRate);
            }
        } catch (Exception e) {

        }


        Double tax = 0.00;
        if (dataSet.get(listPosition).getProductTaxAmt() != null) {
            if (dataSet.get(listPosition).getProductTaxAmt().toString().equals("0")) {
                taxLay.setVisibility(View.GONE);
                tax = 0.00;
            } else {

                txttax.setText(CM.getSp(context, "currency", "").toString() + "" + form.format(Double.parseDouble(dataSet.get(listPosition).getProductTaxAmt().toString())));
                tax = Double.parseDouble(dataSet.get(listPosition).getProductTaxAmt());
            }
        } else {
            taxLay.setVisibility(View.GONE);
        }
        Double dis = 0.00;
        if (!CM.getSp(context, "bIsDiscountAfterTax", "").toString().equals("")) {
            if (CM.getSp(context, "bIsDiscountAfterTax", "").toString().equals("true")) {
                linearLayoutDis.setVisibility(View.GONE);
                if (dataSet.get(listPosition).nDiscountAmt != null && !dataSet.get(listPosition).nDiscountAmt.toString().equals("null")) {
                    dis = Double.parseDouble(dataSet.get(listPosition).nDiscountAmt);

                    try {
                        mtplTextViewDisAfter.setText(CM.getSp(context, "currency", "").toString() + form.format(Double.parseDouble(dataSet.get(listPosition).nDiscountAmt)));
                    } catch (Exception e) {

                    }

                } else {
                    dis = 0.00;
                    linearLayoutDisAfter.setVisibility(View.GONE);
                }
            } else {
                linearLayoutDisAfter.setVisibility(View.GONE);
                if (dataSet.get(listPosition).nDiscountAmt != null && !dataSet.get(listPosition).nDiscountAmt.toString().equals("null")) {
                    try {
                        dis = Double.parseDouble(dataSet.get(listPosition).nDiscountAmt);
                        mtplTextViewDis.setText(CM.getSp(context, "currency", "").toString() + form.format(Double.parseDouble(dataSet.get(listPosition).nDiscountAmt)));
                    } catch (Exception e) {

                    }
                } else {
                    dis = 0.00;
                    linearLayoutDis.setVisibility(View.GONE);
                }

            }

        } else {
            linearLayoutDisAfter.setVisibility(View.GONE);
            linearLayoutDis.setVisibility(View.GONE);

        }

        try {
            totletxt.setText("Total for " + dataSet.get(listPosition).Qty + " Item");
            itemPrice.setText(CM.getSp(context, "currency", "").toString() + form.format(perPrice));

        } catch (Exception e) {

        }
        if (dataSet.get(listPosition).Qty != null) {
            itemQuanti.setText(dataSet.get(listPosition).Qty);
        } else {
            itemQuanti.setText("--");
        }

        if (dataSet.get(listPosition).ProductTotalAmount != null) {
            try {
                itemToal.setText(CM.getSp(context, "currency", "").toString() + form.format(Double.parseDouble(dataSet.get(listPosition).ProductTotalAmount)));
            } catch (Exception e) {

            }

        } else {
            itemToal.setText(CM.getSp(context, "currency", "").toString() + "0.00");
        }


        if (dataSet.get(listPosition).getProductCatName() != null) {
            mtplTextViewProductCat.setText(dataSet.get(listPosition).getProductCatName());
        } else {
            mtplTextViewProductCat.setText("--");
        }


        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dataSet.get(listPosition).getOrderSummeryPojoArraySubs().size(); i++) {
            String header = dataSet.get(listPosition).getOrderSummeryPojoArraySubs().get(i).cAttributeLabel;
            for (int j = 0; j < dataSet.get(listPosition).getOrderSummeryPojoArraySubs().get(i).getOrderSummeryPojoArraySubSubs().size(); j++) {

                try {
                    addonstext.setText(dataSet.get(listPosition).getOrderSummeryPojoArraySubs().get(i).getOrderSummeryPojoArraySubSubs().get(j).getcAttributeLabel() + " :");
                } catch (Exception e) {

                }
                if (dataSet.get(listPosition).getOrderSummeryPojoArraySubs().get(i).getOrderSummeryPojoArraySubSubs().get(j).getnPrice().toString() != null && dataSet.get(listPosition).getOrderSummeryPojoArraySubs().get(i).getOrderSummeryPojoArraySubSubs().get(j).getnPrice().toString().equals("0")) {
                } else {
                    if (!dataSet.get(listPosition).getOrderSummeryPojoArraySubs().get(i).getOrderSummeryPojoArraySubSubs().get(j).getnPrice().toString().equals("null")) {
                        stringBuilder.append(dataSet.get(listPosition).getOrderSummeryPojoArraySubs().get(i).getOrderSummeryPojoArraySubSubs().get(j).getcAttributeValueLabel());
                    } else {
                        stringBuilder.append(dataSet.get(listPosition).getOrderSummeryPojoArraySubs().get(i).getOrderSummeryPojoArraySubSubs().get(j).getcAttributeValueLabel());
                    }
                }
                stringBuilder.append(System.getProperty("line.separator"));
            }


        }


        if (stringBuilder != null && !stringBuilder.toString().equals("")) {
            mtplTextViewAddOn.setText(stringBuilder);

        } else {
            addonsLayout.setVisibility(View.GONE);
        }

        Double tot = 0.00;

        try {
            if (!CM.getSp(context, "bIsDiscountAfterTax", "").toString().equals("")) {
                if (CM.getSp(context, "bIsDiscountAfterTax", "").toString().equals("true")) {
                    tot = Double.parseDouble(String.valueOf(perPrice)) - dis + tax;
                    itemTotal.setText(CM.getSp(context, "currency", "").toString() + String.valueOf(form.format(tot)));
                } else {
                    tot = Double.parseDouble(String.valueOf(perPrice)) + tax - dis;
                    itemTotal.setText(CM.getSp(context, "currency", "").toString() + String.valueOf(form.format(tot)));
                }
            } else {
                totalLay.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }

        if (dataSet.get(listPosition).Qty != null) {
            totletxt.setText("Total for " + dataSet.get(listPosition).Qty + " Item");
        } else {
            totletxt.setText("Total for " + "0" + " Item");
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
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}