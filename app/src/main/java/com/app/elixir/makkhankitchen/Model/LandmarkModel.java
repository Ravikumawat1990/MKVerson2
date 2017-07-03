package com.app.elixir.makkhankitchen.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NetSupport on 07-10-2016.
 */
public class LandmarkModel {

    @SerializedName("landmarks")
    public ArrayList<LandmarkModelArray> landmarkModelArrays = new ArrayList<LandmarkModelArray>();
}
