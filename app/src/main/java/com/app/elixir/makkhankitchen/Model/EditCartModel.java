package com.app.elixir.makkhankitchen.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NetSupport on 21-02-2017.
 */

public class EditCartModel {
    @SerializedName("productDetail")
    public ArrayList<EditCartModelArray> editCartModelArrays = new ArrayList<EditCartModelArray>();

}

