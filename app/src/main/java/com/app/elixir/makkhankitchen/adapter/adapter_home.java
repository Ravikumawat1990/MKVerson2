package com.app.elixir.makkhankitchen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.CategoryModelArray;
import com.app.elixir.makkhankitchen.Model.DetailModel;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by Elixir on 08-Aug-2016.
 */
public class adapter_home extends RecyclerView.Adapter<adapter_home.MyViewHolder> {


    ArrayList<DetailModel> dataSet;
    Context context;
    public OnItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_serial, txt_comp_name, txt_date, txt_report_type, txt_status;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_serial = (TextView) itemView.findViewById(R.id.txt_serial_no);
            txt_comp_name = (TextView) itemView.findViewById(R.id.txt_compName);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_report_type = (TextView) itemView.findViewById(R.id.txt_report_Type);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);


        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(dataSet.get(getAdapterPosition()).getCompName());
        }
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {

        this.listener = mItemClickListener;
    }

    public adapter_home(Context context, ArrayList<DetailModel> data) {
        dataSet = data;
        this.context = context;


    }

    public void update(ArrayList<CategoryModelArray> data) {

        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView serial = holder.txt_serial;
        TextView compName = holder.txt_comp_name;
        TextView date = holder.txt_date;
        TextView reportType = holder.txt_report_type;
        TextView status = holder.txt_status;

        serial.setText(dataSet.get(0).getSerNo());
        compName.setText(dataSet.get(0).getCompName());
        date.setText(dataSet.get(0).getDate());
        reportType.setText(dataSet.get(0).getReportType());
        status.setText(dataSet.get(0).getStatus());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
