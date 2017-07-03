package com.app.elixir.makkhankitchen.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.OrderDeliveryDetail;
import com.app.elixir.makkhankitchen.Model.OrderDeliveryDetailArray;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.adapter.adptCustomerorderDetail;
import com.app.elixir.makkhankitchen.mtplview.MtplLog;
import com.app.elixir.makkhankitchen.mtplview.MtplTextView;
import com.app.elixir.makkhankitchen.utils.CM;
import com.app.elixir.makkhankitchen.volly.OnVolleyHandler;
import com.app.elixir.makkhankitchen.volly.VolleyIntialization;
import com.app.elixir.makkhankitchen.volly.WebService;
import com.app.elixir.makkhankitchen.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewDeliveryBoyOrderDetail extends AppCompatActivity {

    private String TAG = "";
    private OrderDeliveryDetail model_main;
    private ArrayList<OrderDeliveryDetailArray> orderDeliveryArray;
    Toolbar toolbar;
    MtplTextView txtcustName, txtcustNumber, txtcustEmail, txtcustOrderId, txtcustOrderStatus, txtcustDateTime, txtcustAddress, txtcustAmount, txtcustDeliveryCharge;
    private LinearLayoutManager mLayoutManager;
    LinearLayout layoutDeliveryCharge;
    private RecyclerView mRecyclerView;
    String orderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_order_detail);
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
                CM.startActivity(ViewDeliveryBoyOrderDetail.this, ViewDeliveryBoy.class);
                finish();
            }
        });

        initView();


        if (CM.isInternetAvailable(ViewDeliveryBoyOrderDetail.this)) {
            if (CM.getSp(ViewDeliveryBoyOrderDetail.this, "logout", "").toString() != null && CM.getSp(ViewDeliveryBoyOrderDetail.this, "logout", "").toString().equals("1")) {
                Intent intent = new Intent(ViewDeliveryBoyOrderDetail.this, ViewSplash.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else {
                if (getIntent().getExtras() != null) {
                    orderId = getIntent().getExtras().getString("OrderID");
                    Log.i(TAG, "onCreate: FCM Noti");
                    webCallOrderDtail(orderId);

                } else {
                    Log.i(TAG, "onCreate: NORMAL");
                    CM.showPopupCommonValidation(ViewDeliveryBoyOrderDetail.this, "No Data Found", true);
                }
            }
        } else {
            CM.showToast(ViewDeliveryBoyOrderDetail.this, getString(R.string.internetMsg));
        }


    }

    public void initView() {
        txtcustName = (MtplTextView) findViewById(R.id.txtCustName);
        txtcustNumber = (MtplTextView) findViewById(R.id.txtCustNumber);
        txtcustEmail = (MtplTextView) findViewById(R.id.txtCustEmail);
        txtcustOrderId = (MtplTextView) findViewById(R.id.txtCustOrderId);
        txtcustOrderStatus = (MtplTextView) findViewById(R.id.txtCustOrderStats);
        txtcustDateTime = (MtplTextView) findViewById(R.id.txtCustOrderDandT);
        txtcustAddress = (MtplTextView) findViewById(R.id.txtCustAdress);
        txtcustAmount = (MtplTextView) findViewById(R.id.txtCustAmount);
        layoutDeliveryCharge = (LinearLayout) findViewById(R.id.layoutDeliveryCharge);
        txtcustDeliveryCharge = (MtplTextView) findViewById(R.id.txtCustDeliveryChareg);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(ViewDeliveryBoyOrderDetail.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }


    public void webCallOrderDtail(String cusId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewDeliveryBoyOrderDetail.this, true, true);
            WebService.getResponseForOrderDetail(v, cusId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForOrderHistory(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewDeliveryBoyOrderDetail.this)) {
                        CM.showPopupCommonValidation(ViewDeliveryBoyOrderDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForOrderHistory(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewDeliveryBoyOrderDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("ResponseCode").equals("202")) {
                model_main = CM.JsonParse(new OrderDeliveryDetail(), jsonObject.getString("ResponseObject"));
                orderDeliveryArray = model_main.orderDeliveryDetailArrays;
                orderDeliveryArray.size();


                txtcustName.setText(orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).cCustomerName);
                txtcustNumber.setText(orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).cMobile);
                txtcustEmail.setText(orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).cEmail);
                txtcustOrderId.setText(orderDeliveryArray.get(0).cOrderNo);
                txtcustOrderStatus.setText(orderDeliveryArray.get(0).cOrderStatus);
                if (orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).nDeliveryCharge != null && !orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).nDeliveryCharge.toString().equals("0.00")) {
                    layoutDeliveryCharge.setVisibility(View.VISIBLE);
                    txtcustDeliveryCharge.setText(orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).nDeliveryCharge);
                } else {
                    layoutDeliveryCharge.setVisibility(View.GONE);
                }


                DateFormat readFormat = new SimpleDateFormat("hh:mm:ss");
                DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                txtcustDateTime.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "MMM dd, yyyy", orderDeliveryArray.get(0).dOrderDate) + " " + dateFormat.format(readFormat.parse(orderDeliveryArray.get(0).dOrderTime)));
                //txtcustAddress.setText(orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).addressMods.get(0).cAddressLine1);
                txtcustAmount.setText(CM.getSp(ViewDeliveryBoyOrderDetail.this, "currency", "").toString() + " " + orderDeliveryArray.get(0).nAmount);


                adptCustomerorderDetail adptCustomerorderDetail = new adptCustomerorderDetail(ViewDeliveryBoyOrderDetail.this, orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).orderDeliveryDetailArraySubSubs);
                mRecyclerView.setAdapter(adptCustomerorderDetail);


                JSONObject jsonObject1 = new JSONObject(jsonObject.optString("ResponseObject"));
                JSONArray jsonArray = new JSONArray(jsonObject1.optString("OrderDetail"));

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONArray jsonArray1 = new JSONArray(jsonArray.getJSONObject(i).getString("tblOrderdetail"));
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject2 = new JSONObject(jsonArray1.getJSONObject(j).getString("cAddress"));


                            StringBuilder stringBuilder = new StringBuilder();
                            if (jsonObject2.getString("cAddressLine1") != null && !jsonObject2.getString("cAddressLine1").equals("null")) {
                                stringBuilder.append(jsonObject2.getString("cAddressLine1") + ",");
                            }
                            if (jsonObject2.getString("cAddressLine2") != null && !jsonObject2.getString("cAddressLine2").equals("null")) {
                                stringBuilder.append(jsonObject2.getString("cAddressLine2") + ",");
                            }
                            if (jsonObject2.getString("cCity") != null && !jsonObject2.getString("cCity").toString().equals("null")) {
                                stringBuilder.append(jsonObject2.getString("cCity") + ",");
                            }
                            if (jsonObject2.getString("cState") != null && !jsonObject2.getString("cState").toString().equals("null")) {
                                stringBuilder.append(jsonObject2.getString("cState"));
                            }
                            if (jsonObject2.getString("cZip") != null && !jsonObject2.getString("cZip").toString().equals("null")) {
                                stringBuilder.append(jsonObject2.getString("cZip"));
                            }
                            // if (jsonObject2.getString("cLandmark") != null && !jsonObject2.getString("cLandmark").toString().equals("null")) {
                            //    mtpllandMarkk.setText(jsonObject2.getString("cLandmark"));
                            //    }

                            txtcustAddress.setText(stringBuilder);
                        }
                    }
                }

            }

        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewDeliveryBoyOrderDetail.this, e.getMessage(), false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.startActivity(ViewDeliveryBoyOrderDetail.this, ViewDeliveryBoy.class);
        finish();
    }
}
