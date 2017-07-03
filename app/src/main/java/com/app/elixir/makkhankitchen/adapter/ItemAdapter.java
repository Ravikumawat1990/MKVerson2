package com.app.elixir.makkhankitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.AppModel;
import com.app.elixir.makkhankitchen.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NetSupport on 07-02-2017.
 */

public class ItemAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater inflater;
    public List<AppModel> arraylistData = null;
    private ArrayList<AppModel> arraylist;
    ImageView checkBox;

    public ItemAdapter(Context context, List<AppModel> listbean, ImageView checkBox) {
        mContext = context;
        this.arraylistData = listbean;
        inflater = LayoutInflater.from(mContext);
        this.checkBox = checkBox;
        this.arraylist = new ArrayList<AppModel>();
        this.arraylist.addAll(listbean);

    }


    @Override
    public void onClick(View v) {

        if (v == checkBox) {

            if (checkBox.isSelected()) {
                checkBox.setSelected(false);
                for (int i = 0; i < arraylistData.size(); i++) {
                    arraylistData.get(i).setSelected(false);
                }
            } else {
                checkBox.setSelected(true);
                for (int i = 0; i < arraylistData.size(); i++) {
                    arraylistData.get(i).setSelected(true);
                }
            }

            notifyDataSetChanged();
        } else {
            int position = (Integer) v.getTag();

            v.getTag();
            //AppModel bean = arraylistData.get(position);
            if (!v.isSelected()) {
                arraylistData.get(position).setSelected(true);

            } else {
                arraylistData.get(position).setSelected(false);
                if (checkBox.isSelected()) {
                    checkBox.setSelected(false);
                    for (int i = 0; i < arraylistData.size(); i++) {
                        arraylistData.get(i).setSelected(true);
                        arraylistData.get(position).setSelected(false);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }

    public class ViewHolder {
        TextView txtItemName;
        ImageView imgvCheck;
    }

    @Override
    public int getCount() {
        return arraylistData.size();
    }

    @Override
    public AppModel getItem(int position) {
        return arraylistData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row_item, null);
            holder.txtItemName = (TextView) view.findViewById(R.id.txtItemName);
            holder.imgvCheck = (ImageView) view.findViewById(R.id.imgvCheck);
            holder.imgvCheck.setOnClickListener(this);
            checkBox.setOnClickListener(this);
            view.setTag(holder);
            view.setTag(R.id.imgvCheck, holder.imgvCheck);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Set data to view=================================================
        try {
            AppModel bean = arraylistData.get(position);

            if (bean.isSelected()) {
                holder.imgvCheck.setSelected(true);
            } else {
                holder.imgvCheck.setSelected(false);
            }

            holder.txtItemName.setText(bean.getAppName());
            holder.imgvCheck.setTag(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase();
        arraylistData.clear();
        if (charText.length() == 0) {
            arraylistData.addAll(arraylist);
        } else {
            for (AppModel contact : arraylist) {
                if (contact.getAppName().toLowerCase().contains(charText)) {
                    arraylistData.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }

}
