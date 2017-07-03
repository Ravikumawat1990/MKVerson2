package com.app.elixir.makkhankitchen.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.Model.OrderDeliveryDetail;
import com.app.elixir.makkhankitchen.Model.OrderDeliveryDetailArray;
import com.app.elixir.makkhankitchen.Model.OrderDetailModelArray;
import com.app.elixir.makkhankitchen.adapter.adptorderDetail;
import com.app.elixir.makkhankitchen.interfac.ActionBarTitleSetter;
import com.app.elixir.makkhankitchen.interfac.OnFragmentInteractionListenerDelivery;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by NetSupport on 08-09-2016.
 */
public class FragOrderDetailView extends Fragment {

    private static final String TAG = "FragOrderhisDetailView";
    private OnFragmentInteractionListenerDelivery mListener;
    Activity thisActivity;
    private ArrayList<OrderDetailModelArray> detailModelArrays;


    private RecyclerView recycleView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private adptorderDetail mAdapter;
    private MtplTextView textViewTotalItems;
    private MtplTextView textViewItemTotal;
    private MtplTextView textViewSubTotal;
    private MtplTextView textViewVat;
    private MtplTextView textViewDeliveryType;
    private MtplTextView textViewtotalPayable;
    private MtplTextView textViewApplycoupon;
    private LinearLayout linearLayoutCouponApply;
    private ImageButton imageButtonRemove;
    private Button btnSubmit;
    private OrderDeliveryDetail model_main;
    private ArrayList<OrderDeliveryDetailArray> orderDeliveryArray;
    private MtplTextView pName;
    private MtplTextView deliveryStatus;
    private MtplTextView orderdate;
    private MtplTextView contactNo;
    private MtplTextView email;
    private MtplTextView txtAddress;
    private MtplTextView mtpllandMarkk;


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListenerDelivery) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.orderhistorydetail, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.orderhistorydetail));
        initView(rootView);
        return rootView;

    }

    public void initView(View rootView) {
        recycleView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(mStaggeredLayoutManager);
        textViewTotalItems = (MtplTextView) rootView.findViewById(R.id.textViewToalItems);
        textViewItemTotal = (MtplTextView) rootView.findViewById(R.id.textViewItemtTotal);
        textViewSubTotal = (MtplTextView) rootView.findViewById(R.id.textViewItemSubTotal);
        textViewVat = (MtplTextView) rootView.findViewById(R.id.textViewVat);
        textViewDeliveryType = (MtplTextView) rootView.findViewById(R.id.textViewDeliveryType);
        textViewtotalPayable = (MtplTextView) rootView.findViewById(R.id.totalPayable);
        textViewApplycoupon = (MtplTextView) rootView.findViewById(R.id.textviewapplycoupon);
        imageButtonRemove = (ImageButton) rootView.findViewById(R.id.removeCouponbtn);
        linearLayoutCouponApply = (LinearLayout) rootView.findViewById(R.id.layoutCouponApply);
        txtAddress = (MtplTextView) rootView.findViewById(R.id.txtAddress);
        txtAddress.setSelected(true);
        contactNo = (MtplTextView) rootView.findViewById(R.id.contactNo);
        btnSubmit = (Button) rootView.findViewById(R.id.btncontinuPayment);
        mtpllandMarkk = (MtplTextView) rootView.findViewById(R.id.txtlandmark);
        email = (MtplTextView) rootView.findViewById(R.id.email);
        pName = (MtplTextView) rootView.findViewById(R.id.textViewPname);
        deliveryStatus = (MtplTextView) rootView.findViewById(R.id.deliveryStatus);
        orderdate = (MtplTextView) rootView.findViewById(R.id.orderDate);
        String strtext = getArguments().getString("id");
        webCallOrderDtail(strtext);

    }


    public void webCallOrderDtail(String cusId) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getResponseForOrderDetail(v, cusId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForOrderHistory(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(thisActivity)) {
                        CM.showPopupCommonValidation(thisActivity, error, false);
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
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("ResponseCode").equals("202")) {
                model_main = CM.JsonParse(new OrderDeliveryDetail(), jsonObject.getString("ResponseObject"));
                orderDeliveryArray = model_main.orderDeliveryDetailArrays;
                orderDeliveryArray.size();
                mAdapter = new adptorderDetail(thisActivity, orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).orderDeliveryDetailArraySubSubs);
                recycleView.setAdapter(mAdapter);

                try {
                    textViewTotalItems.setText("ITEMS " + "( " + String.valueOf(orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).orderDeliveryDetailArraySubSubs.size()) + " )");
                } catch (Exception e) {

                }
                try {
                    textViewItemTotal.setText(CM.getSp(thisActivity, "currency", "").toString() + orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).nSubTotalAmt);

                } catch (Exception e) {

                }

                try {
                    textViewSubTotal.setText(CM.getSp(thisActivity, "currency", "").toString() + orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).nSubTotalAmt);
                } catch (Exception e) {

                }

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
                            if (jsonObject2.getString("cLandmark") != null && !jsonObject2.getString("cLandmark").toString().equals("null")) {
                                mtpllandMarkk.setText(jsonObject2.getString("cLandmark"));
                            }

                            txtAddress.setText(stringBuilder);
                        }
                    }
                }

                try {

                    contactNo.setText(orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).cMobile);
                } catch (Exception e) {

                }
                try {
                    pName.setText(orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).cCustomerName);

                } catch (Exception e) {

                }
                if (orderDeliveryArray.get(0).cOrderStatus != null) {
                    deliveryStatus.setText(orderDeliveryArray.get(0).cOrderStatus);

                } else {

                }

                try {
                    textViewtotalPayable.setText(CM.getSp(thisActivity, "currency", "").toString() + orderDeliveryArray.get(0).nAmount);

                } catch (Exception e) {

                }

                DateFormat readFormat = new SimpleDateFormat("hh:mm:ss");
                DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");

                try {
                    if (orderDeliveryArray.get(0).dOrderDate != null) {
                        orderdate.setText(CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", orderDeliveryArray.get(0).dOrderDate) + " " + dateFormat.format(readFormat.parse(orderDeliveryArray.get(0).dOrderTime)));
                    }
                } catch (Exception e) {
                    orderdate.setText("Date : " + "--");

                }
                if (orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).cEmail != null) {
                    email.setText(orderDeliveryArray.get(0).orderDeliveryDetailArraySubs.get(0).cEmail);
                }
            } else {

            }

        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);
    }
}
