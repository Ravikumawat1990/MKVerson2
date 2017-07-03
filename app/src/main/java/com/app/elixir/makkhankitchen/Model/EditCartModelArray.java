package com.app.elixir.makkhankitchen.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NetSupport on 21-02-2017.
 */

public class EditCartModelArray {


    @SerializedName("cartItems")
    public ArrayList<EditCartModelArraySub> editCartModelArraySubs = new ArrayList<EditCartModelArraySub>();


}
