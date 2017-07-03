package com.app.elixir.makkhankitchen.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.Model.CityModel;
import com.app.elixir.makkhankitchen.Model.CityModelArray;
import com.app.elixir.makkhankitchen.Model.CompanyModel;
import com.app.elixir.makkhankitchen.Model.CompanyModelArray;
import com.app.elixir.makkhankitchen.Model.LandmarkModel;
import com.app.elixir.makkhankitchen.Model.LandmarkModelArray;
import com.app.elixir.makkhankitchen.Model.PayModeMode;
import com.app.elixir.makkhankitchen.Model.PayModeModeArray;
import com.app.elixir.makkhankitchen.Model.StateModel;
import com.app.elixir.makkhankitchen.Model.StateModelArray;
import com.app.elixir.makkhankitchen.Model.UserProfileModel;
import com.app.elixir.makkhankitchen.Model.UserProfileModelArray;
import com.app.elixir.makkhankitchen.adapter.CustomAdapterCity;
import com.app.elixir.makkhankitchen.adapter.CustomAdapterPayMode;
import com.app.elixir.makkhankitchen.adapter.CustomAdapterState;
import com.app.elixir.makkhankitchen.adapter.CustomSpinnerAdapterLandMark;
import com.app.elixir.makkhankitchen.mtplview.MtplLog;
import com.app.elixir.makkhankitchen.utils.CM;
import com.app.elixir.makkhankitchen.utils.CONSTANT;
import com.app.elixir.makkhankitchen.utils.CV;
import com.app.elixir.makkhankitchen.utils.URLS;
import com.app.elixir.makkhankitchen.volly.OnVolleyHandler;
import com.app.elixir.makkhankitchen.volly.VolleyIntialization;
import com.app.elixir.makkhankitchen.volly.WebService;
import com.app.elixir.makkhankitchen.volly.WebServiceTag;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.app.elixir.makkhankitchen.R.id.radioButton4;


