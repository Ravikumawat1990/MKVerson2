package com.app.elixir.makkhankitchen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.NotificationModel;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adptNotification extends RecyclerView.Adapter<adptNotification.MyViewHolder> {


    private ArrayList<NotificationModel> dataSet;
    Context context;
    public OnItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ExpandableTextView expTv1;
        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView textViewOffer;
        public ImageView placeImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            textViewOffer = (TextView) itemView.findViewById(R.id.textViewOffer);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            expTv1 = (ExpandableTextView) itemView
                    .findViewById(R.id.expand_text_view);

            placeNameHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(String.valueOf(dataSet.get(getAdapterPosition())));
        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }

    public adptNotification(Context context, ArrayList<NotificationModel> data) {
        this.dataSet = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapternotifiaction, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewTitle = holder.textViewOffer;
        holder.expTv1.setText(context.getString(R.string.dummy));

        if (dataSet.get(listPosition).getTitle() != null) {
            textViewTitle.setText(dataSet.get(listPosition).getTitle());
        } else {
            textViewTitle.setText("--");
        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
