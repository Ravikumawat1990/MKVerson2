package com.app.elixir.makkhankitchen.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListenerFoodItem;
import com.app.elixir.makkhankitchen.mtplview.MtplTextView;
import com.app.elixir.makkhankitchen.pojo.PojoCategoryListing;
import com.app.elixir.makkhankitchen.utils.CM;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

/**
 * Created by Elixir on 09-Aug-2016.
 */
public class adptCategoryListing extends RecyclerView.Adapter<adptCategoryListing.MyViewHolder> {


    private ArrayList<PojoCategoryListing> dataSet;
    Context context;
    public OnItemClickListenerFoodItem listener;
    private ArrayList<PojoCategoryListing> arraylist;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ProgressBar progressBar;
        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public MtplTextView placeName;
        public ImageView placeImage;
        LinearLayout relativeLayoutAddtoCart, addtoFavLayout;
        public CardView cardView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (MtplTextView) itemView.findViewById(R.id.placeName);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
            cardView = (CardView) itemView.findViewById(R.id.placeCard);
            relativeLayoutAddtoCart = (LinearLayout) itemView.findViewById(R.id.addtocart);
            addtoFavLayout = (LinearLayout) itemView.findViewById(R.id.addtoFavLayout);

            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            imageView = (ImageView) itemView.findViewById(R.id.placeholder1);
            relativeLayoutAddtoCart.setOnClickListener(this);
            cardView.setOnClickListener(this);
            addtoFavLayout.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.placeCard) {
                listener.onItemClick("detail", this.getPosition());
            } else if (view.getId() == R.id.addtoFavLayout) {
                listener.onItemClick("add", this.getPosition());
            }
        }
    }

    public void update(ArrayList<PojoCategoryListing> modelList) {
        arraylist.clear();
        /// arraylist.clear();
        //this.arraylist = new ArrayList<PojoCategoryListing>();
        // this.arraylist.addAll(modelList);
        //  for (PojoCategoryListing model : modelList) {
        //     arraylist.add(model);
        // }
        arraylist.addAll(modelList);
        notifyDataSetChanged();
    }

    public void SetOnItemClickListener(OnItemClickListenerFoodItem mItemClickListener) {

        this.listener = mItemClickListener;
    }

    public adptCategoryListing(Context context, ArrayList<PojoCategoryListing> data) {
        this.dataSet = data;
        this.context = context;
        this.arraylist = new ArrayList<PojoCategoryListing>();
        this.arraylist.addAll(data);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptercategorylisting, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int listPosition) {

        MtplTextView textViewTitle = holder.placeName;
        textViewTitle.setSelected(true);
        CardView cardView = holder.cardView;
        ImageView imageView = holder.placeImage;
        textViewTitle.setText(dataSet.get(listPosition).getName());
        final ProgressBar progressBar = holder.progressBar;
        CM.getDeviceWidth((Activity) context);
        LinearLayout placeHolder = holder.placeHolder;
        LinearLayout linearLayout = holder.relativeLayoutAddtoCart;
        final ImageView imageViewPlace = holder.imageView;


        if (dataSet.get(listPosition).getIsActive() != null) {
            if (dataSet.get(listPosition).getIsActive().equals("true")) {
                cardView.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
                textViewTitle.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                placeHolder.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                cardView.setVisibility(View.GONE);
                textViewTitle.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                placeHolder.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);

            }
        }


        if (dataSet.get(listPosition).getImagePath() != null) {
            Glide.with(context)
                    .load(dataSet.get(listPosition).getImagePath()).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            imageViewPlace.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            imageViewPlace.setVisibility(View.GONE);
                            return false;
                        }
                    }).error(R.drawable.longplace)
                    .into(imageView);

            Animation scale = AnimationUtils.loadAnimation(context, R.anim.zoom);
            imageViewPlace.startAnimation(scale);
        }


    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase();
        dataSet.clear();
        if (charText.length() == 0) {
            dataSet.addAll(arraylist);
        } else {
            for (PojoCategoryListing contact : arraylist) {
                if (contact.getName().toLowerCase().contains(charText)) {
                    dataSet.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}