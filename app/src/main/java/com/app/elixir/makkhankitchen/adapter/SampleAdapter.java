package com.app.elixir.makkhankitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.R;


public class SampleAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    int count = 0;
    private final String[] mGroups = {
            "Cupcake",
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat"
    };

    private final int[] mGroupDrawables = {
            R.drawable.applogo,
            R.drawable.applogo,
            R.drawable.applogo,
            R.drawable.applogo,
            R.drawable.applogo,
            R.drawable.applogo,
            R.drawable.applogo,
            R.drawable.applogo,
            R.drawable.applogo
    };

    private final String[][] mChilds = {
            {"1.5"},
            {"1.6"},
            {"2.0", "2.0.1", "2.1"},
            {"2.2", "2.2.1", "2.2.2", "2.2.3"},
            {"2.3", "2.3.1", "2.3.2", "2.3.3", "2.3.4", "2.3.5", "2.3.6", "2.3.7"},
            {"3.0", "3.1", "3.2", "3.2.1", "3.2.2", "3.2.3", "3.2.4", "3.2.5", "3.2.6"},
            {"4.0", "4.0.1", "4.0.2", "4.0.3", "4.0.4"},
            {"4.1", "4.1.1", "4.1.2", "4.2", "4.2.1", "4.2.2", "4.3", "4.3.1"},
            {"4.4"}
    };
    GroupHolder holder;
    ChildHolder childholder;

    public SampleAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return mGroups.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups[groupPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.sample_activity_list_group_item, parent, false);
            holder = new GroupHolder();
            holder.itemHeaderName = (TextView) convertView.findViewById(R.id.sample_activity_list_group_item_text);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        holder.itemHeaderName.setText(mGroups[groupPosition]);


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChilds[groupPosition].length;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChilds[groupPosition][childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        childholder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.sample_activity_list_child_item, parent, false);
            childholder = new ChildHolder();
            childholder.itemName = (TextView) convertView.findViewById(R.id.sample_activity_list_child_item_text);
            childholder.itemPrice = (TextView) convertView.findViewById(R.id.txtAmount);
            childholder.btnDec = (ImageButton) convertView.findViewById(R.id.btnDec);
            childholder.btnInc = (ImageButton) convertView.findViewById(R.id.btnInc);
            convertView.setTag(childholder);
        } else {
            childholder = (ChildHolder) convertView.getTag();
        }


        childholder.itemName.setText(mChilds[groupPosition][childPosition]);
        childholder.btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                childholder.itemPrice.setText(String.valueOf(count));

            }
        });
        childholder.btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                childholder.itemPrice.setText(String.valueOf(count));
            }
        });


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class GroupHolder {
        TextView itemHeaderName;


    }

    private class ChildHolder {
        TextView itemName;
        TextView itemPrice;
        TextView itemQuantity;

        ImageButton btnInc;
        ImageButton btnDec;

    }
}
