package com.app.elixir.makkhankitchen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * Created by Elixir on 02-Aug-2016.
 */
public class adptGrid extends BaseAdapter {
    private Context mContext;
    private final String[] string;
    private final int[] Imageid;

    public adptGrid(Context c, String[] string, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.string = string;
    }

    @Override
    public int getCount() {
        return string.length;
    }

    @Override
    public Object getItem(int p) {
        return null;
    }

    @Override
    public long getItemId(int p) {
        return 0;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}