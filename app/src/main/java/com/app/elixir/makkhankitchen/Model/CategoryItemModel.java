package com.app.elixir.makkhankitchen.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NetSupport on 30-08-2016.
 */
public class CategoryItemModel {

    @SerializedName("productlist")
    public ArrayList<CategoryItemModelArray> categoryItemModelArrays = new ArrayList<CategoryItemModelArray>();
}
