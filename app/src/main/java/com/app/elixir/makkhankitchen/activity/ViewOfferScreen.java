package com.app.elixir.makkhankitchen.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.app.elixir.makkhankitchen.Model.DummyData;
import com.app.elixir.makkhankitchen.Model.OfferModel;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.adapter.DifferentRowAdapter;
import com.app.elixir.makkhankitchen.adapter.OfferAdapter;
import com.app.elixir.makkhankitchen.utils.CM;

import java.util.ArrayList;

public class ViewOfferScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    OfferAdapter mAdapter;
    private ArrayList<OfferModel> offerModels;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offer_screen);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar.setNavigationIcon(R.drawable.ic_back_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CM.finishActivity(ViewOfferScreen.this);

            }
        });

        offerModels = new ArrayList<>();

        initView();
        CM.setSp(ViewOfferScreen.this, "data", "{\n" +
                "  \"ResponseCode\": 202,\n" +
                "  \"ResponseObject\": \"{\\\"product\\\":{\\\"cAppStoreCode\\\":null,\\\"cAPIKey\\\":null,\\\"cAPIPass\\\":null,\\\"nRowIndex\\\":0,\\\"nCompanyProductID\\\":1,\\\"nCompanyID\\\":0,\\\"nCustomerID\\\":0,\\\"nOrderFillMode\\\":0,\\\"nSerialNo\\\":1,\\\"nProductID\\\":1,\\\"cProductName\\\":\\\"Chicken Tikka Lahore\\\",\\\"cProductDesc\\\":\\\"BAR-BE-QUE CHICKEN MARINATED IN INDIAN SPICES<br />Medium = 6 pcs. <br />Full = 12 pcs. <br />Party pack = 18 pcs.\\\",\\\"cProductImagePath\\\":\\\"http://supportapi.elixirinfo.co.in/Images/ProductImages/Original/1273d80d-5fd5-4234-a67c-107553d2c603.jpg\\\",\\\"cCategoryName\\\":\\\"NON-VEG. STARTERS\\\",\\\"nPieces\\\":1,\\\"nProductCategoryID\\\":1,\\\"Qty\\\":0,\\\"nRate\\\":150.00,\\\"nAddonTotalAmt\\\":0.0,\\\"TotalAmount\\\":0.0,\\\"ProductSubTotalAmt\\\":0.0,\\\"cAddons\\\":null,\\\"cAddonsPrice\\\":null,\\\"ProductAssignedAddonsJSON\\\":null,\\\"cSpecialComments\\\":null,\\\"isfavourite\\\":false,\\\"ListProductAttributeMapper\\\":[{\\\"nMapperID\\\":1,\\\"nAttributeID\\\":1,\\\"cAttributeLabel\\\":\\\"Size\\\",\\\"isMultiple\\\":false,\\\"isRequired\\\":true,\\\"ListProductAttributeMapperDetail\\\":[{\\\"nAttributeID\\\":0,\\\"cAttributeLabel\\\":null,\\\"nMapperDetailID\\\":2,\\\"nMapperID\\\":1,\\\"nAttributeValueMasterID\\\":0,\\\"cAttributeValueLabel\\\":\\\"Medium\\\",\\\"nPrice\\\":0.00,\\\"nSerialNo\\\":1},{\\\"nAttributeID\\\":0,\\\"cAttributeLabel\\\":null,\\\"nMapperDetailID\\\":1,\\\"nMapperID\\\":1,\\\"nAttributeValueMasterID\\\":0,\\\"cAttributeValueLabel\\\":\\\"Full\\\",\\\"nPrice\\\":125.00,\\\"nSerialNo\\\":2},{\\\"nAttributeID\\\":0,\\\"cAttributeLabel\\\":null,\\\"nMapperDetailID\\\":3,\\\"nMapperID\\\":1,\\\"nAttributeValueMasterID\\\":0,\\\"cAttributeValueLabel\\\":\\\"Party Pack\\\",\\\"nPrice\\\":250.00,\\\"nSerialNo\\\":3}]}]}}\"\n" +
                "}");


        //   initData();
        //    initView();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            OfferModel model = new OfferModel();
            model.setOfferTile("Title" + i);
            model.setOfferDisc("Discription" + i);
            model.setOfferValidity("1" + i);
            model.setImageUrl("https://www.icicibank.com/managed-assets/images/offer-zone/shopping/amazon-svd-t.jpg");
            offerModels.add(model);

        }
    }

    private void initView() {
//        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        mAdapter = new OfferAdapter(offerModels);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);


        DifferentRowAdapter adapter = new DifferentRowAdapter(DummyData.getData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewOfferScreen.this);
    }
}
