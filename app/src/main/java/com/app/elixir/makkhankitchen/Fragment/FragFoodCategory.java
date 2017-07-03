package com.app.elixir.makkhankitchen.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.CategoryModel;
import com.app.elixir.makkhankitchen.Model.CategoryModelArray;
import com.app.elixir.makkhankitchen.Model.CompanyModel;
import com.app.elixir.makkhankitchen.Model.CompanyModelArray;
import com.app.elixir.makkhankitchen.Model.NotiModel;
import com.app.elixir.makkhankitchen.Model.TimeModel;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.adapter.CustomSpinnerAdapter;
import com.app.elixir.makkhankitchen.adapter.adptCategory;
import com.app.elixir.makkhankitchen.database.tbl_notification;
import com.app.elixir.makkhankitchen.interfac.ActionBarTitleSetter;
import com.app.elixir.makkhankitchen.interfac.OnFragmentInteractionListener;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListener;
import com.app.elixir.makkhankitchen.mtplview.MtplLog;
import com.app.elixir.makkhankitchen.mtplview.MtplTextView;
import com.app.elixir.makkhankitchen.utils.CM;
import com.app.elixir.makkhankitchen.utils.CV;
import com.app.elixir.makkhankitchen.utils.Util;
import com.app.elixir.makkhankitchen.volly.OnVolleyHandler;
import com.app.elixir.makkhankitchen.volly.VolleyIntialization;
import com.app.elixir.makkhankitchen.volly.WebService;
import com.app.elixir.makkhankitchen.volly.WebServiceTag;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * Created by Elixir on 02-Aug-2016.
 */
public class FragFoodCategory extends Fragment implements View.OnClickListener, View.OnTouchListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "FragFoodCategory";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    Activity thisActivity;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    public adptCategory mAdapter;
    private ArrayList<CategoryModelArray> categoryModelArrays;
    ArrayList<CategoryModelArray> categoryModelArraysMain;

    private EditText editTextDate;
    private Spinner editTextTime;
    private EditText editTextDatePopup;
    private Spinner editTextTimePopup;
    private LinearLayout rootLayout;
    private LinearLayout noInternetLayout, internetLayout;
    private ArrayList<String> times;
    private TimeModel model_main;
    private AlertDialog helpDialog;
    private int count = 0;
    private MtplTextView textViewCatOne, textViewCatTwo, textViewCatThree, textViewCatFour;
    private ImageView imageViewCatone, imageViewCatTwo, imageViewCatThree, imageViewCatFour;
    private ImageView imageView;
    private ImageView imageView1;
    private ProgressBar progressBar;
    private ProgressBar progressBar1;
    private ArrayList<CompanyModelArray> companyModelArrays;
    private ProgressDialog pDialog;
    private LinearLayout linearLayoutCat1, linearLayoutCat2, linearLayoutCat3, linearLayoutCat4;

    private static final int VISIBLE_THRESHOLD = 5;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount;
    int Pagecount = 0;
    int totalItemCount = 0;
    private boolean userScrolled = false;
    private int mTotalScrolled = 0;
    private LinearLayoutManager mLayoutManager;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            thisActivity = (Activity) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.food_category));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragcategory, container, false);

        thisActivity = getActivity();

        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.app_name));
        TextView titleTextView = null;
        try {
            Field f = ((ActionBarTitleSetter) thisActivity).getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(((ActionBarTitleSetter) thisActivity));
            Typeface font = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_robotolight_0));
            titleTextView.setTypeface(font);
            titleTextView.setTextSize(20);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        try {

            try {
                FirebaseMessaging.getInstance().subscribeToTopic(CV.TOPIC_GLOBAL);
            } catch (Exception e) {
                e.getMessage();
            }
            // checkPlayServices();

            CM.setSp(thisActivity, "regId", FirebaseInstanceId.getInstance().getToken().toString());
            Log.e("FCM_ID", FirebaseInstanceId.getInstance().getToken().toString());
        } catch (Exception e) {
            e.getMessage();
        }

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(thisActivity);