public class ViewCustomerDetail extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "ViewCustomerDetail";
    private Toolbar toolbar;
    private Button buttonPlaceOder;


    private EditText edtName, edtMobileNo, edtMobilNo2, edtEmail, edtZipCodeBilling, edtZipCodeBillingDelivery, editTextDeliveryAddressOne, editTextDeliveryAddressSec;
    private Spinner spinnerLocation;
    private EditText edtBillingAdDressOne, edtBillingAdDressSec;

    Spinner spinnerStateBillingAddress, spinnerCityBillingAddress;
    Spinner spinnerStateDeliveryAddress, spinnerCityDeliveryAddress;


    String[] validation = {"Name", "Email", "Delivery Address"};


    private ArrayList<StateModelArray> stateModelArraysDelivery;
    private ArrayList<CityModelArray> cityModelArraysDelivery;

    private ArrayList<StateModelArray> stateModelArraysBilling;
    private ArrayList<CityModelArray> cityModelArraysBillling;


    private ArrayList<PayModeModeArray> payModeModeArrays;


    private int selectedCity = 0;
    private int selectedSate = 0;
    private int selectedCityDelivery = 0;
    private int selectedSateDelivery = 0;
    private ArrayList<UserProfileModelArray> userProfileModelArrays;
    private ArrayList<LandmarkModelArray> landmarkModelArrays;
    private ProgressDialog mProgressDialog;
    private EditText editTextCommet;
    private ArrayList<CompanyModelArray> companyModelArrays;
    private CompanyModel model_main;
    RadioGroup radioGroup;
    RadioButton radioButton, radioButton1;
    private JSONObject jsonObject;
    private String OrderId;
    private Spinner spinnerPayMode;
    private RelativeLayout layoutPayBorder;
    private CustomAdapterPayMode customAdapterPayMode;
    private RelativeLayout copyAddress;


    private CustomAdapterState adptDeliveryState;
    private CustomAdapterCity adptBillingCity;
    private CustomAdapterState adptBillingState;
    private RadioGroup payModeGrop;
    private RadioButton radioDelivery;
    private RadioButton radioTakeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_verification_detail2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My title");
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewCustomerDetail.this);
                hideSoftKeyboard();

            }
        });
        toolbar.setTitle(getString(R.string.cust_detail));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.titleColor));


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
        initView();
    }

    public void initView() {
        edtName = (EditText) findViewById(R.id.verificationName);
        edtMobileNo = (EditText) findViewById(R.id.verificationMobileNo);
        edtMobilNo2 = (EditText) findViewById(R.id.verificationMobileNo2);
        edtEmail = (EditText) findViewById(R.id.verificationEmail);


        edtBillingAdDressOne = (EditText) findViewById(R.id.edtbillingAddressOne);
        edtBillingAdDressSec = (EditText) findViewById(R.id.edtbillingTwo);


        spinnerStateBillingAddress = (Spinner) findViewById(R.id.spinnersatte);
        spinnerCityBillingAddress = (Spinner) findViewById(R.id.spinnercity);
        edtZipCodeBilling = (EditText) findViewById(R.id.verificationZipCode);


        editTextDeliveryAddressOne = (EditText) findViewById(R.id.verificationDeliveryAddres);
        editTextDeliveryAddressSec = (EditText) findViewById(R.id.verificationDeliveryAddres2);
        spinnerStateDeliveryAddress = (Spinner) findViewById(R.id.spinnerDeliverystate);
        spinnerCityDeliveryAddress = (Spinner) findViewById(R.id.spinnerDeliverycity);
        edtZipCodeBillingDelivery = (EditText) findViewById(R.id.verificationDeliveryZip);
        copyAddress = (RelativeLayout) findViewById(R.id.copyLayoutnew);
        copyAddress.setOnClickListener(this);

        spinnerLocation = (Spinner) findViewById(R.id.userLocation);
        buttonPlaceOder = (Button) findViewById(R.id.btnPlaceOrder);
        editTextCommet = (EditText) findViewById(R.id.conatctUsCommnent);

        buttonPlaceOder.setOnClickListener(this);

        spinnerStateBillingAddress.setOnTouchListener(spinnerOnTouchState);
        spinnerCityBillingAddress.setOnTouchListener(spinnerOnTouchCity);

        spinnerStateDeliveryAddress.setOnTouchListener(spinnerOnTouchStateDelivery);
        spinnerCityDeliveryAddress.setOnTouchListener(spinnerOnTouchCityDelivery);
        spinnerLocation.setOnTouchListener(spinnerOnTouchLandMark);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton2);


        payModeGrop = (RadioGroup) findViewById(R.id.radioGroup1);
        radioDelivery = (RadioButton) findViewById(R.id.radioButton3);
        radioTakeOut = (RadioButton) findViewById(R.id.radioButton4);


        spinnerPayMode = (Spinner) findViewById(R.id.payMode);
        spinnerPayMode.setOnTouchListener(spinnerOnTouchPay);
        layoutPayBorder = (RelativeLayout) findViewById(R.id.payModeBorder);
        layoutPayBorder.setOnClickListener(this);


        if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
            webCallCustomerDetail(CM.getSp(ViewCustomerDetail.this, "customerId", "").toString());
        } else {
            CM.showToast(ViewCustomerDetail.this, getString(R.string.internetMsg));
        }
    }

    private View.OnTouchListener spinnerOnTouchPay = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                webCallPayMode();
            }
            return false;
        }
    };

    private View.OnTouchListener spinnerOnTouchLandMark = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {

                webCallLandMark();
            }
            return false;
        }
    };

    private View.OnTouchListener spinnerOnTouchState = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                webCallState();
            }
            return false;
        }
    };
    private View.OnTouchListener spinnerOnTouchCity = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (stateModelArraysDelivery != null) {
                    if (stateModelArraysDelivery.size() > 0) {
                        for (int i = 0; i < stateModelArraysDelivery.size(); i++) {

                            if (stateModelArraysDelivery.get(i).nStateID != null) {

                                if (selectedSate == i) {
                                    webCallCity(stateModelArraysDelivery.get(i).nStateID);
                                }
                            } else {
                                CM.showToast(ViewCustomerDetail.this, getString(R.string.selectstateVali));
                            }
                        }
                    } else {
                        CM.showToast(ViewCustomerDetail.this, "No City Available");
                    }
                } else {
                    CM.showToast(ViewCustomerDetail.this, getString(R.string.selectstateVali));

                }
            }
            return false;
        }
    };

    private View.OnTouchListener spinnerOnTouchStateDelivery = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                webCallStateDelivery();
            }
            return false;
        }
    };

    private View.OnTouchListener spinnerOnTouchCityDelivery = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (stateModelArraysDelivery != null) {
                    if (stateModelArraysDelivery.size() > 0) {
                        for (int i = 0; i < stateModelArraysDelivery.size(); i++) {

                            if (stateModelArraysDelivery.get(i).nStateID != null) {
                                if (selectedSateDelivery == i) {
                                    webCallCityDelivery(stateModelArraysDelivery.get(i).nStateID);
                                }
                            } else {
                                CM.showToast(ViewCustomerDetail.this, getString(R.string.selectstateVali));
                            }

                        }
                    } else {
                        CM.showToast(ViewCustomerDetail.this, "No City Available");
                    }
                } else {
                    CM.showToast(ViewCustomerDetail.this, getString(R.string.selectstateVali));
                }
            }
            return false;
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlaceOrder:

                if ((model_main.companyModelArrays.get(0).nMinOrderPlacingAmt != null)) {
                    try {
                        Double totAmount = Double.parseDouble(CM.getSp(this, "totAmount", "").toString());
                        Double minAmount = Double.parseDouble(model_main.companyModelArrays.get(0).nMinOrderPlacingAmt);
                        if (totAmount >= minAmount) {
                            String valid = CM.Validation(validation, edtName, edtEmail, editTextDeliveryAddressOne);
                            if (!valid.equals(CV.Valid)) {
                                CM.ShowDialogue(ViewCustomerDetail.this, valid);
                            } else {
                                if (CM.isValidPhoneNumber(edtMobileNo.getText().toString())) {
                                    if (CM.isEmailValid(edtEmail.getText().toString())) {

                                        if (edtMobilNo2.getText().toString().length() >= 1) {
                                            if (CM.isValidPhoneNumber(edtMobilNo2.getText().toString())) {
                                                jsonObject = new JSONObject();
                                                try {
                                                    jsonObject.put(CONSTANT.CAPPSTORECODE, CONSTANT.cAppStoreCode);
                                                    jsonObject.put(CONSTANT.CAPIPASS, CONSTANT.cAPIPass);
                                                    jsonObject.put(CONSTANT.CAPIKEY, CONSTANT.cAPIKey);
                                                    jsonObject.put(CONSTANT.NCARTID, CM.getSp(ViewCustomerDetail.this, "cartId", ""));
                                                    jsonObject.put(CONSTANT.ORDERDATE, CM.converDateFormate("dd-MM-yyyy", "yyyy-MM-dd", CM.getSp(ViewCustomerDetail.this, "serverDate", "").toString()));
                                                    try {
                                                        DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                                        DateFormat writeFormat = new SimpleDateFormat("HH:mm");
                                                        Date date = null;
                                                        date = readFormat.parse(CM.getSp(ViewCustomerDetail.this, "serverTime", "").toString());
                                                        String formattedDate = "";
                                                        if (date != null) {
                                                            formattedDate = writeFormat.format(date);
                                                        }
                                                        jsonObject.put(CONSTANT.ORDERTIME, formattedDate);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    jsonObject.put(CONSTANT.NCOMPANYID, CONSTANT.nCompanyID);
                                                    jsonObject.put(CONSTANT.NCUSTOMERID, CM.getSp(ViewCustomerDetail.this, "customerId", "").toString());
                                                    jsonObject.put(CONSTANT.CCUSTOMERNAME, edtName.getText().toString().trim());
                                                    jsonObject.put(CONSTANT.CEMAIL, edtEmail.getText().toString().trim());
                                                    jsonObject.put(CONSTANT.CMOBILE1, edtMobileNo.getText().toString().trim());

                                                    //Delivery Deatil
                                                    jsonObject.put(CONSTANT.CSHIPADDRESSLINE1, editTextDeliveryAddressOne.getText().toString());
                                                    jsonObject.put(CONSTANT.CSHIPADDRESSLINE2, editTextDeliveryAddressSec.getText().toString());
                                                    jsonObject.put(CONSTANT.CSHIPZIP, edtZipCodeBillingDelivery.getText().toString());


                                                    //Billing Address
                                                    jsonObject.put(CONSTANT.CBILLINGADDRESSLINE1, edtBillingAdDressOne.getText().toString());
                                                    jsonObject.put(CONSTANT.CBILLINGADDRESSLINE2, edtBillingAdDressSec.getText().toString());
                                                    jsonObject.put(CONSTANT.CBILLINGZIP, edtZipCodeBilling.getText().toString());

                                                    jsonObject.put(CONSTANT.CSPCOMMENT, editTextCommet.getText().toString());
                                                    StateModelArray stateModelArray1 = new StateModelArray();
                                                    CityModelArray cityModelArray1 = new CityModelArray();
                                                    stateModelArray1 = (StateModelArray) spinnerStateBillingAddress.getSelectedItem();
                                                    cityModelArray1 = (CityModelArray) spinnerCityBillingAddress.getSelectedItem();

                                                    if (!stateModelArray1.cStateName.equals("Select State")) {
                                                        jsonObject.put(CONSTANT.CBILLINGCITY, stateModelArray1.cStateName);

                                                    } else {
                                                        jsonObject.put(CONSTANT.CBILLINGCITY, "");

                                                    }
                                                    if (!cityModelArray1.cCityName.equals("Select City")) {
                                                        jsonObject.put(CONSTANT.CBILLINGSTATE, cityModelArray1.cCityName);
                                                    } else {
                                                        jsonObject.put(CONSTANT.CBILLINGSTATE, "");
                                                    }
                                                    StateModelArray stateModelArray = new StateModelArray();
                                                    stateModelArray = (StateModelArray) spinnerStateDeliveryAddress.getSelectedItem();
                                                    jsonObject.put(CONSTANT.CSHIPSTATE, stateModelArray.cStateName);
                                                    CityModelArray cityModelArray = new CityModelArray();
                                                    cityModelArray = (CityModelArray) spinnerCityDeliveryAddress.getSelectedItem();
                                                    jsonObject.put(CONSTANT.CSHIPCITY, cityModelArray.cCityName);

                                                    LandmarkModelArray landmarkModelArray = new LandmarkModelArray();
                                                    landmarkModelArray = (LandmarkModelArray) spinnerLocation.getSelectedItem();
                                                    jsonObject.put(CONSTANT.CLANDMARK, landmarkModelArray.cZipCode);
                                                    jsonObject.put(CONSTANT.CADDONS, "");


                                                    StateModelArray stateModelArray2 = new StateModelArray();
                                                    stateModelArray2 = (StateModelArray) spinnerStateBillingAddress.getSelectedItem();
                                                    CityModelArray cityModelArray2 = new CityModelArray();
                                                    cityModelArray2 = (CityModelArray) spinnerCityBillingAddress.getSelectedItem();
                                                    stateModelArray2.cStateName.toString();
                                                    cityModelArray2.cCityName.toString();


                                                    if (!stateModelArray.cStateName.toString().equals("Select State")) {
                                                        if (!cityModelArray.cCityName.toString().equals("Select City")) {
                                                            if (!landmarkModelArray.cZipCode.toString().equals("Select Landmark")) {


                                                                Date orderTime = null, clossiingTime;
                                                                Date currentTime = null;
                                                                try {

                                                                    Date date = Calendar.getInstance().getTime();
                                                                    String orderDate = CM.converDateFormate("dd-MM-yyyy", "dd-MM-yyyy", CM.getSp(ViewCustomerDetail.this, "serverDate", "").toString());
                                                                    String currentDate = CM.converDateFormate("EEE MMM dd HH:mm:ss Z yyyy", "dd-MM-yyyy", date.toString());
                                                                    /**
                                                                     *  Order Date is Not less then Current Date
                                                                     */
                                                                    Date odate = null, cdate = null;
                                                                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                                                    try {
                                                                        odate = format.parse(orderDate);
                                                                        cdate = format.parse(currentDate);
                                                                        System.out.println(date);
                                                                    } catch (ParseException e) {
                                                                        // TODO Auto-generated catch block
                                                                        e.printStackTrace();
                                                                    }


                                                                    DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                                                    DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                                                                    Date time = new Date();
                                                                    //orderTime = readFormat.parse(CM.getSp(ViewCustomerDetail.this, "serverTime", "").toString());
                                                                    //  currentTime = readFormat.parse(dateFormat.format(time));
                                                                    //  clossiingTime = readFormat.parse(CM.getSp(ViewCustomerDetail.this, "closingTime", "").toString());


                                                                    String serverTime = CM.getSp(ViewCustomerDetail.this, "serverTime", "").toString();
                                                                    if (CM.getCurentDateAndTime().contains("p.m.") || CM.getCurentDateAndTime().contains("a.m.")) {
                                                                        serverTime = serverTime.replace("PM", "p.m.");
                                                                        serverTime = serverTime.replace("AM", "a.m.");
                                                                    }
                                                                    orderTime = readFormat.parse(serverTime); //02:05 PM

                                                                    String currentTimeFormat = readFormat.format(time);

                                                                    if (CM.getCurentDateAndTime().contains("p.m.") || CM.getCurentDateAndTime().contains("a.m.")) {
                                                                        currentTimeFormat = currentTimeFormat.replace("PM", "p.m.");
                                                                        currentTimeFormat = currentTimeFormat.replace("AM", "a.m.");
                                                                    }
                                                                    currentTime = readFormat.parse(currentTimeFormat); //11:26 PM


                                                                    String clossingTimeFormat = CM.getSp(ViewCustomerDetail.this, "closingTime", "").toString();
                                                                    if (CM.getCurentDateAndTime().contains("p.m.") || CM.getCurentDateAndTime().contains("a.m.")) {
                                                                        clossingTimeFormat = clossingTimeFormat.replace("PM", "p.m.");
                                                                        clossingTimeFormat = clossingTimeFormat.replace("AM", "a.m.");
                                                                    }
                                                                    clossiingTime = readFormat.parse(clossingTimeFormat); //11:59 PM


                                                                    if (odate.compareTo(cdate) > 0) {
                                                                        if (clossiingTime.after(orderTime)) {

                                                                            PayModeModeArray payModeMode = new PayModeModeArray();
                                                                            payModeMode = (PayModeModeArray) spinnerPayMode.getSelectedItem();
                                                                            String payMode = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                                                                            if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.payu))) {
                                                                                Toast.makeText(this, "Online Payment", Toast.LENGTH_SHORT).show();
                                                                                jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.payu));

                                                                                if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                    CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                } else {
                                                                                    String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                    if (orderMode.equals("TakeOut")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                    } else if (orderMode.equals("Delivery")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    } else {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    }
                                                                                    new TestAsync("online").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                }


                                                                            } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cash))) {
                                                                                jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cash));


                                                                                if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                    CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                } else {
                                                                                    String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                    if (orderMode.equals("TakeOut")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                    } else if (orderMode.equals("Delivery")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    } else {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    }
                                                                                    new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                }


                                                                            } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cod))) {
                                                                                jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cod));


                                                                                if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                    CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                } else {
                                                                                    String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                    if (orderMode.equals("TakeOut")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                    } else if (orderMode.equals("Delivery")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    } else {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    }
                                                                                    new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                }
                                                                            } else {
                                                                                CM.showToast(ViewCustomerDetail.this, "Choose Payment Mode");
                                                                            }

                                                                        }

                                                                    } else if (odate.compareTo(cdate) == 0) {
                                                                        if (currentTime.before(orderTime) && clossiingTime.after(orderTime)) {
                                                                            String payMode = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                                                                            PayModeModeArray payModeMode = new PayModeModeArray();
                                                                            payModeMode = (PayModeModeArray) spinnerPayMode.getSelectedItem();

                                                                            if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.payu))) {
                                                                                Toast.makeText(this, "Online Payment", Toast.LENGTH_SHORT).show();
                                                                                jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.payu));

                                                                                if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                    CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                } else {
                                                                                    String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                    if (orderMode.equals("TakeOut")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                    } else if (orderMode.equals("Delivery")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    } else {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    }
                                                                                    new TestAsync("online").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                }

                                                                            } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cash))) {
                                                                                jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cash));


                                                                                if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                    CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                } else {
                                                                                    String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                    if (orderMode.equals("TakeOut")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                    } else if (orderMode.equals("Delivery")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    } else {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    }
                                                                                    new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                }


                                                                            } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cod))) {
                                                                                jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cod));


                                                                                if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                    CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                } else {
                                                                                    String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                    if (orderMode.equals("TakeOut")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                    } else if (orderMode.equals("Delivery")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    } else {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    }
                                                                                    new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                }
                                                                            } else {
                                                                                CM.showToast(ViewCustomerDetail.this, "Choose Payment Mode");
                                                                            }
                                                                        } else if (currentTime.equals(orderTime)) {
                                                                            if (clossiingTime.after(orderTime)) {
                                                                                PayModeModeArray payModeMode = new PayModeModeArray();
                                                                                payModeMode = (PayModeModeArray) spinnerPayMode.getSelectedItem();

                                                                                String payMode = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                                                                                if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.payu))) {
                                                                                    Toast.makeText(this, "Online Payment", Toast.LENGTH_SHORT).show();
                                                                                    jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.payu));

                                                                                    if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                        CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                    } else {
                                                                                        String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                        if (orderMode.equals("TakeOut")) {
                                                                                            jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                        } else if (orderMode.equals("Delivery")) {
                                                                                            jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                        } else {
                                                                                            jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                        }
                                                                                        new TestAsync("online").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                    }


                                                                                } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cash))) {
                                                                                    jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cash));


                                                                                    if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                        CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                    } else {
                                                                                        String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                        if (orderMode.equals("TakeOut")) {
                                                                                            jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                        } else if (orderMode.equals("Delivery")) {
                                                                                            jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                        } else {
                                                                                            jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                        }
                                                                                        new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                    }
                                                                                } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cod))) {
                                                                                    jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cod));


                                                                                    if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                        CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                    } else {
                                                                                        String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                        if (orderMode.equals("TakeOut")) {
                                                                                            jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                        } else if (orderMode.equals("Delivery")) {
                                                                                            jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                        } else {
                                                                                            jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                        }
                                                                                        new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                    }
                                                                                } else {
                                                                                    CM.showToast(ViewCustomerDetail.this, "Choose Payment Mode");
                                                                                }

                                                                            } else {
                                                                                CM.showToast(this, getString(R.string.validTimeDate));
                                                                            }
                                                                        } else {
                                                                            CM.showToast(this, getString(R.string.validTimeDate));
                                                                        }
                                                                    }
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }


                                                            } else {
                                                                CM.showToast(ViewCustomerDetail.this, "Please Select Landmark");
                                                            }
                                                        } else {
                                                            CM.showToast(ViewCustomerDetail.this, "Please Select City");
                                                        }
                                                    } else {
                                                        CM.showToast(ViewCustomerDetail.this, "Please Select State");
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            } else {
                                                CM.showToast(this, "Enter Valid Secondary phone No.");
                                            }
                                        } else {
                                            jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put(CONSTANT.CAPPSTORECODE, CONSTANT.cAppStoreCode);
                                                jsonObject.put(CONSTANT.CAPIPASS, CONSTANT.cAPIPass);
                                                jsonObject.put(CONSTANT.CAPIKEY, CONSTANT.cAPIKey);


                                                jsonObject.put(CONSTANT.NCARTID, CM.getSp(ViewCustomerDetail.this, "cartId", ""));
                                                jsonObject.put(CONSTANT.ORDERDATE, CM.converDateFormate("dd-MM-yyyy", "yyyy-MM-dd", CM.getSp(ViewCustomerDetail.this, "serverDate", "").toString()));

                                                jsonObject.put(CONSTANT.CZIP, edtZipCodeBillingDelivery.getText().toString());
                                                try {
                                                    DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                                    DateFormat writeFormat = new SimpleDateFormat("HH:mm");
                                                    Date date = null;
                                                    date = readFormat.parse(CM.getSp(ViewCustomerDetail.this, "serverTime", "").toString());
                                                    String formattedDate = "";
                                                    if (date != null) {
                                                        formattedDate = writeFormat.format(date);
                                                    }
                                                    jsonObject.put(CONSTANT.ORDERTIME, formattedDate);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }


                                                jsonObject.put(CONSTANT.NCOMPANYID, CONSTANT.nCompanyID);
                                                jsonObject.put(CONSTANT.NCUSTOMERID, CM.getSp(ViewCustomerDetail.this, "customerId", "").toString());
                                                jsonObject.put(CONSTANT.CCUSTOMERNAME, edtName.getText().toString().trim());
                                                jsonObject.put(CONSTANT.CEMAIL, edtEmail.getText().toString().trim());
                                                jsonObject.put(CONSTANT.CMOBILE1, edtMobileNo.getText().toString().trim());


                                                jsonObject.put(CONSTANT.CSHIPADDRESSLINE1, editTextDeliveryAddressOne.getText().toString());
                                                jsonObject.put(CONSTANT.CSHIPADDRESSLINE2, editTextDeliveryAddressSec.getText().toString());
                                                jsonObject.put(CONSTANT.CSHIPZIP, edtZipCodeBillingDelivery.getText().toString());


                                                //Billing Address
                                                jsonObject.put(CONSTANT.CBILLINGADDRESSLINE1, edtBillingAdDressOne.getText().toString());
                                                jsonObject.put(CONSTANT.CBILLINGADDRESSLINE2, edtBillingAdDressSec.getText().toString());
                                                jsonObject.put(CONSTANT.CBILLINGZIP, edtZipCodeBilling.getText().toString());


                                                CityModelArray cityModelArray = new CityModelArray();
                                                cityModelArray = (CityModelArray) spinnerCityDeliveryAddress.getSelectedItem();
                                                jsonObject.put(CONSTANT.CSHIPCITY, cityModelArray.cCityName);

                                                jsonObject.put(CONSTANT.CSPCOMMENT, editTextCommet.getText().toString());

                                                StateModelArray stateModelArray1 = new StateModelArray();
                                                CityModelArray cityModelArray1 = new CityModelArray();
                                                stateModelArray1 = (StateModelArray) spinnerStateBillingAddress.getSelectedItem();
                                                cityModelArray1 = (CityModelArray) spinnerCityBillingAddress.getSelectedItem();
                                                if (!stateModelArray1.cStateName.equals("Select State")) {
                                                    jsonObject.put(CONSTANT.CBILLINGCITY, stateModelArray1.cStateName);

                                                } else {
                                                    jsonObject.put(CONSTANT.CBILLINGCITY, "");

                                                }
                                                if (!cityModelArray1.cCityName.equals("Select City")) {
                                                    jsonObject.put(CONSTANT.CBILLINGSTATE, cityModelArray1.cCityName);
                                                } else {
                                                    jsonObject.put(CONSTANT.CBILLINGSTATE, "");
                                                }


                                                StateModelArray stateModelArray = new StateModelArray();
                                                stateModelArray = (StateModelArray) spinnerStateDeliveryAddress.getSelectedItem();
                                                jsonObject.put(CONSTANT.CSHIPSTATE, stateModelArray.cStateName);
                                                LandmarkModelArray landmarkModelArray = new LandmarkModelArray();
                                                landmarkModelArray = (LandmarkModelArray) spinnerLocation.getSelectedItem();
                                                jsonObject.put(CONSTANT.CLANDMARK, landmarkModelArray.cZipCode);
                                                jsonObject.put(CONSTANT.CADDONS, "");

                                                StateModelArray stateModelArray2 = new StateModelArray();
                                                stateModelArray2 = (StateModelArray) spinnerStateBillingAddress.getSelectedItem();
                                                CityModelArray cityModelArray2 = new CityModelArray();
                                                cityModelArray2 = (CityModelArray) spinnerCityBillingAddress.getSelectedItem();


                                                if (!stateModelArray.cStateName.toString().equals("Select State")) {
                                                    if (!cityModelArray.cCityName.toString().equals("Select City")) {
                                                        if (!landmarkModelArray.cZipCode.toString().equals("Select Landmark")) {
                                                            //    new TestAsync().execute(URLS.PLACEORDER, jsonObject.toString());
                                                            Date orderTime = null, clossiingTime;
                                                            Date currentTime = null;
                                                            try {

                                                                Date date = Calendar.getInstance().getTime();
                                                                String orderDate = CM.converDateFormate("dd-MM-yyyy", "dd-MM-yyyy", CM.getSp(ViewCustomerDetail.this, "serverDate", "").toString());
                                                                String currentDate = CM.converDateFormate("EEE MMM dd HH:mm:ss Z yyyy", "dd-MM-yyyy", date.toString());
                                                                /**
                                                                 *  Order Date is Not less then Current Date
                                                                 */
                                                                Date odate = null, cdate = null;
                                                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                                                try {
                                                                    odate = format.parse(orderDate);
                                                                    cdate = format.parse(currentDate);
                                                                    System.out.println(date);
                                                                } catch (ParseException e) {
                                                                    // TODO Auto-generated catch block
                                                                    e.printStackTrace();
                                                                }


                                                                DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                                                DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                                                                Date time = new Date();
                                                                //   orderTime = readFormat.parse(CM.getSp(ViewCustomerDetail.this, "serverTime", "").toString());
                                                                //currentTime = readFormat.parse(dateFormat.format(time));
                                                                //   clossiingTime = readFormat.parse(CM.getSp(ViewCustomerDetail.this, "closingTime", "").toString());

                                                                String currentTimeFormat = readFormat.format(time);

                                                                if (CM.getCurentDateAndTime().contains("p.m.") || CM.getCurentDateAndTime().contains("a.m.")) {
                                                                    currentTimeFormat = currentTimeFormat.replace("PM", "p.m.");
                                                                    currentTimeFormat = currentTimeFormat.replace("AM", "a.m.");
                                                                }
                                                                currentTime = readFormat.parse(currentTimeFormat); //11:26 PM

                                                                String serverTime = CM.getSp(ViewCustomerDetail.this, "serverTime", "").toString();
                                                                if (CM.getCurentDateAndTime().contains("p.m.") || CM.getCurentDateAndTime().contains("a.m.")) {
                                                                    serverTime = serverTime.replace("PM", "p.m.");
                                                                    serverTime = serverTime.replace("AM", "a.m.");
                                                                }
                                                                orderTime = readFormat.parse(serverTime); //02:05 PM

                                                                //clossingTime = readFormat1.parse(CM.getSp(ViewOrderSummary.this, "closingTime", "").toString());

                                                                String clossingTimeFormat = CM.getSp(ViewCustomerDetail.this, "closingTime", "").toString();
                                                                if (CM.getCurentDateAndTime().contains("p.m.") || CM.getCurentDateAndTime().contains("a.m.")) {
                                                                    clossingTimeFormat = clossingTimeFormat.replace("PM", "p.m.");
                                                                    clossingTimeFormat = clossingTimeFormat.replace("AM", "a.m.");
                                                                }
                                                                clossiingTime = readFormat.parse(clossingTimeFormat); //11:59 PM

                                                                if (odate.compareTo(cdate) > 0) {
                                                                    if (clossiingTime.after(orderTime)) {
                                                                        //  new TestAsync().execute(URLS.PLACEORDER, jsonObject.toString());

                                                                        String payMode = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                                                                        PayModeModeArray payModeMode = new PayModeModeArray();
                                                                        payModeMode = (PayModeModeArray) spinnerPayMode.getSelectedItem();

                                                                        if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.payu))) {
                                                                            Toast.makeText(this, "Online Payment", Toast.LENGTH_SHORT).show();
                                                                            jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.payu));
                                                                            if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                            } else {
                                                                                String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                if (orderMode.equals("TakeOut")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                } else if (orderMode.equals("Delivery")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                } else {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                }
                                                                                new TestAsync("online").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                            }

                                                                        } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cash))) {
                                                                            jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cash));


                                                                            if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                            } else {
                                                                                String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                if (orderMode.equals("TakeOut")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                } else if (orderMode.equals("Delivery")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                } else {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                }
                                                                                new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                            }
                                                                        } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cod))) {
                                                                            jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cod));

                                                                            if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                            } else {
                                                                                String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                if (orderMode.equals("TakeOut")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                } else if (orderMode.equals("Delivery")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                } else {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                }
                                                                                new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                            }

                                                                        } else {
                                                                            CM.showToast(ViewCustomerDetail.this, "Choose Payment Mode");
                                                                        }
                                                                    }
                                                                } else if (odate.compareTo(cdate) == 0) {
                                                                    if (currentTime.before(orderTime) && clossiingTime.after(orderTime)) {
                                                                        // new TestAsync().execute(URLS.PLACEORDER, jsonObject.toString());
                                                                        String payMode = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                                                                        PayModeModeArray payModeMode = new PayModeModeArray();
                                                                        payModeMode = (PayModeModeArray) spinnerPayMode.getSelectedItem();

                                                                        if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.payu))) {
                                                                            Toast.makeText(this, "Online Payment", Toast.LENGTH_SHORT).show();
                                                                            jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.payu));

                                                                            if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                            } else {
                                                                                String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                if (orderMode.equals("TakeOut")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                } else if (orderMode.equals("Delivery")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                } else {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                }
                                                                                new TestAsync("online").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                            }

                                                                        } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cash))) {
                                                                            jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cash));


                                                                            if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                            } else {
                                                                                String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                if (orderMode.equals("TakeOut")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                } else if (orderMode.equals("Delivery")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                } else {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                }
                                                                                new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                            }
                                                                        } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cod))) {
                                                                            jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cod));


                                                                            if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                            } else {
                                                                                String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                if (orderMode.equals("TakeOut")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                } else if (orderMode.equals("Delivery")) {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                } else {
                                                                                    jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                }
                                                                                new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                            }

                                                                        } else {
                                                                            CM.showToast(ViewCustomerDetail.this, "Choose Payment Mode");
                                                                        }

                                                                    } else if (currentTime.equals(orderTime)) {
                                                                        if (clossiingTime.after(orderTime)) {
                                                                            PayModeModeArray payModeMode = new PayModeModeArray();
                                                                            payModeMode = (PayModeModeArray) spinnerPayMode.getSelectedItem();


                                                                            String payMode = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                                                                            if (payMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.payu))) {
                                                                                Toast.makeText(this, "Online Payment", Toast.LENGTH_SHORT).show();
                                                                                jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.payu));

                                                                                if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                    CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                } else {
                                                                                    String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                    if (orderMode.equals("TakeOut")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                    } else if (orderMode.equals("Delivery")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    } else {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    }
                                                                                    new TestAsync("online").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                }
                                                                            } else if (payMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cash))) {
                                                                                jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cash));


                                                                                if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                    CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                } else {
                                                                                    String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                    if (orderMode.equals("TakeOut")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                    } else if (orderMode.equals("Delivery")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    } else {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    }
                                                                                    new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                }

                                                                            } else if (payModeMode != null && payModeMode.getcOrderPaymentDisplayText().toString().equals(getString(R.string.cod))) {
                                                                                jsonObject.put(CONSTANT.CORDERPAYMENTMODE, getString(R.string.cod));


                                                                                if (payModeGrop.getCheckedRadioButtonId() == -1) {
                                                                                    CM.showToast(ViewCustomerDetail.this, "PLEASE SELECT AT LEAST ONE DELIVERY MODE");
                                                                                } else {
                                                                                    String orderMode = ((RadioButton) findViewById(payModeGrop.getCheckedRadioButtonId())).getText().toString();
                                                                                    if (orderMode.equals("TakeOut")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "1");
                                                                                    } else if (orderMode.equals("Delivery")) {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    } else {
                                                                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                                                                    }
                                                                                    new TestAsync("cash").execute(URLS.PLACEORDER, jsonObject.toString());

                                                                                }


                                                                            } else {
                                                                                CM.showToast(ViewCustomerDetail.this, "Choose Payment Mode");
                                                                            }
                                                                        } else {
                                                                            CM.showToast(this, getString(R.string.validTimeDate));
                                                                        }
                                                                    } else {
                                                                        CM.showToast(this, getString(R.string.validTimeDate));
                                                                    }
                                                                }
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }


                                                        } else {
                                                            CM.showToast(ViewCustomerDetail.this, "Please Select Landmark");
                                                        }
                                                    } else {
                                                        CM.showToast(ViewCustomerDetail.this, "Please Select City");
                                                    }
                                                } else {
                                                    CM.showToast(ViewCustomerDetail.this, "Please Select State");
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    } else {
                                        CM.showToast(ViewCustomerDetail.this, "Enter Valid Email");
                                    }
                                } else {
                                    CM.showToast(ViewCustomerDetail.this, "Primary Phone No. Not Valid");
                                }
                            }

                        } else {
                            CM.showToast(this, "Order Must me upto " + CM.getSp(ViewCustomerDetail.this, "currency", "").toString() + "" + model_main.companyModelArrays.get(0).nMinOrderPlacingAmt);
                        }

                    } catch (Exception e) {
                        CM.showToast(this, e.getMessage());
                    }

                } else {
                    CM.showToast(this, "Minimum Order is Not Valid");

                }
                break;
            case R.id.copyLayoutnew:

                StateModelArray stateModelArray1 = new StateModelArray();
                CityModelArray cityModelArray1 = new CityModelArray();
                try {
                    cityModelArray1 = (CityModelArray) spinnerCityDeliveryAddress.getSelectedItem();
                    stateModelArray1 = (StateModelArray) spinnerStateDeliveryAddress.getSelectedItem();
                } catch (Exception e) {
                    e.getMessage();

                }
                stateModelArraysBilling.clear();
                stateModelArraysBilling = new ArrayList<>();
                if (stateModelArray1.cStateName != null) {
                    if (stateModelArray1.cStateName.toString().equals("Select State")) {
                        stateModelArraysBilling.clear();

                        stateModelArray1.cStateName = "Select State";
                        stateModelArraysBilling.add(stateModelArray1);
                        CustomAdapterState customAdapterState1 = new CustomAdapterState(ViewCustomerDetail.this, R.layout.adapterstate, stateModelArraysBilling);
                        spinnerStateBillingAddress.setAdapter(customAdapterState1);
                        customAdapterState1.notifyDataSetChanged();
                    } else {
                        //String cStateName = stateModelArrayNew.cStateName;
                        stateModelArraysBilling.clear();
                        stateModelArray1.cStateName = stateModelArray1.cStateName.toString();
                        stateModelArraysBilling.add(stateModelArray1);
                        adptBillingState.clear();
                        adptBillingState = new CustomAdapterState(ViewCustomerDetail.this, R.layout.adapterstate, stateModelArraysBilling);
                        spinnerStateBillingAddress.setAdapter(adptBillingState);
                        adptBillingState.notifyDataSetChanged();

                    }
                }
                cityModelArraysBillling = new ArrayList<>();
                if (cityModelArray1.cCityName != null) {
                    if (cityModelArray1.cCityName.toString().equals("Select City")) {
                        cityModelArraysDelivery.clear();
                        CityModelArray cityModelArray = new CityModelArray();
                        cityModelArray.cCityName = "Select City";
                        cityModelArraysBillling.add(cityModelArray);
                        adptBillingCity = new CustomAdapterCity(ViewCustomerDetail.this, R.layout.adapterstate, cityModelArraysBillling);
                        spinnerCityBillingAddress.setAdapter(adptBillingCity);
                        adptBillingCity.notifyDataSetChanged();
                    } else {
                        cityModelArraysBillling.clear();
                        CityModelArray cityModelArray = new CityModelArray();
                        cityModelArray.cCityName = cityModelArray1.cCityName.toString();
                        cityModelArraysBillling.add(cityModelArray);
                        adptBillingCity = new CustomAdapterCity(ViewCustomerDetail.this, R.layout.adapterstate, cityModelArraysBillling);
                        spinnerCityBillingAddress.setAdapter(adptBillingCity);
                        adptBillingCity.notifyDataSetChanged();
                    }
                }


                edtBillingAdDressOne.setText(editTextDeliveryAddressOne.getText().toString());
                edtBillingAdDressSec.setText(editTextDeliveryAddressSec.getText().toString());
                edtZipCodeBilling.setText(edtZipCodeBillingDelivery.getText().toString());

                break;
        }
    }

