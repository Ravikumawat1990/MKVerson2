package com.app.elixir.makkhankitchen.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.CartItem;
import com.app.elixir.makkhankitchen.Model.CartItemArray;
import com.app.elixir.makkhankitchen.Model.CategoryItemDetailModel;
import com.app.elixir.makkhankitchen.Model.CategoryItemDetailModelArray;
import com.app.elixir.makkhankitchen.Model.EditCartModel;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.adapter.CustomSpinnerAdapterAddOn;
import com.app.elixir.makkhankitchen.adapter.ExpandableListAdapter;
import com.app.elixir.makkhankitchen.adapter.ExpandableListAdapterNot;
import com.app.elixir.makkhankitchen.database.ItemDetail;
import com.app.elixir.makkhankitchen.database.Items;
import com.app.elixir.makkhankitchen.interfac.OnFragmentInteractionListener;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListener;
import com.app.elixir.makkhankitchen.mtplview.MtplButton;
import com.app.elixir.makkhankitchen.mtplview.MtplLog;
import com.app.elixir.makkhankitchen.mtplview.MtplTextView;
import com.app.elixir.makkhankitchen.numberPicker.Interface.LimitExceededListener;
import com.app.elixir.makkhankitchen.numberPicker.NumberPicker;
import com.app.elixir.makkhankitchen.pojo.PojoArray;
import com.app.elixir.makkhankitchen.pojo.PojoItemDetail;
import com.app.elixir.makkhankitchen.pojo.PojoItems;
import com.app.elixir.makkhankitchen.utils.CM;
import com.app.elixir.makkhankitchen.utils.CONSTANT;
import com.app.elixir.makkhankitchen.utils.URLS;
import com.app.elixir.makkhankitchen.volly.OnVolleyHandler;
import com.app.elixir.makkhankitchen.volly.VolleyIntialization;
import com.app.elixir.makkhankitchen.volly.WebService;
import com.app.elixir.makkhankitchen.volly.WebServiceTag;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ViewFoodItemDetail extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewFoodItemDetail";
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private MtplButton addtoCartbtn;
    private MtplButton buttonAddOn;
    private ArrayList<CategoryItemDetailModelArray> categoryItemDetailModelArrayArrayList;
    private ImageView imageViewCoverImage;
    private MtplTextView textViewItemDis;
    private NumberPicker numberPicker;
    public static MtplTextView textViewItemPrice;
    private EditText comment;
    private ArrayList<PojoItems> pojoCustomizes;
    private ArrayList<PojoItemDetail> pojoItemDetails;
    private CategoryItemDetailModel model_main;
    private int count = 0;
    private LinearLayout linearLayoutItemDetail;
    private ArrayList<PojoItemDetail> allCheckedRecord;
    private LinearLayout linearLayoutCustoTitle;
    private String header;
    private MtplButton btnSpeicalComment;
    private AppBarLayout appBarLayout;
    private AppBarLayout appbar;
    private ProgressDialog mProgressDialog;
    private CoordinatorLayout rootLayout;
    private String catId;
    private Spinner spinner;
    private String itemPrice;
    private String itemQuantity = "1";
    private MtplTextView itemTypeTitle;
    private RadioButton radioButton;
    private RelativeLayout spinnerLayout;
    private Spinner spinner1;
    private String isRequired;
    private OnFragmentInteractionListener mListener;

    boolean isAdded = false;


    public static ExpandableListView expListView, expListView1;
    public ArrayList<String> listDataHeader;
    public HashMap<String, ArrayList<PojoArray>> listDataChild1;
    public static ExpandableListAdapter listAdapter;
    public static ExpandableListAdapterNot expandableListAdapterNot;

    public ArrayList<PojoItems> pojoItemses;
    public ArrayList<PojoItemDetail> pojoItemDetails1;
    public ArrayList<PojoArray> pojoArrays;
    public static TextView item;
    private String tot;
    private LinearLayout addOnHeaderLayout;
    private ProgressBar progressBar;
    private ArrayList<CartItemArray> cartItemArrays;
    private StringBuilder stringBuilder;
    private ImageView imageViewPlace;
    private LinearLayout linearLayoutExpandList;
    private MtplTextView layoutNotReq;
    private MtplTextView layoutReq;
    MtplTextView mtplTextViewTitle;
    String isEdit = "";
    String comProductId = "";
    private String isMulti;
    private EditCartModel model_mainEdit;
    private String isFav = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_detail);
        toolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar.setNavigationIcon(R.drawable.ic_back_white);

        try {
            mListener = (OnFragmentInteractionListener) this;
        } catch (Exception e) {
            e.getMessage();
        }

        Intent intent = getIntent();
        if (intent.getStringExtra("comProId") != null) {
            comProductId = intent.getStringExtra("comProId");
            catId = CM.getSp(ViewFoodItemDetail.this, "cartId", "").toString();
        } else {

        }
        if (intent.getStringExtra("isedit") != null) {
            isEdit = intent.getStringExtra("isedit");
            catId = intent.getStringExtra("catId");
            comProductId = intent.getStringExtra("comProId");


        } else {

        }

        toolbar.setTitle("");
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
                CM.finishActivity(ViewFoodItemDetail.this);
                Items.deleteAll();
                ItemDetail.deleteAll();
                CM.setSp(ViewFoodItemDetail.this, "quantity", "");
                // mListener.showDrawerToggle(false);
            }
        });
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.personal_collapsed_title);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.personal_expanded_title);
        final Typeface tf = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_robotolight_0));
        rootLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);


        appbar = (AppBarLayout) findViewById(R.id.appBar);

        addtoCartbtn = (MtplButton) findViewById(R.id.addtoCartbtn);
        buttonAddOn = (MtplButton) findViewById(R.id.itemdetailbtnAddon);
        buttonAddOn.setOnClickListener(this);
        addtoCartbtn.setOnClickListener(this);
        initView();
    }

    private void initView() {
        imageViewCoverImage = (ImageView) findViewById(R.id.cover_pic);
        imageViewCoverImage.setOnClickListener(this);

        textViewItemDis = (MtplTextView) findViewById(R.id.textViewItemDis);
        numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        numberPicker.setLimitExceededListener(new DefaultLimitExceededListener());
        numberPicker.setMax(Integer.parseInt(CM.getSp(ViewFoodItemDetail.this, "Quant", "").toString()));
        textViewItemPrice = (MtplTextView) findViewById(R.id.textViewItemPrice);
        linearLayoutItemDetail = (LinearLayout) findViewById(R.id.itemDetail);
        linearLayoutCustoTitle = (LinearLayout) findViewById(R.id.customizetitle);
        layoutNotReq = (MtplTextView) findViewById(R.id.layoutNotReq);
        layoutReq = (MtplTextView) findViewById(R.id.layoutReq);

        progressBar = (ProgressBar) findViewById(R.id.progreesbar);
        imageViewPlace = (ImageView) findViewById(R.id.placeholder);
        Animation scale = AnimationUtils.loadAnimation(ViewFoodItemDetail.this, R.anim.zoom);
        imageViewPlace.startAnimation(scale);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        itemTypeTitle = (MtplTextView) findViewById(R.id.itemType);
        spinnerLayout = (RelativeLayout) findViewById(R.id.spinnerLayout);
        expListView = (ExpandableListView) findViewById(R.id.expListView);
        linearLayoutExpandList = (LinearLayout) findViewById(R.id.layoutexpandList);
        expListView1 = (ExpandableListView) findViewById(R.id.expListView1);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Light_0.ttf");
        RadioButton radio1 = (RadioButton) findViewById(R.id.radioone);
        RadioButton radio2 = (RadioButton) findViewById(R.id.radioTwo);
        RadioButton radio3 = (RadioButton) findViewById(R.id.radiothree);
        radio1.setTypeface(font);
        radio2.setTypeface(font);
        radio3.setTypeface(font);
        addOnHeaderLayout = (LinearLayout) findViewById(R.id.addOnHeader);

        numberPicker.SetOnItemClickListener(new OnItemClickListener() {
                                                @Override
                                                public void onItemClick(String value) {

                                                    try {
                                                        itemQuantity = value;
                                                        tot = ItemDetail.getAllCheckeditemRecord();
                                                        if (!CM.getSp(ViewFoodItemDetail.this, "itemPrice", "").toString().equals("")) {
                                                            Double aFloat = (Double.parseDouble(CM.getSp(ViewFoodItemDetail.this, "itemPrice", "").toString()) + Double.parseDouble(tot)) * Double.parseDouble(itemQuantity);
                                                            DecimalFormat form = new DecimalFormat("0.00");
                                                            textViewItemPrice.setText(CM.getSp(ViewFoodItemDetail.this, "currency", "").toString() + String.valueOf(form.format(aFloat)));
                                                            CM.setSp(ViewFoodItemDetail.this, "quantity", value);
                                                        } else {

                                                        }
                                                    } catch (Exception e) {

                                                    }

                                                }
                                            }

        );


        if (CM.isInternetAvailable(ViewFoodItemDetail.this)) {
            if (isEdit != null && !isEdit.equals("")) {
                webCallEditFoodItemDetail(catId, CM.getSp(ViewFoodItemDetail.this, "customerId", "").toString(), comProductId);

            } else {
                webCallFoodItemDetail(comProductId, CM.getSp(ViewFoodItemDetail.this, "customerId", "").toString());
            }
        } else {
            CM.showToast(ViewFoodItemDetail.this, getString(R.string.internetMsg));
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Items.deleteAll();
        ItemDetail.deleteAll();
        CM.setSp(ViewFoodItemDetail.this, "quantity", "");
        CM.finishActivity(this);
        //mListener.showDrawerToggle(false);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addtoCartbtn:

                if (isEdit != null && !isEdit.equals("")) {

                    ArrayList<PojoItems> pojoItems = ItemDetail.getAlldataNotReruirdwithAddOnRequird();
                    if (pojoItems != null && pojoItems.size() > 0) {
                        String addOnPrice = ItemDetail.getAllCheckeditemRecord();
                        ArrayList<PojoItemDetail> allCheckedRecord = ItemDetail.getAllCheckedRecord();
                        ArrayList<PojoItems> pojoItemses = new ArrayList<>();
                        for (int i = 0; i < allCheckedRecord.size(); i++) {
                            ArrayList<PojoItems> data = Items.getAlldataUsingMapperId(allCheckedRecord.get(i).getnMapperID().toString());
                            for (int j = 0; j < data.size(); j++) {
                                PojoItems pojoItemDetail = new PojoItems();
                                pojoItemDetail.setnMapperID(data.get(j).getnMapperID().toString());
                                pojoItemDetail.setnAttributeID(data.get(j).getnAttributeID().toString());

                                if (data.get(j).getcAttributeLabel() != null) {
                                    pojoItemDetail.setcAttributeLabel(data.get(j).getcAttributeLabel().toString());
                                } else {
                                    pojoItemDetail.setcAttributeLabel("");
                                }
                                pojoItemDetail.setIsMultiple(data.get(j).getIsMultiple().toString());
                                pojoItemDetail.setIsRequired(data.get(j).getIsRequired().toString());
                                pojoItemses.add(pojoItemDetail);
                            }
                        }


                        JSONArray jsonArrayMain = new JSONArray();
                        JSONObject jsonObjectHeader = new JSONObject();
                        stringBuilder = new StringBuilder();
                        for (int k = 0; k < pojoItemses.size(); k++) {
                            try {
                                jsonObjectHeader.put("nMapperID", pojoItemses.get(k).getnMapperID());
                                jsonObjectHeader.put("nAttributeID", pojoItemses.get(k).getnAttributeID());

                                if (pojoItemses.get(k).getcAttributeLabel() != null) {
                                    jsonObjectHeader.put("cAttributeLabel", pojoItemses.get(k).getcAttributeLabel());

                                } else {
                                    jsonObjectHeader.put("cAttributeLabel", "");

                                }
                                jsonObjectHeader.put("isMultiple", pojoItemses.get(k).getIsMultiple());
                                jsonObjectHeader.put("isRequired", pojoItemses.get(k).getIsRequired());


                                if (pojoItemses.get(k).getcAttributeLabel() != null) {
                                    stringBuilder.append("<div id=" + "'StrAddon_" + pojoItemses.get(k).getnAttributeID() + "'" + "style='font-weight: normal !important;'><strong>" + pojoItemses.get(k).getcAttributeLabel() + ":</strong>");

                                } else {
                                    stringBuilder.append("");

                                }
                                ArrayList<PojoItemDetail> data = ItemDetail.getAlldataUsingMapperId(pojoItemses.get(k).getnMapperID().toString());
                                JSONObject jsonObject = new JSONObject();
                                JSONArray jsonArray = new JSONArray();
                                for (int j = 0; j < data.size(); j++) {
                                    stringBuilder.append("<ul style='list-style: none; padding: 0px; margin: 5px;'>");
                                    if (data.get(j).getnPrice().toString().equals("0")) {
                                        stringBuilder.append("<li id='" + data.get(j).getnMapperDetailID() + "'" + ">" + "+ " + data.get(j).getcAttributeValueLabel().toString() + "</li>");
                                    } else {
                                        stringBuilder.append("<li id='" + data.get(j).getnMapperDetailID() + "'" + ">" + "+ " + data.get(j).getcAttributeValueLabel().toString() + "</li>");
                                    }
                                    stringBuilder.append("</ul></div>");
                                    jsonObject.put("nAttributeID", data.get(j).getnAttributeID().toString());
                                    jsonObject.put("cAttributeLabel", "");
                                    jsonObject.put("nMapperDetailID", data.get(j).getnMapperDetailID().toString());
                                    jsonObject.put("nMapperID", data.get(j).getnMapperID().toString());
                                    jsonObject.put("nAttributeValueMasterID", data.get(j).getnAttributeValueMasterID().toString());
                                    jsonObject.put("cAttributeValueLabel", data.get(j).getcAttributeValueLabel().toString());
                                    if (data.get(j).getnPrice().toString().equals("0")) {
                                        jsonObject.put("nPrice", "");
                                    } else {
                                        jsonObject.put("nPrice", data.get(j).getnPrice().toString());
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                jsonObjectHeader.put("ListProductAttributeMapperDetail", jsonArray);
                                jsonArrayMain.put(jsonObjectHeader);

                            } catch (Exception e) {
                                e.getMessage();
                            }


                        }
                        jsonObjectHeader.length();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(CONSTANT.CAPPSTORECODE, CONSTANT.cAppStoreCode);
                            jsonObject.put(CONSTANT.CAPIPASS, CONSTANT.cAPIPass);
                            jsonObject.put(CONSTANT.CAPIKEY, CONSTANT.cAPIKey);
                            jsonObject.put(CONSTANT.NROWINDEX, "");
                            if (!CM.getSp(ViewFoodItemDetail.this, "cartId", "").equals("")) {
                                jsonObject.put(CONSTANT.NCARTID, CM.getSp(ViewFoodItemDetail.this, "cartId", "").toString());
                            } else {
                                jsonObject.put(CONSTANT.NCARTID, "0");
                            }
                            jsonObject.put(CONSTANT.NCARTDETAILID, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nCartDetailID);
                            jsonObject.put(CONSTANT.NCOMPANYPRODUCTID, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nCompanyProductID);
                            jsonObject.put(CONSTANT.NCOMPANYID, CONSTANT.nCompanyID);
                            jsonObject.put(CONSTANT.NCUSTOMERID, CM.getSp(ViewFoodItemDetail.this, "customerId", "").toString());
                            jsonObject.put(CONSTANT.NSERIALNO, "0");

                            if (CM.getSp(ViewFoodItemDetail.this, "date", "").toString().equals("")) {
                                Date date = Calendar.getInstance().getTime();
                                String currentDate = CM.converDateFormate("EEE MMM dd HH:mm:ss Z yyyy", "yyyy-MM-dd", date.toString());
                                jsonObject.put(CONSTANT.ORDERDATE, currentDate);
                            } else {
                                String currentDate = CM.converDateFormate("dd-MM-yyyy", "yyyy-MM-dd", CM.getSp(ViewFoodItemDetail.this, "date", "").toString());
                                jsonObject.put(CONSTANT.ORDERDATE, currentDate);
                            }


                            if (CM.getSp(ViewFoodItemDetail.this, "time", "").toString().equals("")) {
                                try {
                                    Date time = new Date();
                                    DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                    DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                                    DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date currentTime = readFormat.parse(String.valueOf(dateFormat.format(time)));
                                    String formattedDate = "";
                                    if (currentTime != null) {
                                        formattedDate = writeFormat.format(currentTime);
                                    }

                                    jsonObject.put(CONSTANT.ORDERTIME, formattedDate);
                                } catch (Exception e) {
                                    e.getMessage();
                                }

                            } else {
                                try {
                                    DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                    DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date date = readFormat.parse(CM.getSp(ViewFoodItemDetail.this, "time", "").toString());
                                    String formattedDate = "";
                                    if (date != null) {
                                        formattedDate = writeFormat.format(date);
                                    }
                                    jsonObject.put(CONSTANT.ORDERTIME, formattedDate);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            jsonObject.put(CONSTANT.CADDONS, stringBuilder.toString());
                            jsonObject.put(CONSTANT.NPRODUCTID, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nProductID);
                            jsonObject.put(CONSTANT.CPRODUCTNAME, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).cProductName);
                            jsonObject.put(CONSTANT.CPRODUCTDESC, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).cProductDesc);
                            jsonObject.put(CONSTANT.CPRODUCTIMAGEPATH, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).cProductImagePath);
                            jsonObject.put(CONSTANT.CCATEGORYNAME, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).cCategoryName);
                            jsonObject.put(CONSTANT.NPIECES, "");
                            jsonObject.put(CONSTANT.NPRODUCTCATEGORYID, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nProductCategoryID);
                            if (!CM.getSp(ViewFoodItemDetail.this, "quantity", "").toString().equals("")) {
                                jsonObject.put(CONSTANT.QTY, CM.getSp(ViewFoodItemDetail.this, "quantity", "").toString());
                            } else {
                                jsonObject.put(CONSTANT.QTY, "1");
                            }
                            jsonObject.put(CONSTANT.NRATE, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nRate);

                            tot = ItemDetail.getAllCheckeditemRecord();
                            if (tot != null && !tot.equals("")) {
                                jsonObject.put(CONSTANT.NADDONTOTALAMT, tot);
                            } else {
                                jsonObject.put(CONSTANT.NADDONTOTALAMT, "");
                            }
                            jsonObject.put(CONSTANT.TOTALAMOUNT, textViewItemPrice.getText().toString().trim());
                            jsonObject.put(CONSTANT.PRODUCTSUBTOTALAMT, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).ProductSubTotalAmt);
                            jsonObject.put(CONSTANT.CADDONSPRICE, addOnPrice);
                            if (!CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString().equals("null") && !CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString().equals("")) {
                                jsonObject.put(CONSTANT.NORDERFILLMODE, CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString());
                            } else {
                                jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                            }
                            jsonObject.put("ListProductAttributeMapper", jsonArrayMain);
                            new TestAsync().execute(URLS.EDITCUSTOMIZATION, jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {


                        if (categoryItemDetailModelArrayArrayList != null && categoryItemDetailModelArrayArrayList.size() > 0) { //If Addons Exist Then Select Addons
                            CM.showToast(this, "Please Select Required AddOn");
                        } else {

                            JSONObject jsonObject = new JSONObject();
                            try {

                                if (model_main != null) {
                                    jsonObject.put(CONSTANT.CAPPSTORECODE, CONSTANT.cAppStoreCode);
                                    jsonObject.put(CONSTANT.CAPIPASS, CONSTANT.cAPIPass);
                                    jsonObject.put(CONSTANT.CAPIKEY, CONSTANT.cAPIKey);
                                    jsonObject.put(CONSTANT.NROWINDEX, model_main.nRowIndex);
                                    if (!CM.getSp(ViewFoodItemDetail.this, "cartId", "").equals("")) {
                                        jsonObject.put(CONSTANT.NCARTID, CM.getSp(ViewFoodItemDetail.this, "cartId", "").toString());
                                    } else {
                                        jsonObject.put(CONSTANT.NCARTID, "0");
                                    }
                                    jsonObject.put(CONSTANT.NCARTDETAILID, "");
                                    jsonObject.put(CONSTANT.NCOMPANYPRODUCTID, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nCompanyProductID);
                                    jsonObject.put(CONSTANT.NCOMPANYID, CONSTANT.nCompanyID);
                                    jsonObject.put(CONSTANT.NCUSTOMERID, CM.getSp(ViewFoodItemDetail.this, "customerId", "").toString());
                                    jsonObject.put(CONSTANT.NSERIALNO, "0");
                                    if (CM.getSp(ViewFoodItemDetail.this, "date", "").toString().equals("")) {
                                        Date date = Calendar.getInstance().getTime();
                                        String currentDate = CM.converDateFormate("EEE MMM dd HH:mm:ss Z yyyy", "yyyy-MM-dd", date.toString());
                                        jsonObject.put(CONSTANT.ORDERDATE, currentDate);
                                    } else {
                                        String currentDate = CM.converDateFormate("dd-MM-yyyy", "yyyy-MM-dd", CM.getSp(ViewFoodItemDetail.this, "date", "").toString());
                                        jsonObject.put(CONSTANT.ORDERDATE, currentDate);
                                    }


                                    if (CM.getSp(ViewFoodItemDetail.this, "time", "").toString().equals("")) {
                                        try {
                                            Date time = new Date();
                                            DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                            DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                                            DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
                                            Date currentTime = readFormat.parse(String.valueOf(dateFormat.format(time)));
                                            String formattedDate = "";
                                            if (currentTime != null) {
                                                formattedDate = writeFormat.format(currentTime);
                                            }

                                            jsonObject.put(CONSTANT.ORDERTIME, formattedDate);
                                        } catch (Exception e) {
                                        }

                                    } else {
                                        try {
                                            DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                            DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
                                            Date date = readFormat.parse(CM.getSp(ViewFoodItemDetail.this, "time", "").toString());
                                            String formattedDate = "";
                                            if (date != null) {
                                                formattedDate = writeFormat.format(date);
                                            }

                                            jsonObject.put(CONSTANT.ORDERTIME, formattedDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    jsonObject.put(CONSTANT.CADDONS, "");
                                    jsonObject.put(CONSTANT.NPRODUCTID, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nProductID);
                                    jsonObject.put(CONSTANT.CPRODUCTNAME, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).cProductName);
                                    jsonObject.put(CONSTANT.CPRODUCTDESC, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).cProductDesc);
                                    jsonObject.put(CONSTANT.CPRODUCTIMAGEPATH, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).cProductImagePath);
                                    jsonObject.put(CONSTANT.CCATEGORYNAME, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).cCategoryName);
                                    jsonObject.put(CONSTANT.NPIECES, "");
                                    jsonObject.put(CONSTANT.NPRODUCTCATEGORYID, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nProductCategoryID);

                                    if (!CM.getSp(ViewFoodItemDetail.this, "quantity", "").toString().equals("")) {
                                        jsonObject.put(CONSTANT.QTY, CM.getSp(ViewFoodItemDetail.this, "quantity", "").toString());
                                    } else {
                                        jsonObject.put(CONSTANT.QTY, "1");
                                    }
                                    jsonObject.put(CONSTANT.NRATE, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nRate);

                                    tot = ItemDetail.getAllCheckeditemRecord();
                                    if (tot != null && !tot.equals("")) {
                                        jsonObject.put(CONSTANT.NADDONTOTALAMT, tot);
                                    } else {
                                        jsonObject.put(CONSTANT.NADDONTOTALAMT, "");
                                    }
                                    jsonObject.put(CONSTANT.TOTALAMOUNT, textViewItemPrice.getText().toString().trim());
                                    jsonObject.put(CONSTANT.PRODUCTSUBTOTALAMT, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).ProductSubTotalAmt);
                                    jsonObject.put(CONSTANT.CADDONS, stringBuilder);
                                    jsonObject.put(CONSTANT.CADDONSPRICE, model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.get(0).nAddonTotalAmt);
                                    if (!CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString().equals("null") && !CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString().equals("")) {
                                        jsonObject.put(CONSTANT.NORDERFILLMODE, CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString());
                                    } else {
                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                    }
                                    JSONArray jsonArray = new JSONArray();
                                    jsonObject.put("ListProductAttributeMapper", jsonArray);
                                    new TestAsync().execute(URLS.POSTCART, jsonObject.toString());

                                } else {
                                    CM.showToast(ViewFoodItemDetail.this, getString(R.string.cna));

                                }
                            } catch (Exception e) {

                            }
                        }
                    }
                } else {

                    //if  isEdit False


                    ArrayList<PojoItems> pojoItems = ItemDetail.getAlldataNotReruirdwithAddOnRequird();
                    if (pojoItems != null && pojoItems.size() > 0) {
                        String addOnPrice = ItemDetail.getAllCheckeditemRecord();
                        ArrayList<PojoItemDetail> allCheckedRecord = ItemDetail.getAllCheckedRecord();
                        ArrayList<PojoItems> pojoItemses = new ArrayList<>();
                        for (int i = 0; i < allCheckedRecord.size(); i++) {
                            ArrayList<PojoItems> data = Items.getAlldataUsingMapperId(allCheckedRecord.get(i).getnMapperID().toString());
                            for (int j = 0; j < data.size(); j++) {
                                PojoItems pojoItemDetail = new PojoItems();
                                pojoItemDetail.setnMapperID(data.get(j).getnMapperID().toString());
                                pojoItemDetail.setnAttributeID(data.get(j).getnAttributeID().toString());

                                if (data.get(j).getcAttributeLabel() != null) {
                                    pojoItemDetail.setcAttributeLabel(data.get(j).getcAttributeLabel().toString());
                                } else {
                                    pojoItemDetail.setcAttributeLabel("");
                                }
                                pojoItemDetail.setIsMultiple(data.get(j).getIsMultiple().toString());
                                pojoItemDetail.setIsRequired(data.get(j).getIsRequired().toString());
                                pojoItemses.add(pojoItemDetail);
                            }
                        }


                        JSONArray jsonArrayMain = new JSONArray();
                        JSONObject jsonObjectHeader = new JSONObject();
                        stringBuilder = new StringBuilder();
                        for (int k = 0; k < pojoItemses.size(); k++) {
                            try {
                                jsonObjectHeader.put("nMapperID", pojoItemses.get(k).getnMapperID());
                                jsonObjectHeader.put("nAttributeID", pojoItemses.get(k).getnAttributeID());

                                if (pojoItemses.get(k).getcAttributeLabel() != null) {
                                    jsonObjectHeader.put("cAttributeLabel", pojoItemses.get(k).getcAttributeLabel());

                                } else {
                                    jsonObjectHeader.put("cAttributeLabel", "");

                                }
                                jsonObjectHeader.put("isMultiple", pojoItemses.get(k).getIsMultiple());
                                jsonObjectHeader.put("isRequired", pojoItemses.get(k).getIsRequired());


                                if (pojoItemses.get(k).getcAttributeLabel() != null) {
                                    stringBuilder.append("<div id=" + "'StrAddon_" + pojoItemses.get(k).getnAttributeID() + "'" + "style='font-weight: normal !important;'><strong>" + pojoItemses.get(k).getcAttributeLabel() + ":</strong>");

                                } else {
                                    stringBuilder.append("");

                                }
                                ArrayList<PojoItemDetail> data = ItemDetail.getAlldataUsingMapperId(pojoItemses.get(k).getnMapperID().toString());
                                JSONObject jsonObject = new JSONObject();
                                JSONArray jsonArray = new JSONArray();
                                for (int j = 0; j < data.size(); j++) {
                                    stringBuilder.append("<ul style='list-style: none; padding: 0px; margin: 5px;'>");
                                    if (data.get(j).getnPrice().toString().equals("0")) {
                                        stringBuilder.append("<li id='" + data.get(j).getnMapperDetailID() + "'" + ">" + "+ " + data.get(j).getcAttributeValueLabel().toString() + "</li>");
                                    } else {
                                        stringBuilder.append("<li id='" + data.get(j).getnMapperDetailID() + "'" + ">" + "+ " + data.get(j).getcAttributeValueLabel().toString() + "</li>");
                                    }
                                    stringBuilder.append("</ul></div>");
                                    jsonObject.put("nAttributeID", data.get(j).getnAttributeID().toString());
                                    jsonObject.put("cAttributeLabel", "");
                                    jsonObject.put("nMapperDetailID", data.get(j).getnMapperDetailID().toString());
                                    jsonObject.put("nMapperID", data.get(j).getnMapperID().toString());
                                    jsonObject.put("nAttributeValueMasterID", data.get(j).getnAttributeValueMasterID().toString());
                                    jsonObject.put("cAttributeValueLabel", data.get(j).getcAttributeValueLabel().toString());
                                    if (data.get(j).getnPrice().toString().equals("0")) {
                                        jsonObject.put("nPrice", "");
                                    } else {
                                        jsonObject.put("nPrice", data.get(j).getnPrice().toString());
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                jsonObjectHeader.put("ListProductAttributeMapperDetail", jsonArray);
                                jsonArrayMain.put(jsonObjectHeader);

                            } catch (Exception e) {
                                e.getMessage();
                            }


                        }
                        jsonObjectHeader.length();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(CONSTANT.CAPPSTORECODE, CONSTANT.cAppStoreCode);
                            jsonObject.put(CONSTANT.CAPIPASS, CONSTANT.cAPIPass);
                            jsonObject.put(CONSTANT.CAPIKEY, CONSTANT.cAPIKey);
                            jsonObject.put(CONSTANT.NROWINDEX, model_main.nRowIndex);
                            if (!CM.getSp(ViewFoodItemDetail.this, "cartId", "").equals("")) {
                                jsonObject.put(CONSTANT.NCARTID, CM.getSp(ViewFoodItemDetail.this, "cartId", "").toString());
                            } else {
                                jsonObject.put(CONSTANT.NCARTID, "0");
                            }
                            jsonObject.put(CONSTANT.NCARTDETAILID, "");
                            jsonObject.put(CONSTANT.NCOMPANYPRODUCTID, model_main.nCompanyProductID);
                            jsonObject.put(CONSTANT.NCOMPANYID, CONSTANT.nCompanyID);
                            jsonObject.put(CONSTANT.NCUSTOMERID, CM.getSp(ViewFoodItemDetail.this, "customerId", "").toString());
                            jsonObject.put(CONSTANT.NSERIALNO, "0");

                            if (CM.getSp(ViewFoodItemDetail.this, "date", "").toString().equals("")) {
                                Date date = Calendar.getInstance().getTime();
                                String currentDate = CM.converDateFormate("EEE MMM dd HH:mm:ss Z yyyy", "yyyy-MM-dd", date.toString());
                                jsonObject.put(CONSTANT.ORDERDATE, currentDate);
                            } else {
                                String currentDate = CM.converDateFormate("dd-MM-yyyy", "yyyy-MM-dd", CM.getSp(ViewFoodItemDetail.this, "date", "").toString());
                                jsonObject.put(CONSTANT.ORDERDATE, currentDate);
                            }


                            if (CM.getSp(ViewFoodItemDetail.this, "time", "").toString().equals("")) {
                                try {
                                    Date time = new Date();
                                    DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                    DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                                    DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date currentTime = readFormat.parse(String.valueOf(dateFormat.format(time)));
                                    String formattedDate = "";
                                    if (currentTime != null) {
                                        formattedDate = writeFormat.format(currentTime);
                                    }

                                    jsonObject.put(CONSTANT.ORDERTIME, formattedDate);
                                } catch (Exception e) {
                                    e.getMessage();
                                }

                            } else {
                                try {
                                    DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                    DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
                                    Date date = readFormat.parse(CM.getSp(ViewFoodItemDetail.this, "time", "").toString());
                                    String formattedDate = "";
                                    if (date != null) {
                                        formattedDate = writeFormat.format(date);
                                    }
                                    jsonObject.put(CONSTANT.ORDERTIME, formattedDate);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            jsonObject.put(CONSTANT.CADDONS, stringBuilder.toString());
                            jsonObject.put(CONSTANT.NPRODUCTID, model_main.nProductID);
                            jsonObject.put(CONSTANT.CPRODUCTNAME, model_main.cProductName);
                            jsonObject.put(CONSTANT.CPRODUCTDESC, model_main.cProductDesc);
                            jsonObject.put(CONSTANT.CPRODUCTIMAGEPATH, model_main.cProductImagePath);
                            jsonObject.put(CONSTANT.CCATEGORYNAME, model_main.cCategoryName);
                            jsonObject.put(CONSTANT.NPIECES, model_main.nPieces);
                            jsonObject.put(CONSTANT.NPRODUCTCATEGORYID, model_main.nProductCategoryID);
                            if (!CM.getSp(ViewFoodItemDetail.this, "quantity", "").toString().equals("")) {
                                jsonObject.put(CONSTANT.QTY, CM.getSp(ViewFoodItemDetail.this, "quantity", "").toString());
                            } else {
                                jsonObject.put(CONSTANT.QTY, "1");
                            }
                            jsonObject.put(CONSTANT.NRATE, model_main.nRate);

                            tot = ItemDetail.getAllCheckeditemRecord();
                            if (tot != null && !tot.equals("")) {
                                jsonObject.put(CONSTANT.NADDONTOTALAMT, tot);
                            } else {
                                jsonObject.put(CONSTANT.NADDONTOTALAMT, "");
                            }
                            jsonObject.put(CONSTANT.TOTALAMOUNT, textViewItemPrice.getText().toString().trim());
                            jsonObject.put(CONSTANT.PRODUCTSUBTOTALAMT, model_main.ProductSubTotalAmt);
                            jsonObject.put(CONSTANT.CADDONSPRICE, addOnPrice);
                            if (!CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString().equals("null") && !CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString().equals("")) {
                                jsonObject.put(CONSTANT.NORDERFILLMODE, CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString());
                            } else {
                                jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                            }
                            jsonObject.put("ListProductAttributeMapper", jsonArrayMain);
                            new TestAsync().execute(URLS.POSTCART, jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {


                        if (categoryItemDetailModelArrayArrayList != null && categoryItemDetailModelArrayArrayList.size() > 0) { //If Addons Exist Then Select Addons
                            CM.showToast(this, "Please Select Required AddOn");
                        } else {

                            JSONObject jsonObject = new JSONObject();
                            try {

                                if (model_main != null) {
                                    jsonObject.put(CONSTANT.CAPPSTORECODE, CONSTANT.cAppStoreCode);
                                    jsonObject.put(CONSTANT.CAPIPASS, CONSTANT.cAPIPass);
                                    jsonObject.put(CONSTANT.CAPIKEY, CONSTANT.cAPIKey);
                                    jsonObject.put(CONSTANT.NROWINDEX, model_main.nRowIndex);
                                    if (!CM.getSp(ViewFoodItemDetail.this, "cartId", "").equals("")) {
                                        jsonObject.put(CONSTANT.NCARTID, CM.getSp(ViewFoodItemDetail.this, "cartId", "").toString());
                                    } else {
                                        jsonObject.put(CONSTANT.NCARTID, "0");
                                    }
                                    jsonObject.put(CONSTANT.NCARTDETAILID, "");
                                    jsonObject.put(CONSTANT.NCOMPANYPRODUCTID, model_main.nCompanyProductID);
                                    jsonObject.put(CONSTANT.NCOMPANYID, CONSTANT.nCompanyID);
                                    jsonObject.put(CONSTANT.NCUSTOMERID, CM.getSp(ViewFoodItemDetail.this, "customerId", "").toString());
                                    jsonObject.put(CONSTANT.NSERIALNO, "0");
                                    if (CM.getSp(ViewFoodItemDetail.this, "date", "").toString().equals("")) {
                                        Date date = Calendar.getInstance().getTime();
                                        String currentDate = CM.converDateFormate("EEE MMM dd HH:mm:ss Z yyyy", "yyyy-MM-dd", date.toString());
                                        jsonObject.put(CONSTANT.ORDERDATE, currentDate);
                                    } else {
                                        String currentDate = CM.converDateFormate("dd-MM-yyyy", "yyyy-MM-dd", CM.getSp(ViewFoodItemDetail.this, "date", "").toString());
                                        jsonObject.put(CONSTANT.ORDERDATE, currentDate);
                                    }


                                    if (CM.getSp(ViewFoodItemDetail.this, "time", "").toString().equals("")) {
                                        try {
                                            Date time = new Date();
                                            DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                            DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                                            DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
                                            Date currentTime = readFormat.parse(String.valueOf(dateFormat.format(time)));
                                            String formattedDate = "";
                                            if (currentTime != null) {
                                                formattedDate = writeFormat.format(currentTime);
                                            }

                                            jsonObject.put(CONSTANT.ORDERTIME, formattedDate);
                                        } catch (Exception e) {
                                        }

                                    } else {
                                        try {
                                            DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                                            DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
                                            Date date = readFormat.parse(CM.getSp(ViewFoodItemDetail.this, "time", "").toString());
                                            String formattedDate = "";
                                            if (date != null) {
                                                formattedDate = writeFormat.format(date);
                                            }

                                            jsonObject.put(CONSTANT.ORDERTIME, formattedDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    jsonObject.put(CONSTANT.CADDONS, "");
                                    jsonObject.put(CONSTANT.NPRODUCTID, model_main.nProductID);
                                    jsonObject.put(CONSTANT.CPRODUCTNAME, model_main.cProductName);
                                    jsonObject.put(CONSTANT.CPRODUCTDESC, model_main.cProductDesc);
                                    jsonObject.put(CONSTANT.CPRODUCTIMAGEPATH, model_main.cProductImagePath);
                                    jsonObject.put(CONSTANT.CCATEGORYNAME, model_main.cCategoryName);
                                    jsonObject.put(CONSTANT.NPIECES, model_main.nPieces);
                                    jsonObject.put(CONSTANT.NPRODUCTCATEGORYID, model_main.nProductCategoryID);

                                    if (!CM.getSp(ViewFoodItemDetail.this, "quantity", "").toString().equals("")) {
                                        jsonObject.put(CONSTANT.QTY, CM.getSp(ViewFoodItemDetail.this, "quantity", "").toString());
                                    } else {
                                        jsonObject.put(CONSTANT.QTY, "1");
                                    }
                                    jsonObject.put(CONSTANT.NRATE, model_main.nRate);

                                    tot = ItemDetail.getAllCheckeditemRecord();
                                    if (tot != null && !tot.equals("")) {
                                        jsonObject.put(CONSTANT.NADDONTOTALAMT, tot);
                                    } else {
                                        jsonObject.put(CONSTANT.NADDONTOTALAMT, "");
                                    }
                                    jsonObject.put(CONSTANT.TOTALAMOUNT, textViewItemPrice.getText().toString().trim());
                                    jsonObject.put(CONSTANT.PRODUCTSUBTOTALAMT, model_main.ProductSubTotalAmt);
                                    jsonObject.put(CONSTANT.CADDONS, model_main.cAddons);
                                    jsonObject.put(CONSTANT.CADDONSPRICE, "0");
                                    if (!CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString().equals("null") && !CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString().equals("")) {
                                        jsonObject.put(CONSTANT.NORDERFILLMODE, CM.getSp(ViewFoodItemDetail.this, "DefPmode", "").toString());
                                    } else {
                                        jsonObject.put(CONSTANT.NORDERFILLMODE, "2");
                                    }
                                    JSONArray jsonArray = new JSONArray();
                                    jsonObject.put("ListProductAttributeMapper", jsonArray);
                                    new TestAsync().execute(URLS.POSTCART, jsonObject.toString());

                                } else {
                                    CM.showToast(ViewFoodItemDetail.this, getString(R.string.cna));

                                }
                            } catch (Exception e) {

                            }
                        }
                    }


                }
                break;
            case R.id.itemdetailbtnAddon:
                Intent i = new Intent(ViewFoodItemDetail.this, ViewCustomize.class);
                CM.startActivityForResult(i, 100, ViewFoodItemDetail.this);
                break;
            case R.id.btnSpecialCommnent:
                showPopUp();
                break;
            case R.id.cover_pic:

//                Intent intent = new Intent(ViewFoodItemDetail.this, viewFullScreenImage.class);
//                intent.putExtra("imagePaht", model_main.cProductImagePath);
//                intent.putExtra("title", model_main.cProductName);
//
//                CM.startActivity(intent, this);
                break;

        }
    }


    public class DefaultLimitExceededListener implements LimitExceededListener {

        public void limitExceeded(int limit, int exceededValue) {

            String message = String.format("Value cannot be setted to %d because the limit is %d.", limit, exceededValue);
            Log.v(this.getClass().getSimpleName(), message);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showPopUp() {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(ViewFoodItemDetail.this);
        helpBuilder.setTitle(getString(R.string.orderForTime));
        helpBuilder.setMessage(getString(R.string.content));
        View child = getLayoutInflater().inflate(R.layout.itemdetailpopup, null);
        helpBuilder.setView(child);
        final EditText editTextDate = (EditText) child.findViewById(R.id.conatctUsCommnent);
        helpBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        collapsingToolbarLayout.invalidate();
                    }
                });

        helpBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                collapsingToolbarLayout.invalidate();
            }
        });


        // Remember, create doesn't show the dialog
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();

        helpDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Boolean wantToCloseDialog = false;


                //Do stuff, possibly set wantToCloseDialog to true then...
                if (!editTextDate.getText().toString().equals("")) {
                    helpDialog.dismiss();
                } else {


                    CM.showToast(ViewFoodItemDetail.this, getString(R.string.validateCate));
                }
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fooddetail, menu);
        MenuItem settingsItem = menu.findItem(R.id.addFav);

        if (model_main != null) {
            if (model_main.isfavourite != null) {
                if (model_main.isfavourite.equals("true")) {
                    settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                    settingsItem.setCheckable(false);
                } else {
                    settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                    settingsItem.setCheckable(true);
                }

            }


        } else {
            if (isFav.equals("true")) {
                settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                settingsItem.setCheckable(false);
            } else {
                settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                settingsItem.setCheckable(true);
            }

        }


        if (isAdded) {
            settingsItem.setIcon(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
            settingsItem.setCheckable(false);
        } else {
            settingsItem.setCheckable(true);
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addFav:
                webAddToFav(comProductId, CM.getSp(ViewFoodItemDetail.this, "customerId", "").toString());

                break;
        }
        return true;
    }


    public void webCallEditFoodItemDetail(String cartId, String cusId, String comProductId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewFoodItemDetail.this, true, true);
            WebService.getResendForEditCategoryItemDetail(v, cartId, cusId, comProductId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForEditCartDetail(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewFoodItemDetail.this)) {
                        CM.showPopupCommonValidation(ViewFoodItemDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForEditCartDetail(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewFoodItemDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("ResponseCode").equals("202")) {
                try {
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("ResponseObject"));
                    JSONArray jsonArray = new JSONArray(jsonObject1.getString("productDetail"));
                    model_mainEdit = CM.JsonParse(new EditCartModel(), jsonObject.getString("ResponseObject"));
                    model_mainEdit.editCartModelArrays.get(0).editCartModelArraySubs.size();

                    pojoCustomizes = new ArrayList<>();
                    pojoCustomizes.clear();
                    pojoItemDetails = new ArrayList<>();
                    pojoItemDetails.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        isFav = jsonArray.getJSONObject(i).optString("isfavouirate");
                        if (isFav != null && !isFav.equals("") && isFav.equals("true")) {
                            invalidateOptionsMenu();
                        }

                        JSONArray jsonArray1 = new JSONArray(jsonArray.getJSONObject(i).getString("ListProductAttributeMapper"));
                        for (int j = 0; j < jsonArray1.length(); j++) {
                            PojoItems pojoCustomize = new PojoItems();
                            pojoCustomize.setnMapperID(jsonArray1.getJSONObject(j).getString("nMapperID"));
                            pojoCustomize.setnAttributeID(jsonArray1.getJSONObject(j).getString("nAttributeID"));
                            pojoCustomize.setcAttributeLabel(jsonArray1.getJSONObject(j).getString("cAttributeLabel"));
                            pojoCustomize.setIsMultiple(jsonArray1.getJSONObject(j).getString("isMultiple"));
                            pojoCustomize.setIsRequired(jsonArray1.getJSONObject(j).getString("isRequired"));
                            isMulti = jsonArray1.getJSONObject(j).getString("isMultiple");
                            isRequired = jsonArray1.getJSONObject(j).getString("isRequired");
                            if (jsonArray1.getJSONObject(j).getString("cAttributeLabel") != null) {
                                header = jsonArray1.getJSONObject(j).getString("cAttributeLabel");
                            } else {
                                header = "";
                            }
                            pojoCustomizes.add(pojoCustomize);
                            JSONArray jsonArray2 = new JSONArray(jsonArray1.getJSONObject(j).getString("ListProductAttributeMapperDetail"));
                            for (int k = 0; k < jsonArray2.length(); k++) {
                                PojoItemDetail pojoItemDetail = new PojoItemDetail();
                                pojoItemDetail.setnAttributeID(jsonArray2.getJSONObject(k).getString("nAttributeID"));
                                pojoItemDetail.setcAttributeLabel(jsonArray2.getJSONObject(k).getString("cAttributeLabel"));
                                pojoItemDetail.setnMapperDetailID(jsonArray2.getJSONObject(k).getString("nMapperDetailID"));
                                pojoItemDetail.setnMapperID(jsonArray2.getJSONObject(k).getString("nMapperID"));
                                pojoItemDetail.setnAttributeValueMasterID(jsonArray2.getJSONObject(k).getString("nAttributeValueMasterID"));
                                pojoItemDetail.setcAttributeValueLabel(jsonArray2.getJSONObject(k).getString("cAttributeValueLabel"));
                                pojoItemDetail.setnPrice(jsonArray2.getJSONObject(k).getString("nPrice"));
                                pojoItemDetail.setIsChecked("0");
                                pojoItemDetail.setIsMulti(isMulti);
                                pojoItemDetail.setIsRequired(isRequired);
                                pojoItemDetail.setHeader(header);
                                pojoItemDetails.add(pojoItemDetail);
                            }
                        }

                        try {
                            Items.Insert(pojoCustomizes);
                            ItemDetail.Insert(pojoItemDetails);
                        } catch (Exception e) {
                            Log.e("dbError", String.valueOf(e.getMessage()));
                        }


                        JSONArray cartItem = new JSONArray(jsonArray.getJSONObject(i).getString("cartItems"));
                        for (int j = 0; j < cartItem.length(); j++) {


                            if (cartItem.getJSONObject(j).optString("isfavourite") != null && cartItem.getJSONObject(j).optString("isfavourite").equals("true")) {
                                invalidateOptionsMenu();
                            }


                            if (cartItem.getJSONObject(j).getString("nRate") != null) {
                                CM.setSp(ViewFoodItemDetail.this, "itemPrice", cartItem.getJSONObject(j).getString("nRate"));
                            } else {
                                CM.setSp(ViewFoodItemDetail.this, "itemPrice", "0.00");
                            }


                            toolbar.setTitle(cartItem.getJSONObject(j).getString("cProductName"));
                            collapsingToolbarLayout.setTitle(cartItem.getJSONObject(j).getString("cProductName"));


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                try {
                                    textViewItemDis.setText((Html.fromHtml(cartItem.getJSONObject(j).optString("cProductDesc"), Html.FROM_HTML_MODE_LEGACY)));
                                } catch (Exception e) {
                                }
                            } else {
                                try {
                                    textViewItemDis.setText(Html.fromHtml((cartItem.getJSONObject(j).optString("cProductDesc"))));
                                } catch (Exception e) {

                                }

                            }


                            DecimalFormat form = new DecimalFormat("0.00");

                            textViewItemPrice.setText(CM.getSp(ViewFoodItemDetail.this, "currency", "").toString() + form.format(Double.parseDouble(cartItem.getJSONObject(j).getString("nRate"))));
                            Glide.with(ViewFoodItemDetail.this)
                                    .load(cartItem.getJSONObject(j).getString("cProductImagePath"))
                                    .listener(new RequestListener<String, GlideDrawable>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                            imageViewPlace.setVisibility(View.VISIBLE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                            imageViewPlace.setVisibility(View.GONE);
                                            return false;
                                        }
                                    }).error(R.drawable.shortplace).into(imageViewCoverImage);


                            JSONArray jsonArray2 = new JSONArray(cartItem.getJSONObject(j).getString("ProductAssignedAddonsJSON"));
                            for (int k = 0; k < jsonArray2.length(); k++) {


                                if (CM.CheckIsDataAlreadyInDBorNot("Items", "nMapperID", jsonArray2.getJSONObject(k).getString("nMapperID"))) {
                                    JSONArray jsonArray3 = new JSONArray(jsonArray2.getJSONObject(k).getString("ListProductAttributeMapperDetail"));
                                    for (int l = 0; l < jsonArray3.length(); l++) {

                                        Double d = new Double(cartItem.getJSONObject(j).optString("Qty").toString());
                                        numberPicker.setValue(d.intValue());
                                        CM.setSp(ViewFoodItemDetail.this, "quantity", cartItem.getJSONObject(j).optString("Qty").toString());
                                        try {
                                            Double aFloat;
                                            try {
                                                aFloat = ((Double.parseDouble(cartItem.getJSONObject(j).optString("nRate").toString()) + Double.parseDouble(cartItem.getJSONObject(j).optString("nAddonTotalAmt").toString())) * Double.parseDouble(cartItem.getJSONObject(j).optString("Qty")));
                                                textViewItemPrice.setText("Rs. " + String.valueOf(aFloat));
                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        } catch (Exception e) {
                                            e.getMessage();
                                        }


                                        if (CM.CheckIsDataAlreadyInDBorNot("ItemDetail", "nMapperDetailID", jsonArray3.getJSONObject(l).getString("nMapperDetailID"))) {
                                            if (jsonArray2.getJSONObject(k).getString("isMultiple").toString().equals("false")) {
                                                ItemDetail.updateRadioButton("1", jsonArray3.getJSONObject(l).getString("nMapperDetailID"), jsonArray2.getJSONObject(k).getString("nMapperID"));
                                            } else {
                                                ItemDetail.updateCheckBox("1", jsonArray3.getJSONObject(l).getString("nMapperDetailID"), jsonArray2.getJSONObject(k).getString("nMapperID"));

                                            }
                                        } else {
                                            Log.e("false", "");
                                        }

                                    }

                                } else {
                                    Log.e("false", "");
                                }

                            }


                        }


                    }


                    prepareListDataForRequiredAddOn();
                    prepareListDataForNotRequiredAddOn();


                } catch (Exception e) {
                    Log.d(TAG, "getResponseForCartDat: " + e.getMessage());

                }
            }

        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewFoodItemDetail.this, e.getMessage(), false);
        }
    }

    public void webCallFoodItemDetail(String compProid, String cusId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewFoodItemDetail.this, true, true);
            WebService.getResendForCategoryItemDetail(v, compProid, cusId, new OnVolleyHandler() {
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
                    if (CM.isInternetAvailable(ViewFoodItemDetail.this)) {
                        CM.showPopupCommonValidation(ViewFoodItemDetail.this, error, false);
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
            CM.showPopupCommonValidation(ViewFoodItemDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {

            JSONObject jsonObject = new JSONObject(response);


            if (jsonObject.getString("ResponseCode") != null && jsonObject.getString("ResponseCode").equals("202")) {
                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("ResponseObject"));
                if (!jsonObject1.getString("product").toString().equals("null")) {

                    model_main = CM.JsonParse(new CategoryItemDetailModel(), jsonObject1.getString("product"));
                    if (categoryItemDetailModelArrayArrayList != null) {
                        categoryItemDetailModelArrayArrayList.clear();
                    }
                    categoryItemDetailModelArrayArrayList = model_main.categoryItemDetailModelArrays;
                    categoryItemDetailModelArrayArrayList.size();
                    try {

                        if (model_main.isfavourite != null && model_main.isfavourite.equals("true")) {
                            invalidateOptionsMenu();
                        }

                        if (model_main.nRate != null) {
                            CM.setSp(ViewFoodItemDetail.this, "itemPrice", model_main.nRate);
                        } else {
                            CM.setSp(ViewFoodItemDetail.this, "itemPrice", "0.00");
                        }

                        toolbar.setTitle(model_main.cProductName);
                        collapsingToolbarLayout.setTitle(model_main.cProductName);


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            try {
                                textViewItemDis.setText((Html.fromHtml(model_main.cProductDesc, Html.FROM_HTML_MODE_LEGACY)));
                            } catch (Exception e) {
                            }
                        } else {
                            try {
                                textViewItemDis.setText(Html.fromHtml((model_main.cProductDesc)));
                            } catch (Exception e) {

                            }

                        }


                        DecimalFormat form = new DecimalFormat("0.00");

                        textViewItemPrice.setText(CM.getSp(ViewFoodItemDetail.this, "currency", "").toString() + form.format(Double.parseDouble(model_main.nRate)));
                        Glide.with(ViewFoodItemDetail.this)
                                .load(model_main.cProductImagePath)
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        imageViewPlace.setVisibility(View.VISIBLE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        imageViewPlace.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).error(R.drawable.shortplace).into(imageViewCoverImage);
                        pojoCustomizes = new ArrayList<>();
                        pojoCustomizes.clear();
                        pojoItemDetails = new ArrayList<>();
                        pojoItemDetails.clear();
                        String isMulti;
                        for (int i = 0; i < categoryItemDetailModelArrayArrayList.size(); i++) {

                            PojoItems pojoCustomize = new PojoItems();
                            pojoCustomize.setnMapperID(categoryItemDetailModelArrayArrayList.get(i).nMapperID);
                            pojoCustomize.setnAttributeID(categoryItemDetailModelArrayArrayList.get(i).nAttributeID);
                            if (categoryItemDetailModelArrayArrayList.get(i).cAttributeLabel != null) {
                                pojoCustomize.setcAttributeLabel(categoryItemDetailModelArrayArrayList.get(i).cAttributeLabel);

                            } else {
                                pojoCustomize.setcAttributeLabel("");

                            }
                            pojoCustomize.setIsMultiple(categoryItemDetailModelArrayArrayList.get(i).isMultiple);
                            pojoCustomize.setIsRequired(categoryItemDetailModelArrayArrayList.get(i).isRequired);
                            isMulti = categoryItemDetailModelArrayArrayList.get(i).isMultiple;
                            isRequired = categoryItemDetailModelArrayArrayList.get(i).isRequired;
                            if (categoryItemDetailModelArrayArrayList.get(i).cAttributeLabel != null) {
                                header = categoryItemDetailModelArrayArrayList.get(i).cAttributeLabel;

                            } else {
                                header = "";

                            }


                            pojoCustomizes.add(pojoCustomize);
                            for (int j = 0; j < categoryItemDetailModelArrayArrayList.get(i).categoryItemDetailModelSubArrays.size(); j++) {
                                PojoItemDetail pojoItemDetail = new PojoItemDetail();
                                pojoItemDetail.setnAttributeID(categoryItemDetailModelArrayArrayList.get(i).categoryItemDetailModelSubArrays.get(j).nAttributeID);
                                pojoItemDetail.setcAttributeLabel(categoryItemDetailModelArrayArrayList.get(i).categoryItemDetailModelSubArrays.get(j).cAttributeLabel);
                                pojoItemDetail.setnMapperDetailID(categoryItemDetailModelArrayArrayList.get(i).categoryItemDetailModelSubArrays.get(j).nMapperDetailID);
                                pojoItemDetail.setnMapperID(categoryItemDetailModelArrayArrayList.get(i).categoryItemDetailModelSubArrays.get(j).nMapperID);
                                pojoItemDetail.setnAttributeValueMasterID(categoryItemDetailModelArrayArrayList.get(i).categoryItemDetailModelSubArrays.get(j).nAttributeValueMasterID);
                                pojoItemDetail.setcAttributeValueLabel(categoryItemDetailModelArrayArrayList.get(i).categoryItemDetailModelSubArrays.get(j).cAttributeValueLabel);
                                pojoItemDetail.setnPrice(categoryItemDetailModelArrayArrayList.get(i).categoryItemDetailModelSubArrays.get(j).nPrice);
                                pojoItemDetail.setIsChecked("0");
                                pojoItemDetail.setIsMulti(isMulti);
                                pojoItemDetail.setIsRequired(isRequired);
                                pojoItemDetail.setHeader(header);
                                pojoItemDetails.add(pojoItemDetail);
                            }

                        }

                        if (categoryItemDetailModelArrayArrayList.size() >= 1) {
                            itemPrice = pojoItemDetails.get(0).getnPrice();
                            addOnHeaderLayout.setVisibility(View.VISIBLE);
                        } else {
                            if (categoryItemDetailModelArrayArrayList.size() >= 2) {
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayoutExpandList.getLayoutParams();
                                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
                                params.height = height;
                                linearLayoutExpandList.setLayoutParams(params);
                            }
                            addOnHeaderLayout.setVisibility(View.GONE);
                            itemPrice = "0.00";
                        }
                        CustomSpinnerAdapterAddOn mySpinnerArrayAdapter = new CustomSpinnerAdapterAddOn(ViewFoodItemDetail.this, pojoItemDetails);
                        spinner.setAdapter(mySpinnerArrayAdapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                itemPrice = pojoItemDetails.get(position).getnPrice();
                                Double aFloat = Double.parseDouble(itemPrice) * Double.parseDouble(itemQuantity);
                                textViewItemPrice.setText(CM.getSp(ViewFoodItemDetail.this, "currency", "").toString() + "" + String.valueOf(aFloat));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });
                        try {
                            Items.Insert(pojoCustomizes);
                            ItemDetail.Insert(pojoItemDetails);
                        } catch (Exception e) {
                            Log.e("dbError", String.valueOf(e.getMessage()));
                        }
                        prepareListDataForRequiredAddOn();
                        prepareListDataForNotRequiredAddOn();

                    } catch (Exception e) {
                        CM.showPopupCommonValidation(ViewFoodItemDetail.this, e.getMessage(), false);
                    }
                } else {
                    addOnHeaderLayout.setVisibility(View.GONE);
                    CM.showToast(ViewFoodItemDetail.this, "No Date Found");
                }
            } else {
                if (jsonObject.getString("ResponseObject").equals("null")) {
                    CM.showToast(ViewFoodItemDetail.this, "No Data Found");
                } else {
                    CM.showToast(ViewFoodItemDetail.this, jsonObject.getString("ResponseObject"));
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    class TestAsync extends AsyncTask<String, Integer, String> {
        String TAG = getClass().getSimpleName();
        private String result;

        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(ViewFoodItemDetail.this);
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
                StringEntity se = new StringEntity(json);
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

                        CM.setSp(ViewFoodItemDetail.this, "quantity", "");
                        CartItem model_main = CM.JsonParse(new CartItem(), jsonObject1.getString("ResponseObject").toString());
                        if (CM.getSp(ViewFoodItemDetail.this, "cartId", "").equals("")) {
                            CM.setSp(ViewFoodItemDetail.this, "cartId", model_main.nCartID.toString());
                        }
                        CM.setSp(ViewFoodItemDetail.this, "cartItem", model_main.nCartItemCount.toString());
                        CM.showToast(ViewFoodItemDetail.this, "Item Added to cart");
                        Intent intent = ViewFoodItemDetail.this.getIntent();
                        intent.putExtra("count", model_main.nCartItemCount.toString());
                        setResult(RESULT_OK, intent);
                        finish();
                        Items.deleteAll();
                        ItemDetail.deleteAll();
                    } else {
                        CM.showToast(ViewFoodItemDetail.this, jsonObject1.getString("ResponseObject").toString());
                        Log.e("error", jsonObject1.getString("ResponseObject").toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                CM.showToast(ViewFoodItemDetail.this, getString(R.string.serverNotWork));
            }

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

    private void prepareListDataForRequiredAddOn() {
        listDataHeader = new ArrayList<String>();
        listDataChild1 = new HashMap<>();
        listDataHeader.clear();
        listDataChild1.clear();

        if (pojoItemses != null && pojoItemses.size() > 0) {
            pojoItemses.clear();
        }
        pojoItemses = Items.getAlldata();
        if (pojoItemses != null) {
            for (int j = 0; j < pojoItemses.size(); j++) {

                if (pojoItemses.get(j).getcAttributeLabel() != null) {
                    listDataHeader.add(pojoItemses.get(j).getcAttributeLabel());
                } else {
                    listDataHeader.add("");
                }
                pojoItemDetails1 = ItemDetail.getSelectedIdRecord(pojoItemses.get(j).getnMapperID());
                pojoArrays = new ArrayList<>();
                pojoArrays.clear();
                for (int i = 0; i < pojoItemDetails1.size(); i++) {
                    PojoArray pojoArray = new PojoArray();
                    pojoArray.setnAttributeID(pojoItemDetails1.get(i).getnAttributeID());
                    if (pojoItemDetails1.get(i).getcAttributeLabel() != null) {
                        pojoArray.setcAttributeLabel(pojoItemDetails1.get(i).getcAttributeLabel());
                    } else {
                        pojoArray.setcAttributeLabel("");
                    }
                    pojoArray.setnMapperDetailID(pojoItemDetails1.get(i).getnMapperDetailID());
                    pojoArray.setnMapperID(pojoItemDetails1.get(i).getnMapperID());
                    pojoArray.setcAttributeValueLabel(pojoItemDetails1.get(i).getcAttributeValueLabel());
                    pojoArray.setnAttributeValueMasterID(pojoItemDetails1.get(i).getnAttributeValueMasterID());
                    pojoArray.setnPrice(pojoItemDetails1.get(i).getnPrice());
                    pojoArray.setIsChecked(pojoItemDetails1.get(i).getIsChecked());
                    pojoArray.setIsMulti(pojoItemDetails1.get(i).getIsMulti());
                    pojoArray.setHeader(pojoItemDetails1.get(i).getHeader());
                    pojoArrays.add(pojoArray);
                }

                if (pojoItemses.get(j).getcAttributeLabel() != null) {
                    listDataChild1.put(pojoItemses.get(j).getcAttributeLabel(), pojoArrays);

                } else {
                    listDataChild1.put("", pojoArrays);

                }
            }
            if (listAdapter != null) {
                listAdapter = null;
            }

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild1);
            expListView.setAdapter(listAdapter);
            expListView.expandGroup(0);
            expListView.invalidateViews();
            listAdapter.notifyDataSetChanged();
            item = (TextView) findViewById(R.id.textViewGrandTotal);
            String tot = ItemDetail.getAllCheckeditemRecord();
            try {
                double d = (Double.parseDouble(CM.getSp(ViewFoodItemDetail.this, "itemPrice", "").toString()) + (Double.parseDouble(tot)));
                item.setText("Total " + CM.getSp(ViewFoodItemDetail.this, "currency", "").toString() + " " + String.valueOf(d));
            } catch (Exception e) {

            }
        }
    }

    private void prepareListDataForNotRequiredAddOn() {
        listDataHeader = new ArrayList<String>();
        listDataChild1 = new HashMap<>();
        listDataHeader.clear();
        listDataChild1.clear();

        if (pojoItemses != null && pojoItemses.size() > 0) {
            pojoItemses.clear();
        }
        pojoItemses = Items.getAlldataNotReruird();

        if (pojoItemses != null) {

        } else {
            layoutNotReq.setVisibility(View.GONE);
        }
        if (pojoItemses != null) {
            for (int j = 0; j < pojoItemses.size(); j++) {

                if (pojoItemses.get(j).getcAttributeLabel() != null) {
                    listDataHeader.add(pojoItemses.get(j).getcAttributeLabel());
                } else {
                    listDataHeader.add("");
                }

                pojoItemDetails1 = ItemDetail.getSelectedIdRecord(pojoItemses.get(j).getnMapperID());
                pojoArrays = new ArrayList<>();
                pojoArrays.clear();
                for (int i = 0; i < pojoItemDetails1.size(); i++) {
                    PojoArray pojoArray = new PojoArray();
                    pojoArray.setnAttributeID(pojoItemDetails1.get(i).getnAttributeID());
                    if (pojoItemDetails1.get(i).getcAttributeLabel() != null) {
                        pojoArray.setcAttributeLabel(pojoItemDetails1.get(i).getcAttributeLabel());

                    } else {
                        pojoArray.setcAttributeLabel("");

                    }
                    pojoArray.setnMapperDetailID(pojoItemDetails1.get(i).getnMapperDetailID());
                    pojoArray.setnMapperID(pojoItemDetails1.get(i).getnMapperID());
                    pojoArray.setcAttributeValueLabel(pojoItemDetails1.get(i).getcAttributeValueLabel());
                    pojoArray.setnAttributeValueMasterID(pojoItemDetails1.get(i).getnAttributeValueMasterID());
                    pojoArray.setnPrice(pojoItemDetails1.get(i).getnPrice());
                    pojoArray.setIsChecked(pojoItemDetails1.get(i).getIsChecked());
                    pojoArray.setIsMulti(pojoItemDetails1.get(i).getIsMulti());
                    pojoArray.setHeader(pojoItemDetails1.get(i).getHeader());
                    pojoArrays.add(pojoArray);
                }


                if (pojoItemses.get(j).getcAttributeLabel() != null) {
                    listDataChild1.put(pojoItemses.get(j).getcAttributeLabel(), pojoArrays);
                } else {
                    listDataChild1.put("", pojoArrays);
                }
            }
            expandableListAdapterNot = new ExpandableListAdapterNot(this, listDataHeader, listDataChild1);
            expListView1.setAdapter(expandableListAdapterNot);
            expListView1.expandGroup(0);

            item = (TextView) findViewById(R.id.textViewGrandTotal);
            String tot = ItemDetail.getAllCheckeditemRecord();
            try {
                double d = (double) Float.parseFloat(tot);
                item.setText("Total " + CM.getSp(ViewFoodItemDetail.this, "currency", "").toString() + " " + String.valueOf(d));

            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    String checkNull(String string) {
        String data = "";
        String str = (string == null) ? "Null" : string;
        if (str.equals("Null")) {
            data = string;
        }
        return data;
    }


    public void webAddToFav(String comproId, String cusId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewFoodItemDetail.this, false, false);
            WebService.getResponseAddToFav(v, comproId, cusId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForAddToFav(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewFoodItemDetail.this)) {
                        CM.showPopupCommonValidation(ViewFoodItemDetail.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForAddToFav(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewFoodItemDetail.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("ResponseCode").equals("202")) {
                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("ResponseObject"));
                CM.showToast(this, "Item Added To Add To Favorites");
                isAdded = true;
                invalidateOptionsMenu();
            }


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewFoodItemDetail.this, e.getMessage(), false);
        }
    }


}
