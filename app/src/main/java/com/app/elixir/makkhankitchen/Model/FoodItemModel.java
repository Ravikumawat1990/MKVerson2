package com.app.elixir.makkhankitchen.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NetSupport on 20-09-2016.
 */
public class FoodItemModel {

    @SerializedName("product")
    public ArrayList<CategoryItemDetailModel> categoryItemDetailModels = new ArrayList<CategoryItemDetailModel>();

}