//    private void makePayment(String cOrderNo) {
//        String getFname = edtName.getText().toString().trim();
//        String getEmail = edtEmail.getText().toString().trim();
//        String getPhone = edtMobileNo.getText().toString().trim();
//        String getAmt = CM.getSp(this, "totPay", "").toString();
//
//        Intent intent = new Intent(getApplicationContext(), PayMentGateWay.class);
//        intent.putExtra("FIRST_NAME", getFname);
//        intent.putExtra("PHONE_NUMBER", getPhone);
//        intent.putExtra("EMAIL_ADDRESS", getEmail);
//        intent.putExtra("RECHARGE_AMT", getAmt);
//        startActivityForResult(intent, 555);
//
//    }

    public void webCallCustomerDetail(String custId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);
            WebService.getResponseForCustomerDetail(v, custId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCustomerDetail(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForCustomerDetail(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {

            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("ResponseCode") != null && jsonObject.getString("ResponseCode").equals("202")) {
                UserProfileModel model_main = CM.JsonParse(new UserProfileModel(), jsonObject.getString("ResponseObject"));
                userProfileModelArrays = model_main.userProfileModelArrays;

                if (userProfileModelArrays.get(0).cFirstname != null && userProfileModelArrays.get(0).cLastname != null) {
                    edtName.setText(userProfileModelArrays.get(0).cFirstname + " " + userProfileModelArrays.get(0).cLastname);
                } else if (userProfileModelArrays.get(0).cFirstname != null) {
                    edtName.setText(userProfileModelArrays.get(0).cFirstname);
                }

                if (userProfileModelArrays.get(0).cMobilePrimary != null) {
                    edtMobileNo.setText(userProfileModelArrays.get(0).cMobilePrimary);
                } else {
                    edtMobileNo.setText("");
                }

                if (userProfileModelArrays.get(0).cMobileSecondary != null) {
                    edtMobilNo2.setText(userProfileModelArrays.get(0).cMobileSecondary);

                } else {
                    edtMobilNo2.setText("");
                }
                if (userProfileModelArrays.get(0).cEmail != null) {
                    edtEmail.setText(userProfileModelArrays.get(0).cEmail);
                } else {
                    edtEmail.setText("");
                }

                if (userProfileModelArrays.get(0).cAddressLine1 != null) {
                    editTextDeliveryAddressOne.setText(userProfileModelArrays.get(0).cAddressLine1);

                } else {
                    editTextDeliveryAddressOne.setText("");

                }
                if (userProfileModelArrays.get(0).cAddressLine2 != null) {
                    editTextDeliveryAddressSec.setText(userProfileModelArrays.get(0).cAddressLine2);
                } else {
                    editTextDeliveryAddressSec.setText("");
                }


                //Shipping Address Spiner City and State.
                cityModelArraysDelivery = new ArrayList<>();
                CustomAdapterCity customAdapterState = new CustomAdapterCity(ViewCustomerDetail.this, R.layout.adapterstate, cityModelArraysDelivery);
                spinnerCityDeliveryAddress.setAdapter(customAdapterState);
                if (userProfileModelArrays.get(0).cCity == null) {
                    CityModelArray cityModelArray = new CityModelArray();
                    cityModelArray.cCityName = "Select City";
                    cityModelArraysDelivery.add(cityModelArray);
                    CM.setSp(this, "city", "Select City");
                } else {
                    CityModelArray cityModelArray = new CityModelArray();
                    cityModelArray.cCityName = userProfileModelArrays.get(0).cCity.toString();
                    cityModelArraysDelivery.add(cityModelArray);
                    CM.setSp(this, "city", userProfileModelArrays.get(0).cCity.toString());
                }
                customAdapterState.notifyDataSetChanged();

                stateModelArraysDelivery = new ArrayList<>();
                adptDeliveryState = new CustomAdapterState(ViewCustomerDetail.this, R.layout.adapterstate, stateModelArraysDelivery);
                spinnerStateDeliveryAddress.setAdapter(adptDeliveryState);
                if (userProfileModelArrays.get(0).cState == null) {
                    StateModelArray stateModelArray = new StateModelArray();
                    stateModelArray.cStateName = "Select State";
                    stateModelArraysDelivery.add(stateModelArray);
                    CM.setSp(this, "state", "Select State");
                } else {
                    StateModelArray stateModelArray = new StateModelArray();
                    stateModelArray.cStateName = userProfileModelArrays.get(0).cState;
                    stateModelArray.nStateID = "1";
                    stateModelArraysDelivery.add(stateModelArray);
                    CM.setSp(this, "state", userProfileModelArrays.get(0).cState.toString());
                }
                adptDeliveryState.notifyDataSetChanged();


                //Delivery Address Spinner City and State.

                cityModelArraysBillling = new ArrayList<>();
                CityModelArray cityModelArray = new CityModelArray();
                cityModelArray.cCityName = "Select City";
                cityModelArraysBillling.add(cityModelArray);
                adptBillingCity = new CustomAdapterCity(ViewCustomerDetail.this, R.layout.adapterstate, cityModelArraysBillling);
                spinnerCityBillingAddress.setAdapter(adptBillingCity);
                stateModelArraysBilling = new ArrayList<>();
                StateModelArray stateModelArray = new StateModelArray();
                stateModelArray.cStateName = "Select State";
                stateModelArraysBilling.add(stateModelArray);
                adptBillingState = new CustomAdapterState(ViewCustomerDetail.this, R.layout.adapterstate, stateModelArraysBilling);
                spinnerStateBillingAddress.setAdapter(adptBillingState);

                landmarkModelArrays = new ArrayList<>();
                CustomSpinnerAdapterLandMark mySpinnerArrayAdapter = new CustomSpinnerAdapterLandMark(ViewCustomerDetail.this, landmarkModelArrays);
                spinnerLocation.setAdapter(mySpinnerArrayAdapter);
                if (userProfileModelArrays.get(0).cLandmark == null) {
                    LandmarkModelArray landmarkModelArray = new LandmarkModelArray();
                    landmarkModelArray.cZipCode = "Select Landmark";
                    landmarkModelArrays.add(landmarkModelArray);
                } else {
                    LandmarkModelArray landmarkModelArray = new LandmarkModelArray();
                    landmarkModelArray.cZipCode = userProfileModelArrays.get(0).cLandmark;
                    landmarkModelArrays.add(landmarkModelArray);
                }
                mySpinnerArrayAdapter.notifyDataSetChanged();
                if (userProfileModelArrays.get(0).cZip != null) {
                    edtZipCodeBillingDelivery.setText(userProfileModelArrays.get(0).cZip);
                } else {
                    edtZipCodeBillingDelivery.setText("");
                }
                webCallReturnPolicy();
            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }

    public void webCallState() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);
            WebService.getResponseForState(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForState(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForState(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            StateModel model_main = CM.JsonParse(new StateModel(), jsonObject.getString("ResponseObject"));
            stateModelArraysBilling = model_main.stateModelArrays;
            stateModelArraysBilling.size();
            adptBillingState.clear();
            adptBillingState = null;
            adptBillingState = new CustomAdapterState(ViewCustomerDetail.this, R.layout.adapterstate, stateModelArraysBilling);
            spinnerStateBillingAddress.setAdapter(adptBillingState);
            spinnerStateBillingAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedSate = adapterView.getSelectedItemPosition();

                    if (stateModelArraysBilling != null) {
                        if (stateModelArraysBilling.size() > 0) {
                            for (int j = 0; j < stateModelArraysBilling.size(); j++) {
                                if (selectedSate == j) {
                                    webCallCity(stateModelArraysBilling.get(j).nStateID);
                                }
                            }
                        } else {
                            CM.showToast(ViewCustomerDetail.this, "No City Available");
                        }
                    } else {
                        CM.showToast(ViewCustomerDetail.this, getString(R.string.selectstateVali));

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }

    public void webCallCity(String id) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);
            WebService.getResponseForCity(v, id, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCity(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForCity(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("ResponseCode").equals("202")) {
                CityModel model_main = CM.JsonParse(new CityModel(), jsonObject.getString("ResponseObject"));
                cityModelArraysBillling = model_main.cityModelArrays;
                cityModelArraysBillling.size();
                CustomAdapterCity customAdapterState = new CustomAdapterCity(ViewCustomerDetail.this, R.layout.adapterstate, cityModelArraysBillling);
                spinnerCityBillingAddress.setAdapter(customAdapterState);
                spinnerCityBillingAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        selectedCity = adapterView.getSelectedItemPosition();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } else if (jsonObject.getString("ResponseCode").equals("204")) {
                CM.showToast(ViewCustomerDetail.this, jsonObject.getString("ResponseObject").toString());

            }


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }

    public void webCallStateDelivery() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);
            WebService.getResponseForState(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForStateDelivery(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getResponseForStateDelivery(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            StateModel model_main = CM.JsonParse(new StateModel(), jsonObject.getString("ResponseObject"));
            stateModelArraysDelivery = model_main.stateModelArrays;
            stateModelArraysDelivery.size();


            adptDeliveryState = new CustomAdapterState(ViewCustomerDetail.this, R.layout.adapterstate, stateModelArraysDelivery);
            spinnerStateDeliveryAddress.setAdapter(adptDeliveryState);


            if (!CM.getSp(this, "state", "").equals("")) {

                for (int i = 0; i < stateModelArraysDelivery.size(); i++) {
                    if (stateModelArraysDelivery.get(i).cStateName.equals(CM.getSp(this, "state", "").toString())) {
                        int spinnerPosition = adptDeliveryState.getPosition(stateModelArraysDelivery.get(i));
                        spinnerStateDeliveryAddress.setSelection(spinnerPosition);
                    }
                }
            }
            spinnerStateDeliveryAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedSateDelivery = adapterView.getSelectedItemPosition();

                    if (stateModelArraysDelivery != null) {
                        if (stateModelArraysDelivery.size() > 0) {
                            for (int j = 0; j < stateModelArraysDelivery.size(); j++) {
                                if (selectedSateDelivery == j) {
                                    CM.setSp(ViewCustomerDetail.this, "state", stateModelArraysDelivery.get(j).cStateName.toString());
                                    webCallCityDelivery(stateModelArraysDelivery.get(j).nStateID);
                                }
                            }
                        } else {
                            CM.showToast(ViewCustomerDetail.this, "No City Available");
                        }
                    } else {
                        CM.showToast(ViewCustomerDetail.this, getString(R.string.selectstateVali));

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }

    public void webCallCityDelivery(String id) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);
            WebService.getResponseForCity(v, id, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCityDelivery(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getResponseForCityDelivery(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("ResponseCode").equals("202")) {
                CityModel model_main = CM.JsonParse(new CityModel(), jsonObject.getString("ResponseObject"));
                cityModelArraysDelivery = model_main.cityModelArrays;
                // stateModelArrays.size();

                adptBillingCity = new CustomAdapterCity(ViewCustomerDetail.this, R.layout.adapterstate, cityModelArraysDelivery);
                spinnerCityDeliveryAddress.setAdapter(adptBillingCity);
                spinnerCityDeliveryAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedCityDelivery = adapterView.getSelectedItemPosition();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } else if (jsonObject.getString("ResponseCode").equals("204")) {
                CM.showToast(ViewCustomerDetail.this, jsonObject.getString("ResponseObject").toString());

            }


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }

    public void webCallLandMark() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);
            WebService.getResponseForLandMark(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);


                    getResponseForLandMark(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForLandMark(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {

            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("ResponseCode").equals("202")) {

                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("ResponseObject"));
                LandmarkModel model_main = CM.JsonParse(new LandmarkModel(), jsonObject1.toString());
                landmarkModelArrays = model_main.landmarkModelArrays;
                landmarkModelArrays.size();
                CustomSpinnerAdapterLandMark mySpinnerArrayAdapter = new CustomSpinnerAdapterLandMark(ViewCustomerDetail.this, landmarkModelArrays);
                spinnerLocation.setAdapter(mySpinnerArrayAdapter);

            } else {

            }


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }


    class TestAsync extends AsyncTask<String, Integer, String> {
        String TAG = getClass().getSimpleName();
        private String result;
        String mode;

        public TestAsync(String mode) {
            this.mode = mode;
        }

        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(ViewCustomerDetail.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        protected String doInBackground(String... strings) {
            InputStream inputStream = null;


            try {
                // 1. create HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(strings[0]);
                String json = "";

                JSONObject jsonObject1 = new JSONObject(strings[1]);
                json = jsonObject1.toString();
                String jsons = json.toString().replace("\\", "");
                StringEntity se = new StringEntity(jsons);
                // 6. set httpPost Entity
                httpPost.setEntity(se);
                // 7. Set some headers to inform server about the type of the content
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httpPost);
                // 9. receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();
                // 10. convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return result;
        }


        protected void onPostExecute(String result) {
            mProgressDialog.dismiss();
            if (result != null) {
                try {
                    JSONObject jsonObject1 = new JSONObject(result.toString());
                    String responseCode = jsonObject1.getString("ResponseCode");
                    if (responseCode.equals("202")) {

                        if (mode.equals("cash")) {
                            CM.setSp(ViewCustomerDetail.this, "totPay", "");
                            CM.setSp(ViewCustomerDetail.this, "totAmount", "");
                            CM.setSp(ViewCustomerDetail.this, "cartId", "0");
                            CM.setSp(ViewCustomerDetail.this, "time", "");
                            CM.setSp(ViewCustomerDetail.this, "date", "");
                            CM.setSp(ViewCustomerDetail.this, "serverTime", "");
                            CM.setSp(ViewCustomerDetail.this, "serverDate", "");
                            showDialog("ORDER DETAIL IS SENT ON YOUR EMAIL.");

                        } else {
                            JSONObject jsonObject = new JSONObject(jsonObject1.getString("ResponseObject"));
                            OrderId = jsonObject.getString("OrderID");
                            // makePayment(jsonObject.getString("OrderID").toString());
                        }


                    } else {
                        CM.showToast(ViewCustomerDetail.this, jsonObject1.getString("ResponseObject"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                CM.showToast(ViewCustomerDetail.this, getString(R.string.serverNotWork));
            }

        }

    }


    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewCustomerDetail.this);
        hideSoftKeyboard();
    }

    public String convertNull(String data) {
        String str = "";
        if (!data.equals("null")) {
            str = data;
        }
        return str;
    }


    public void webCallReturnPolicy() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);
            WebService.getResendForConpanyDate(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForReturnPolicy(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getResponseForReturnPolicy(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            model_main = CM.JsonParse(new CompanyModel(), jsonObject.getString("ResponseObject"));
            webCallPayMode();
            if (model_main.companyModelArrays.get(0).nOrderFillMode != null) {
                CM.setSp(ViewCustomerDetail.this, "payMode", model_main.companyModelArrays.get(0).nOrderFillMode.toString());

                if (model_main.companyModelArrays.get(0).nOrderFillMode.toString().equals("1")) {


                    if (!CM.getSp(ViewCustomerDetail.this, "DefPmode", "").toString().equals("null") && !CM.getSp(ViewCustomerDetail.this, "DefPmode", "").toString().equals("")) {
                        if (CM.getSp(ViewCustomerDetail.this, "DefPmode", "").toString().equals("1")) {
                            radioTakeOut.setChecked(true);
                        }
                    }
                    {

                    }


                    radioTakeOut.setEnabled(true);
                    radioDelivery.setEnabled(false);
                    radioDelivery.setChecked(false);


                } else if (model_main.companyModelArrays.get(0).nOrderFillMode.toString().equals("2")) {


                    radioTakeOut.setChecked(false);
                    radioTakeOut.setEnabled(false);
                    radioDelivery.setEnabled(true);


                    if (!CM.getSp(ViewCustomerDetail.this, "DefPmode", "").toString().equals("null") && !CM.getSp(ViewCustomerDetail.this, "DefPmode", "").toString().equals("")) {
                        if (CM.getSp(ViewCustomerDetail.this, "DefPmode", "").toString().equals("2")) {
                            radioDelivery.setChecked(true);
                        }
                    }
                    {

                    }


                } else {

                    if (!CM.getSp(ViewCustomerDetail.this, "DefPmode", "").toString().equals("null") && !CM.getSp(ViewCustomerDetail.this, "DefPmode", "").toString().equals("")) {
                        if (CM.getSp(ViewCustomerDetail.this, "DefPmode", "").toString().equals("3")) {
                            radioDelivery.setChecked(true);
                        }
                    }
                    {

                    }
                    radioDelivery.setEnabled(true);
                    radioTakeOut.setEnabled(true);

                }

            }


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton3:
                if (checked)
                    if (CM.isInternetAvailable(this)) {
                        webCallChangePayMode("2", CM.getSp(ViewCustomerDetail.this, "customerId", "").toString(), CM.getSp(ViewCustomerDetail.this, "cartId", "").toString());
                    } else {
                        CM.showToast(this, getString(R.string.internetMsg));
                    }
                break;
            case radioButton4:
                if (CM.isInternetAvailable(this)) {
                    webCallChangePayMode("1", CM.getSp(ViewCustomerDetail.this, "customerId", "").toString(), CM.getSp(ViewCustomerDetail.this, "cartId", "").toString());
                } else {
                    CM.showToast(this, getString(R.string.internetMsg));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 555) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                String payidId = data.getStringExtra("transId");
                String msg = data.getStringExtra("msg");
                if (result.equals("success")) {
                    webCallPaymentSuccess(payidId, result, CM.getSp(ViewCustomerDetail.this, "customerId", "").toString(), OrderId);
                } else {
                    webCallPaymentFail(payidId, result, CM.getSp(ViewCustomerDetail.this, "customerId", "").toString(), msg, OrderId);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                CM.showToast(ViewCustomerDetail.this, "PAYMENT CANCEL BY USER");
            }
        }
    }


    public void webCallPaymentFail(String paymentId, String msg, String cusId, String errorMSG, String orderId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);

            WebService.GetFailPayment(v, cusId, paymentId, msg, errorMSG, orderId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForFail(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void webCallPaymentSuccess(String paymentId, String msg, String custId, String orderId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);

            WebService.GetSuccessPayment(v, custId, paymentId, msg, orderId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForSuccess(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForSuccess(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {

            JSONObject jsonObject1 = new JSONObject(response);

            if (jsonObject1.getString("ResponseCode") != null) {
                if (jsonObject1.getString("ResponseCode").equals("202")) {
                    CM.showToast(ViewCustomerDetail.this, "PAYMENT SUCCESSFULLY RECEIVED");
                    CM.setSp(ViewCustomerDetail.this, "totPay", "");
                    CM.setSp(ViewCustomerDetail.this, "totAmount", "");
                    CM.setSp(ViewCustomerDetail.this, "cartId", "0");
                    CM.setSp(ViewCustomerDetail.this, "time", "");
                    CM.setSp(ViewCustomerDetail.this, "date", "");
                    CM.setSp(ViewCustomerDetail.this, "serverTime", "");
                    CM.setSp(ViewCustomerDetail.this, "serverDate", "");
                    showDialog("ORDER DETAIL IS SENT ON YOUR EMAIL.");
                } else {
                    CM.showToast(ViewCustomerDetail.this, jsonObject1.getString("ResponseObject"));
                }
            }
        } catch (
                Exception e
                )

        {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }

    }

    private void getResponseForFail(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {

            JSONObject jsonObject1 = new JSONObject(response);

            if (jsonObject1.getString("ResponseCode") != null) {
                if (jsonObject1.getString("ResponseCode").equals("202")) {
                    CM.showToast(ViewCustomerDetail.this, "PAYMENT FAIL");
                } else {
                    CM.showToast(ViewCustomerDetail.this, jsonObject1.getString("ResponseObject"));
                }
            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }

    public String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();


            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) hexString.append("0");
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException nsae) {
        }

        return hexString.toString();


    }


    private void showDialogMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TAG);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }


    public void webCallPayMode() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);
            WebService.getResponsePayMode(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForPayMode(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getResponseForPayMode(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("ResponseCode").equals("202")) {
                PayModeMode model_main = CM.JsonParse(new PayModeMode(), jsonObject.getString("ResponseObject"));
                payModeModeArrays = model_main.payModeModeArrays;
                payModeModeArrays.size();

                ArrayList<PayModeModeArray> payModeModeArrays1 = new ArrayList<>();
                for (int i = 0; i < payModeModeArrays.size(); i++) {
                    if (payModeModeArrays.get(i).getIsActive().toString().equals("true")) {
                        PayModeModeArray payModeModeArray = new PayModeModeArray();
                        payModeModeArray.setcOrderPaymentDisplayText(payModeModeArrays.get(i).getcOrderPaymentDisplayText());
                        payModeModeArray.setcOrderPaymentMode(payModeModeArrays.get(i).getcOrderPaymentMode());
                        payModeModeArray.setIsActive(payModeModeArrays.get(i).getIsActive());
                        payModeModeArrays1.add(payModeModeArray);
                    }
                }
                customAdapterPayMode = new CustomAdapterPayMode(ViewCustomerDetail.this, R.layout.adapterstate, payModeModeArrays1);
                spinnerPayMode.setAdapter(customAdapterPayMode);
                spinnerPayMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } else if (jsonObject.getString("ResponseCode").equals("204")) {
                CM.showToast(ViewCustomerDetail.this, jsonObject.getString("ResponseObject").toString());

            }


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }

    public void showDialog(String txt) {
        new AlertDialog.Builder(ViewCustomerDetail.this)
                .setTitle(getString(R.string.app_name))
                .setMessage(txt)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ViewCustomerDetail.this, ViewDrawerActivty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setIcon(R.drawable.applogo)
                .setCancelable(false)
                .show();
    }

    public void webCallChangePayMode(String mode, String custId, String cardId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomerDetail.this, true, true);
            WebService.getResponseChoosePayMode(v, mode, custId, cardId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForMode(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomerDetail.this)) {
                        CM.showPopupCommonValidation(ViewCustomerDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForMode(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("ResponseCode") != null && jsonObject.getString("ResponseCode").toString().equals("202")) {
                //CM.showToast(this, "Success");
            } else {
                if (jsonObject.getString("ResponseObject") != null) {
                    CM.showToast(ViewCustomerDetail.this, jsonObject.getString("ResponseObject"));
                }

            }


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewCustomerDetail.this, e.getMessage(), false);
        }
    }
}

