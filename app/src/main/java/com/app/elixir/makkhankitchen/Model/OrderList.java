package com.app.elixir.makkhankitchen.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NetSupport on 08-10-2016.
 */
public class OrderList {

    @SerializedName("TodayOrderConfirmList")
    public ArrayList<OrderListArray> orderListArrays = new ArrayList<OrderListArray>();
}
