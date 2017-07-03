package com.app.elixir.makkhankitchen.activity;

import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.CouponCodeModel;
import com.app.elixir.makkhankitchen.Model.CouponCodeModelArray;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.adapter.adptCouponCode;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListener;
import com.app.elixir.makkhankitchen.mtplview.MtplLog;
import com.app.elixir.makkhankitchen.mtplview.MtplTextView;
import com.app.elixir.makkhankitchen.pojo.PojoCouponCode;
import com.app.elixir.makkhankitchen.utils.CM;
import com.app.elixir.makkhankitchen.volly.OnVolleyHandler;
import com.app.elixir.makkhankitchen.volly.VolleyIntialization;
import com.app.elixir.makkhankitchen.volly.WebService;
import com.app.elixir.makkhankitchen.volly.WebServiceTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ViewCoupons extends AppCompatActivity {


    ArrayList<PojoCouponCode> arrayList;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private adptCouponCode mAdapter;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private ArrayList<CouponCodeModelArray> couponCodeModelArrays;
    private LinearLayout noInternetLayout;
    private LinearLayout internetLayout;
    public static final String KEY_STATUS = "status";
    private ImageView imageViewMsg;
    private MtplTextView txtViewMsg;
    private String TAG = "ViewCoupons";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_coupons);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.orderstatus));
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        toolbar.setTitleTextColor(Color.WHITE);

        TextView titleTextView = null;
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_robotolight_0));
            titleTextView.setTypeface(font);
            titleTextView.setTextSize(20);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CM.startActivity(ViewCoupons.this, ViewDrawerActivty.class);
                finish();
            }
        });

        init();
    }


    public void init() {
        arrayList = new ArrayList<>();
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        noInternetLayout = (LinearLayout) findViewById(R.id.noInternet);
        internetLayout = (LinearLayout) findViewById(R.id.Internet);
        imageViewMsg = (ImageView) findViewById(R.id.imageViewMessage);
        txtViewMsg = (MtplTextView) findViewById(R.id.textViewNoInterNet);
        if (CM.isInternetAvailable(ViewCoupons.this)) {
            webCallCouponCode();
            noInternetLayout.setVisibility(View.GONE);
            internetLayout.setVisibility(View.VISIBLE);
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            internetLayout.setVisibility(View.GONE);
            txtViewMsg.setText(getString(R.string.internetMsg));
            imageViewMsg.setBackgroundResource(R.drawable.group);
        }
    }


    public void webCallCouponCode() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCoupons.this, true, true);
            WebService.getResponseForCouPonCode(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCouponCode(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCoupons.this)) {
                        CM.showPopupCommonValidation(ViewCoupons.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getResponseForCouponCode(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCoupons.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("ResponseObject"));

            if (jsonObject.getString("ResponseCode").equals("202")) {
                if (jsonObject1.optString("lblNoDiscountCoupons") != null && !jsonObject1.optString("lblNoDiscountCoupons").equals("")) {
                    noInternetLayout.setVisibility(View.VISIBLE);
                    internetLayout.setVisibility(View.GONE);
                    txtViewMsg.setText(getString(R.string.noCoupons));
                    imageViewMsg.setBackgroundResource(R.drawable.no_records);

                } else {
                    CouponCodeModel model_main = CM.JsonParse(new CouponCodeModel(), jsonObject.getString("ResponseObject"));
                    couponCodeModelArrays = model_main.couponCodeModelArrays;
                    couponCodeModelArrays.size();
                    mAdapter = new adptCouponCode(ViewCoupons.this, couponCodeModelArrays);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.SetOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(String value) {
                            CM.setClipboard(ViewCoupons.this, value);
                            CM.showToast(ViewCoupons.this, "Text Copied");
                        }
                    });
                }
            } else {
                CM.showToast(ViewCoupons.this, jsonObject.getString("ResponseObject").toString());
            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCoupons.this, e.getMessage(), false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.startActivity(ViewCoupons.this, ViewDrawerActivty.class);
        finish();
    }
}