//        OkHttpClient client = new OkHttpClient();
//        client.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
//
////setup cache
//        File httpCacheDirectory = new File(thisActivity.getCacheDir(), "responses");
//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        Cache cache = new Cache(httpCacheDirectory, cacheSize);
//
//        //add cache to the client
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();


        setHasOptionsMenu(false);
        init(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        thisActivity.registerReceiver(mMessageReceiver, new IntentFilter("Customer"));
        mListener.showDrawerToggle(true);
        try {
            ArrayList<NotiModel> notiModels = tbl_notification.getAllData();
            if (notiModels != null) {
                if (notiModels.size() >= 1) {
                    int cou = Integer.parseInt(String.valueOf(notiModels.size()));
                    count = 0;
                    for (int i = 0; i < cou; i++) {
                        doIncrease();
                    }
                }

            } else {
                count = 0;
                thisActivity.invalidateOptionsMenu();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public void init(View rootView) {


        linearLayoutCat1 = (LinearLayout) rootView.findViewById(R.id.cat1LinearLayout);
        linearLayoutCat2 = (LinearLayout) rootView.findViewById(R.id.cat2LinearLayout);
        linearLayoutCat3 = (LinearLayout) rootView.findViewById(R.id.cat3LinearLayout);
        linearLayoutCat4 = (LinearLayout) rootView.findViewById(R.id.cat4LinearLayout);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progrees);
        progressBar1 = (ProgressBar) rootView.findViewById(R.id.progrees1);


        textViewCatOne = (MtplTextView) rootView.findViewById(R.id.catOneTextView);
        textViewCatOne.setSelected(true);
        textViewCatTwo = (MtplTextView) rootView.findViewById(R.id.catTwoTextView);
        textViewCatTwo.setSelected(true);
        textViewCatThree = (MtplTextView) rootView.findViewById(R.id.catThreeTextView);
        textViewCatThree.setSelected(true);
        textViewCatFour = (MtplTextView) rootView.findViewById(R.id.catFourTextView);
        textViewCatFour.setSelected(true);

        imageViewCatone = (ImageView) rootView.findViewById(R.id.catOneImageView);
        imageViewCatTwo = (ImageView) rootView.findViewById(R.id.catTwoImageView);
        imageViewCatThree = (ImageView) rootView.findViewById(R.id.catThreeImagetView);
        imageViewCatFour = (ImageView) rootView.findViewById(R.id.catFourImagetView);

        // PackageManager p = thisActivity.getPackageManager();
        //getAppLabel(p, "");

        imageView = (ImageView) rootView.findViewById(R.id.placeholder);
        imageView1 = (ImageView) rootView.findViewById(R.id.placeholder1);

        rootLayout = (LinearLayout) rootView.findViewById(R.id.mainCAT);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(thisActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        noInternetLayout = (LinearLayout) rootView.findViewById(R.id.noInternet);
        internetLayout = (LinearLayout) rootView.findViewById(R.id.Internet);
        linearLayoutCat1.setOnClickListener(this);
        linearLayoutCat2.setOnClickListener(this);
        linearLayoutCat3.setOnClickListener(this);
        linearLayoutCat4.setOnClickListener(this);

        categoryModelArraysMain = new ArrayList<>();
        mAdapter = new adptCategory(thisActivity, categoryModelArraysMain);
        mRecyclerView.setAdapter(mAdapter);

        if (CM.isInternetAvailable(getActivity())) {
            webCallCompanyPolicy();
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            internetLayout.setVisibility(View.GONE);
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mTotalScrolled += dy;
                if (userScrolled) {
                    totalItemCount = mLayoutManager.getItemCount();
                    visibleItemCount = mLayoutManager.getChildCount();
                    firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                        // End has been reached
                        Pagecount++;
                        webCallFoodCategory(Pagecount);

                        loading = true;
                        userScrolled = false;
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }

            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.foodcat, menu);
        if (menu.findItem(R.id.profile) != null) {
            menu.findItem(R.id.profile).setVisible(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notiCount:
                FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                FragNotification fragNotification = new FragNotification();
                t.setCustomAnimations(0, R.anim.fadeout);
                t.replace(R.id.container, fragNotification).addToBackStack("FragFoodCategory");
                t.commit();
                return true;
            case R.id.action_search:
                break;

        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.cat1LinearLayout:
                if (categoryModelArrays != null && categoryModelArrays.size() > 0) {
                    FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                    FragFoodItem categoryListing = new FragFoodItem();
                    bundle.putString("itemId", categoryModelArrays.get(0).nProductCategoryID.toString());
                    categoryListing.setArguments(bundle);
                    t.setCustomAnimations(0, R.anim.fadeout);
                    t.add(R.id.container, categoryListing).addToBackStack("FragFoodCategory");
                    t.commit();
                }
                break;
            case R.id.cat2LinearLayout:

                try {
                    if (categoryModelArrays != null && categoryModelArrays.size() > 0) {
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        FragFoodItem fragFoodItem = new FragFoodItem();
                        bundle.putString("itemId", categoryModelArrays.get(1).nProductCategoryID.toString());
                        fragFoodItem.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(0, R.anim.fadeout);
                        fragmentTransaction.add(R.id.container, fragFoodItem).addToBackStack("FragFoodCategory");
                        fragmentTransaction.commit();
                    }
                } catch (Exception e) {

                }
                break;
            case R.id.cat3LinearLayout:

                try {
                    if (categoryModelArrays != null && categoryModelArrays.size() > 0) {
                        FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                        FragFoodItem fragFoodItem1 = new FragFoodItem();
                        bundle.putString("itemId", categoryModelArrays.get(2).nProductCategoryID.toString());
                        fragFoodItem1.setArguments(bundle);
                        fragmentTransaction1.setCustomAnimations(0, R.anim.fadeout);
                        fragmentTransaction1.add(R.id.container, fragFoodItem1).addToBackStack("FragFoodCategory");
                        fragmentTransaction1.commit();
                    }
                } catch (Exception e) {

                }
                break;
            case R.id.cat4LinearLayout:

                try {
                    if (categoryModelArrays != null && categoryModelArrays.size() > 0) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        FragFoodItem fragFoodItem2 = new FragFoodItem();
                        bundle.putString("itemId", categoryModelArrays.get(4).nProductCategoryID.toString());
                        fragFoodItem2.setArguments(bundle);
                        transaction.setCustomAnimations(0, R.anim.fadeout);
                        transaction.add(R.id.container, fragFoodItem2).addToBackStack("FragFoodCategory");
                        transaction.commit();
                    }
                } catch (Exception e) {

                }

        }
    }

    private void showPopUp2(String msg) {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(thisActivity);
        helpBuilder.setTitle(getString(R.string.orderForTime));
        helpBuilder.setMessage(msg);
        View child = thisActivity.getLayoutInflater().inflate(R.layout.popuplout, null);
        helpBuilder.setView(child);
        editTextDate = (EditText) child.findViewById(R.id.edtDate);

        if (!CM.getSp(thisActivity, "serverDate", "").toString().equals("")) {
            editTextDate.setText(CM.getSp(thisActivity, "serverDate", "").toString());
        } else {

        }
        editTextTime = (Spinner) child.findViewById(R.id.spinnerTime);
        editTextDate.setOnTouchListener(this);
        helpBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        webCallFoodCategory(Pagecount);
                    }
                });
        helpBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        try {
            showdate(model_main.tOpeningTime, model_main.tClosingTime, model_main.tDisplayHoursInterval);
        } catch (Exception e) {
            e.getMessage();
        }
        if (times != null && times.size() > 0) {
            CustomSpinnerAdapter mySpinnerArrayAdapter = new CustomSpinnerAdapter(thisActivity, times);
            editTextTime.setAdapter(mySpinnerArrayAdapter);
        }
        helpDialog = helpBuilder.create();
        helpDialog.show();

        helpDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextDate.getText().toString().equals("")) {
                    helpDialog.dismiss();
                    String strings = (String) editTextTime.getSelectedItem();
                    CM.setSp(thisActivity, "serverTime", strings);
                    CM.setSp(thisActivity, "serverDate", editTextDate.getText().toString());

                    webCallFoodCategory(Pagecount);
                } else {
                    CM.showToast(thisActivity, getString(R.string.validateCate));
                }
            }
        });
        editTextTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String strings = (String) adapterView.getSelectedItem();
                // CM.setSp(thisActivity, "time", strings);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        switch (view.getId()) {
            case R.id.edtDate:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (editTextDate.getRight() - editTextDate.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        Calendar now = Calendar.getInstance();

                        if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = format.parse(CM.getSp(thisActivity, "serverDate", "").toString());
                                now.setTime(date);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }

                        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                        try {
                            Calendar now1 = Calendar.getInstance();
                            int day = now1.get(Calendar.DATE);
                            int days = now1.getActualMaximum(Calendar.DAY_OF_MONTH);
                            ArrayList<Calendar> calendars = new ArrayList<>();


                            int month = now1.get(Calendar.MONTH) + 1;
                            String yearMonth = now1.get(Calendar.YEAR) + "/" + month + "/";
                            for (int i = day + 3; i <= days; i++) {
                                calendars.add(DateToCalendar(new Date(yearMonth + i)));
                            }


                            Calendar tArray[] = calendars.toArray(new Calendar[calendars.size()]);
                            dpd.setDisabledDays(tArray);
                            dpd.setMinDate(DateToCalendar(new Date(System.currentTimeMillis() - 1000)));
                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


                            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);
                            // 2016-12-31
                            if ((day == (monthEnd.getDayOfMonth() - 2)) || (day == (monthEnd.getDayOfMonth() - 1)) || (day == (monthEnd.getDayOfMonth()))) {

                            } else {
                                Calendar c = Calendar.getInstance();
                                c.set(monthEnd.getYear(), monthEnd.getMonthOfYear() - 1, monthEnd.getDayOfMonth());//Year,Mounth -1,Day
                                dpd.setMaxDate(c);
                            }

                        } catch (Exception e) {
                            e.getMessage();

                        }
                        return true;
                    }
                }
                break;
            case R.id.edtDatePopup:

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (editTextDatePopup.getRight() - editTextDatePopup.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Calendar now = Calendar.getInstance();
                        if (!CM.getSp(thisActivity, "serverDate", "").equals("")) {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = format.parse(CM.getSp(thisActivity, "serverDate", "").toString());
                                now.setTime(date);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }

                        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                        try {
                            Calendar now1 = Calendar.getInstance();
                            int day = now1.get(Calendar.DATE);
                            int days = now1.getActualMaximum(Calendar.DAY_OF_MONTH);
                            ArrayList<Calendar> calendars = new ArrayList<>();


                            int month = now1.get(Calendar.MONTH) + 1;
                            String yearMonth = now1.get(Calendar.YEAR) + "/" + month + "/";
                            for (int i = day + 3; i <= days; i++) {
                                calendars.add(DateToCalendar(new Date(yearMonth + i)));
                            }


                            Calendar tArray[] = calendars.toArray(new Calendar[calendars.size()]);
                            dpd.setDisabledDays(tArray);
                            dpd.setMinDate(DateToCalendar(new Date(System.currentTimeMillis() - 1000)));
                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");


                            LocalDate monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1);
                            // 2016-12-31
                            if ((day == (monthEnd.getDayOfMonth() - 2)) || (day == (monthEnd.getDayOfMonth() - 1)) || (day == (monthEnd.getDayOfMonth()))) {

                            } else {
                                Calendar c = Calendar.getInstance();
                                c.set(monthEnd.getYear(), monthEnd.getMonthOfYear() - 1, monthEnd.getDayOfMonth());//Year,Mounth -1,Day
                                dpd.setMaxDate(c);
                            }

                        } catch (Exception e) {
                            e.getMessage();

                        }
                        return true;
                    }
                }
                break;


        }


        return false;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;

        if (editTextDate != null) {
            editTextDate.setText(dayOfMonth + "-" + month + "-" + year);
        }


        String currentDate = "";
        Date cuurentTime;
        String formattedDate = "";
        DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
        DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
        DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = Calendar.getInstance().getTime();
        currentDate = CM.converDateFormate("EEE MMM dd HH:mm:ss Z yyyy", "yyyy-MM-dd", date.toString());
        Date time = new Date();
        try {
            cuurentTime = readFormat.parse(dateFormat.format(time));
            if (cuurentTime != null) {
                formattedDate = writeFormat.format(cuurentTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        String orderDate = "";
        //if (!CM.getSp(thisActivity, "serverDate", "").toString().equals("")) {
        orderDate = CM.converDateFormate("dd-MM-yyyy", "yyyy-MM-dd", dayOfMonth + "-" + month + "-" + year); // CM.converDateFormate("yyyy-MM-dd", "yyyy-MM-dd", CM.getSp(thisActivity, "serverDate", "").toString());
//        } else {
//            orderDate = currentDate;
//        }
        webCallStoreTime(orderDate + " " + timeStamp, currentDate + " " + formattedDate);
        CM.setSp(thisActivity, "serverDate", dayOfMonth + "-" + month + "-" + year);

    }


    public int getDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }


    public void webCallStoreTime(String currentDate, String orderDateTime) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, false, false);
            WebService.getResponseForSetTime(v, currentDate, orderDateTime, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForStoreTime(response);

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

    private void getResponseForStoreTime(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("ResponseCode").equals("202")) {
                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("ResponseObject"));
                if (jsonObject1.has("lblStoreClosed")) {
                    if (helpDialog != null) {
                        helpDialog.dismiss();
                    }
                    showPopUp2(getString(R.string.content));
                } else {
                    model_main = CM.JsonParse(new TimeModel(), jsonObject.getString("ResponseObject"));
                    CM.setSp(thisActivity, "openingTime", model_main.tOpeningTime);
                    CM.setSp(thisActivity, "closingTime", model_main.tClosingTime);
                    CM.setSp(thisActivity, "timeInterval", model_main.tDisplayHoursInterval);
                    if (model_main.isPopupOpen.equals("false")) {
                        if (helpDialog != null) {
                            helpDialog.dismiss();
                        }

//                        if (!CM.getSp(thisActivity, "serverDate", "").toString().equals("") && !CM.getSp(thisActivity, "serverTime", "").toString().equals("")) {
//                            try {
//                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm aaa");
//                                Date orderTime;
//                                try {
//                                    String orderDate = CM.converDateFormate("dd-MM-yyyy", "yyyy/MM/dd", CM.getSp(thisActivity, "serverDate", "").toString());
//
//                                    String str1 = "2017/01/10 12:34 PM";
//                                    Date curentDateTime = formatter.parse(getDateTime());  //getDateTime()
//                                    String str2 = "2017/01/10 01:17 PM";
//                                    Date orderDateTime = formatter.parse(orderDate + " " + CM.getSp(thisActivity, "serverTime", "").toString());  //orderDate + " " + CM.getSp(thisActivity, "serverTime", "").toString()
//
//
//                                    if (curentDateTime.after(orderDateTime)) {
//                                        System.out.println("orderDateTime is Greater than my curentDateTime");
//                                        CM.showToast(thisActivity, "currentDateTimeis after orderDateTime");
//                                        showPopUp2(getString(R.string.content1));
//                                    } else if (curentDateTime.before(orderDateTime)) {
//                                        System.out.println("currentDateTime is before orderDateTime");
//                                    } else if (curentDateTime.equals(orderDateTime)) {
//                                        System.out.println("currentDateTime  is equal orderDateTime");
//                                    }
//                                } catch (Exception e) {
//                                    e.getMessage();
//
//                                }
//                            } catch (Exception e) {
//                                e.getMessage();
//                            }
//                        } else {
//                            showPopUp2(getString(R.string.content1));
//                        }
                        webCallFoodCategory(Pagecount);
                    } else if (model_main.isPopupOpen.equals("true")) {
                        if (helpDialog != null) {
                            helpDialog.dismiss();
                        }
                        showPopUp2(getString(R.string.content));
                    } else {
                        webCallFoodCategory(Pagecount);
                    }
                }


            } else {

            }

        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }


    public void webCallFoodCategory(int pagecount) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, false, false);
            WebService.getResendForCategories(v, String.valueOf(pagecount), new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseFoodCategory(response);

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


    private void getResponseFoodCategory(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("ResponseCode") != null && jsonObject.getString("ResponseCode").toString().equals("202")) {


                CategoryModel model_main = CM.JsonParse(new CategoryModel(), jsonObject.getString("ResponseObject"));
                if (categoryModelArrays != null) {
                    categoryModelArrays.clear();
                }
                categoryModelArrays = model_main.categoryModels;
                for (int i = 0; i < categoryModelArrays.size(); i++) {
                    CategoryModelArray categoryModelArray = new CategoryModelArray();
                    categoryModelArray.cCategoryName = categoryModelArrays.get(i).cCategoryName;
                    categoryModelArray.bIsActive = categoryModelArrays.get(i).bIsActive;
                    categoryModelArray.cProductCatImagePath = categoryModelArrays.get(i).cProductCatImagePath;
                    categoryModelArray.nProductCategoryID = categoryModelArrays.get(i).nProductCategoryID;
                    categoryModelArraysMain.add(categoryModelArray);
                }

                try {
                    if (categoryModelArrays != null && categoryModelArrays.size() > 0) {
                        mAdapter.update(categoryModelArraysMain);
                    }

                } catch (Exception e) {
                    e.getMessage();
                }


                try {
                    textViewCatOne.setText(categoryModelArraysMain.get(0).cCategoryName);
                    textViewCatTwo.setText(categoryModelArraysMain.get(1).cCategoryName);

                } catch (Exception e) {
                    e.getMessage();
                }

                mAdapter.SetOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(String value) {
                        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                        FragFoodItem categoryListing = new FragFoodItem();
                        Bundle bundle = new Bundle();
                        bundle.putString("itemId", value);
                        t.setCustomAnimations(0, R.anim.fadeout);
                        categoryListing.setArguments(bundle);
                        t.add(R.id.container, categoryListing).addToBackStack("FragFoodCategory");
                        t.commit();
                    }
                });

                Glide.with(this)
                        .load(categoryModelArraysMain.get(1).cProductCatImagePathR)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                imageView.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                imageView.setVisibility(View.GONE);
                                return false;
                            }
                        }).error(R.drawable.circular)
                        .into(imageViewCatTwo);

            }


        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }

    public static Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


    public void showdate(String tOpeningTime, String tClosingTime, String intervel) {
        // Date time = null;
//        Date time1 = null;
        DateTimeFormatter FORMAT = DateTimeFormat.forPattern("hh:mm aa");
//        ArrayList<String> strings = new ArrayList<>();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
//        try {
//            time = dateFormat.parse(tOpeningTime);
//            time1 = dateFormat.parse(tClosingTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        times = new ArrayList<String>();
        String hour = intervel.substring(0, 2);
        String min = intervel.length() > 2 ? intervel.substring(intervel.length() - 2) : intervel;
        Duration duration = null;
        try {
            if (hour != null && !hour.equals("00")) {
                duration = Hours.hours(Integer.parseInt(hour)).toStandardDuration();
            } else if (min != null && !min.equals("00")) {
                duration = Minutes.minutes(Integer.parseInt(min)).toStandardDuration();
            } else {
                duration = Minutes.minutes(Integer.parseInt(min)).toStandardDuration();
            }
        } catch (Exception e) {

        }
        final Set<DateTime> set = new LinkedHashSet<DateTime>();
        DateTime d = new DateTime(CM.dateFormat(tOpeningTime));
        DateTime e = new DateTime(CM.dateFormat(tClosingTime));
        do {
            set.add(d);
            d = d.plus(duration);
        } while (d.compareTo(e) <= 0);
        for (final DateTime dt : set) {
            System.out.println(FORMAT.print(dt));
            //strings.add(FORMAT.print(dt));
            times.add(FORMAT.print(dt));
        }
        times.size();

    }


    @Override
    public void onPause() {
        super.onPause();
        thisActivity.unregisterReceiver(mMessageReceiver);
    }

    private void doIncrease() {
        count++;
        thisActivity.invalidateOptionsMenu();
    }

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("message");

            try {
                ArrayList<NotiModel> notiModels = tbl_notification.getAllData();
                if (notiModels != null) {
                    if (notiModels.size() >= 1) {
                        int cou = Integer.parseInt(String.valueOf(notiModels.size()));
                        count = 0;
                        for (int i = 0; i < cou; i++) {
                            doIncrease();
                        }
                    }
                } else {
                    count = 0;
                    thisActivity.invalidateOptionsMenu();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    };

    public void webCallCompanyPolicy() {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, false, false);
            WebService.getResendForConpanyDate(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCompanyPolicy(response);

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


    private void getResponseForCompanyPolicy(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("ResponseCode") != null && jsonObject.getString("ResponseCode").toString().equals("202")) {
                CompanyModel model_main = CM.JsonParse(new CompanyModel(), jsonObject.getString("ResponseObject"));
                companyModelArrays = model_main.companyModelArrays;
                if (companyModelArrays != null && companyModelArrays.size() > 0) {

                    if (companyModelArrays.get(0).cCurrencyCode != null) {
                        CM.setSp(thisActivity, "currency", Util.getCurrencySymbol(companyModelArrays.get(0).cCurrencyCode.toString()));

                    }
                    if (companyModelArrays.get(0).nMaxOrderQty != null) {
                        CM.setSp(thisActivity, "Quant", companyModelArrays.get(0).nMaxOrderQty.toString());
                    } else {
                        CM.setSp(thisActivity, "Quant", "10");
                    }
                    if (companyModelArrays.get(0).bImagesVisible != null) {
                        if (companyModelArrays.get(0).bImagesVisible.toString().equals("false")) {
                            CM.setSp(thisActivity, "isImage", "1");
                        } else {
                            CM.setSp(thisActivity, "isImage", "0");
                        }
                    } else {
                        CM.setSp(thisActivity, "isImage", "0");
                    }
                    if (companyModelArrays.get(0).nMinOrderPlacingAmt != null) {
                        CM.setSp(thisActivity, "minOrder", companyModelArrays.get(0).nMinOrderPlacingAmt);
                    }

                    if (companyModelArrays.get(0).nMaxOrderPlacingAmt != null) {
                        CM.setSp(thisActivity, "maxOrder", companyModelArrays.get(0).nMaxOrderPlacingAmt);
                    }


                    if (companyModelArrays.get(0).bIsDiscountAfterTax != null) {
                        CM.setSp(thisActivity, "bIsDiscountAfterTax", companyModelArrays.get(0).bIsDiscountAfterTax.toString());
                    }

                    if (companyModelArrays.get(0).nOrderFillMode != null) {
                        CM.setSp(thisActivity, "payMode", companyModelArrays.get(0).nOrderFillMode.toString());
                    }


                    if (companyModelArrays.get(0).nMaxOrderAmtForWarning != null) {
                        CM.setSp(thisActivity, "maxOrderWar", companyModelArrays.get(0).nMaxOrderAmtForWarning.toString());
                    }
                    if (companyModelArrays.get(0).cMaxOrderAmtForWarningMsg != null) {
                        CM.setSp(thisActivity, "maxOrderWarMsg", companyModelArrays.get(0).cMaxOrderAmtForWarningMsg.toString());
                    }
                    if (companyModelArrays.get(0).nOrderFillModeDefault != null) {
                        CM.setSp(thisActivity, "DefPmode", companyModelArrays.get(0).nOrderFillModeDefault.toString());
                    }

                    DateFormat readFormat = new SimpleDateFormat("hh:mm aaa");
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                    DateFormat writeFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = Calendar.getInstance().getTime();
                    Date time = new Date();
                    Date orderTime = null, cuurentTime = null;
                    String currentDate = "";
                    String formattedDate = "";
                    try {
                        cuurentTime = readFormat.parse(dateFormat.format(time));
                        if (cuurentTime != null) {
                            formattedDate = writeFormat.format(cuurentTime);
                        }
                        currentDate = CM.converDateFormate("EEE MMM dd HH:mm:ss Z yyyy", "yyyy-MM-dd", date.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }
                    String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

                    String orderDate = "";

                    if (!CM.getSp(thisActivity, "serverDate", "").toString().equals("")) {
                        // CM.setSp(thisActivity, "serverDate", dayOfMonth + "-" + month + "-" + year);
                        orderDate = CM.converDateFormate("dd-MM-yyyy", "yyyy-MM-dd", CM.getSp(thisActivity, "serverDate", "").toString());
                    } else {
                        orderDate = currentDate;
                    }
                    webCallStoreTime(orderDate + " " + timeStamp, currentDate + " " + formattedDate);


                    noInternetLayout.setVisibility(View.GONE);
                    internetLayout.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm aaa");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getAppLabel(PackageManager pm, String pathToApk) {
        String root = Environment.getExternalStorageDirectory().toString();
        File FilePathDir = new File(root, "BACKUP/APPS");
        File file = new File(FilePathDir, "Browser.apk");

        PackageInfo packageInfo = pm.getPackageArchiveInfo(file.getAbsolutePath(), 0);

        if (Build.VERSION.SDK_INT >= 8) {
            // those two lines do the magic:
            packageInfo.applicationInfo.sourceDir = pathToApk;
            packageInfo.applicationInfo.publicSourceDir = pathToApk;

        }

        CharSequence label = pm.getApplicationLabel(packageInfo.applicationInfo);


        Drawable icon = null;
        try {
            icon = thisActivity.getPackageManager().getApplicationIcon(packageInfo.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        imageViewCatone.setImageDrawable(icon);

        return label != null ? label.toString() : null;
    }

//    void showDialog() {
//
//        String path = Environment.getExternalStorageDirectory().toString() + "/AyalaPDF";
//        Log.d("Files", "Path: " + path);
//        File directory = new File(path);
//        File[] files = directory.listFiles();
//        Log.d("Files", "Size: " + files.length);
//        ArrayList<String> dialog_item = new ArrayList<>();
//        for (int i = 0; i < files.length; i++) {
//            Log.d("Files", "FileName:" + files[i].getName());
//            dialog_item.add(files[i].getName());
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);
//        builder.setTitle("Select Files");
//
//        //list of items
//        final String[] items = dialog_item.toArray(new String[dialog_item.size()]);
//        builder.setSingleChoiceItems(items, 0,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // item selected logic
//                        String name = items[which];
//                        CM.showToast(thisActivity, name);
//                    }
//                });
//
//        String positiveText = getString(android.R.string.ok);
//        builder.setPositiveButton(positiveText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // positive button logic
//                    }
//                });
//
//        String negativeText = getString(android.R.string.cancel);
//        builder.setNegativeButton(negativeText,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // negative button logic
//                    }
//                });
//
//        AlertDialog dialog = builder.create();
//        // display dialog
//        dialog.show();
//    }


    private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (CM.isInternetAvailable(thisActivity)) {
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };


}