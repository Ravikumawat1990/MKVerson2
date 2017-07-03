package com.app.elixir.makkhankitchen.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.CardDateArray;
import com.app.elixir.makkhankitchen.Model.CartDataModel;
import com.app.elixir.makkhankitchen.Model.CategoryItemModel;
import com.app.elixir.makkhankitchen.Model.CategoryItemModelArray;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.activity.ViewFoodItemDetail;
import com.app.elixir.makkhankitchen.activity.ViewOrderSummary;
import com.app.elixir.makkhankitchen.adapter.CustomGridViewAdapter;
import com.app.elixir.makkhankitchen.adapter.DataAdapter;
import com.app.elixir.makkhankitchen.adapter.adptCategoryGrid;
import com.app.elixir.makkhankitchen.adapter.adptCategoryListing;
import com.app.elixir.makkhankitchen.adapter.adptCustomGrid;
import com.app.elixir.makkhankitchen.interfac.ActionBarTitleSetter;
import com.app.elixir.makkhankitchen.interfac.OnFragmentInteractionListener;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListenerFoodItem;
import com.app.elixir.makkhankitchen.interfac.OnTaskCompleted;
import com.app.elixir.makkhankitchen.mtplview.MtplLog;
import com.app.elixir.makkhankitchen.pojo.PojoCategoryListing;
import com.app.elixir.makkhankitchen.utils.BadgeDrawable;
import com.app.elixir.makkhankitchen.utils.CM;
import com.app.elixir.makkhankitchen.volly.OnVolleyHandler;
import com.app.elixir.makkhankitchen.volly.VolleyIntialization;
import com.app.elixir.makkhankitchen.volly.WebService;
import com.app.elixir.makkhankitchen.volly.WebServiceTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Elixir on 09-Aug-2016.
 */
public class FragFoodItem extends Fragment implements SearchView.OnQueryTextListener, OnTaskCompleted {

    private static final String TAG = "FragFoodItem";
    private static final int VISIBLE_THRESHOLD = 5;
    Activity thisActivity;
    private OnFragmentInteractionListener mListener;
    ArrayList<PojoCategoryListing> arrayList;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private adptCategoryListing mAdapter;


    protected Handler handler;
    DataAdapter dataAdapter;
    public static GridView grid;
    private CardView cardView;
    adptCustomGrid adptCustomGrid;
    public static boolean cuurentView = false;
    private ArrayList<CategoryItemModelArray> categoryModelArrays;
    private LinearLayout rootLayout;
    private LinearLayout noInternetLayout;
    private LinearLayout internetLayout;
    private ArrayList<CardDateArray> cartDeatilArray;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<PojoCategoryListing> pojoCategoryListings;
    public static CustomGridViewAdapter customGridViewAdapter;
    private boolean userScrolled = false;
    private int mTotalScrolled = 0;


    private int mVisibleThreshold = 5;
    private int mCurrentPage = 0;
    private int mPreviousTotal = 0;
    private boolean mLoading = true;
    private boolean mLastPage = false;
    private GridLayoutManager lLayout;
    private RecyclerView mRecyclerViewGrid;


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            thisActivity = (Activity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    private int count = 0;

    String catId;

    private static int MAX_ITEMS_PER_REQUEST = 10;
    private static final int NUMBER_OF_ITEMS = 100;
    private static final int SIMULATED_LOADING_TIME_IN_MS = 1500;

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount;
    int Pagecount = 0;
    int totalItemCount = 0;
    adptCategoryGrid adptCategoryGridl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.catlisting, container, false);
        thisActivity = getActivity();

        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.food_item));
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
        setHasOptionsMenu(true);
        catId = getArguments().getString("itemId");
        init(rootView);
        cuurentView = false;


        return rootView;
    }


    public void init(View rootView) {
        CM.setSp(thisActivity, "quantity", "1.0");
        CM.setSp(thisActivity, "itemPrice", "");
        arrayList = new ArrayList<>();
        cardView = (CardView) rootView.findViewById(R.id.cardviewcatlisting);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerViewGrid = (RecyclerView) rootView.findViewById(R.id.my_recycler_viewGrid);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(thisActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        grid = (GridView) rootView.findViewById(R.id.grid);

        lLayout = new GridLayoutManager(thisActivity, 2);
        adptCategoryGridl = new adptCategoryGrid(thisActivity, arrayList);
        mRecyclerViewGrid.setLayoutManager(lLayout);

        rootLayout = (LinearLayout) rootView.findViewById(R.id.mainListing);
        noInternetLayout = (LinearLayout) rootView.findViewById(R.id.noInternet);
        internetLayout = (LinearLayout) rootView.findViewById(R.id.Internet);
        mAdapter = new adptCategoryListing(thisActivity, arrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerViewGrid.setAdapter(adptCategoryGridl);

        if (CM.isInternetAvailable(getActivity())) {

            webCallCartDat(CM.getSp(thisActivity, "customerId", "").toString());
            webCallFoodCategory(catId, Pagecount);
            noInternetLayout.setVisibility(View.GONE);
            internetLayout.setVisibility(View.VISIBLE);
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            internetLayout.setVisibility(View.GONE);
        }


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // mTotalScrolled += dy;
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

                        webCallFoodCategory(catId, Pagecount);
                        //mRecyclerView.smoothScrollToPosition(mTotalScrolled);

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


        mRecyclerViewGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // mTotalScrolled += dy;
                if (userScrolled) {
                    totalItemCount = lLayout.getItemCount();
                    visibleItemCount = lLayout.getChildCount();
                    firstVisibleItem = lLayout.findFirstVisibleItemPosition();

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

                        webCallFoodCategory(catId, Pagecount);
                        //mRecyclerView.smoothScrollToPosition(mTotalScrolled);

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
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);
        webCallCartDat(CM.getSp(thisActivity, "customerId", "").toString());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.cartlisting, menu);
        MenuItem menuItem = menu.findItem(R.id.cartMenu);
        if (menu.findItem(R.id.notiCount) != null) {
            menu.findItem(R.id.notiCount).setVisible(false);
        }
        if (menu.findItem(R.id.profile) != null) {
            menu.findItem(R.id.profile).setVisible(false);
        }
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion == 15) {
            LayerDrawable icon = null;
            try {
                menuItem.setIcon(thisActivity.getResources().getDrawable(R.drawable.ic_add_shopping_cart_white_24dp));
                BitmapDrawable iconBitmap = (BitmapDrawable) menuItem.getIcon();
                icon = new LayerDrawable(new Drawable[]{iconBitmap});
            } catch (Exception e) {
                e.getMessage();
            }
            setBadgeCount(thisActivity, icon, String.valueOf(count));
        } else {
            LayerDrawable icon = null;
            try {
                icon = (LayerDrawable) menuItem.getIcon();
            } catch (Exception e) {
                e.getMessage();
            }
            setBadgeCount(thisActivity, icon, String.valueOf(count));
        }
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);


    }


    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartMenu:
                if (cartDeatilArray != null) {
                    if (cartDeatilArray.size() >= 1) {
                        if (cartDeatilArray.get(0).nCartID == null) {

                        } else {
                            Intent intent = new Intent(thisActivity, ViewOrderSummary.class);
                            intent.putExtra("cartId", String.valueOf(cartDeatilArray.get(0).nCartID));
                            CM.startActivity(intent, thisActivity);
                        }
                    }
                }
                return true;
            case R.id.toogelList:
                if (cuurentView) {
                    item.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_view_module_white_24dp, null));
                    mRecyclerViewGrid.setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);
                    cuurentView = false;
                } else {
                    item.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_view_list_white_24dp, null));
                    mRecyclerViewGrid.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);
                    cuurentView = true;
                }
                return true;

        }
        return false;
    }

    private void doIncrease() {
        count++;
        thisActivity.invalidateOptionsMenu();
    }

    private void dodIncrease() {
        count--;
        thisActivity.invalidateOptionsMenu();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111) {
            if (resultCode == Activity.RESULT_OK) {
                webCallCartDat(CM.getSp(thisActivity, "customerId", "").toString());
            }
        }
    }

    public void webCallCartDat(String cutomerId) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.GETCARTDATA(v, cutomerId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCartDat(response);

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

    private void getResponseForCartDat(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("ResponseCode").equals("202")) {
                try {
                    // CM.setSp(getActivity(), "cartId", "0");
                    CartDataModel model_main = CM.JsonParse(new CartDataModel(), jsonObject.getString("ResponseObject"));
                    cartDeatilArray = model_main.cardDateArrays;
                    if (cartDeatilArray.size() >= 1) {
                        CM.setSp(thisActivity, "cartId", cartDeatilArray.get(0).nCartID);
                        int count1 = Integer.parseInt(cartDeatilArray.get(0).nCartItemCount.toString());
                        count = 0;
                        for (int i = 0; i < count1; i++) {
                            doIncrease();
                        }

                        if (count1 == 0) {
                            if (cartDeatilArray.get(0).nCartID == null) {
                                CM.setSp(thisActivity, "cartId", "0");
                                thisActivity.invalidateOptionsMenu();
                            } else {
                                //           CM.setSp(thisActivity, "cartId", "0");
                                thisActivity.invalidateOptionsMenu();
                            }
                        }
                    } else {
                        if (cartDeatilArray.size() == 0) {
                            try {
                                count = 0;
                                CM.setSp(thisActivity, "cartId", "0");
                                thisActivity.invalidateOptionsMenu();
                            } catch (Exception e) {
                                Log.d(TAG, "getResponseForCartDat: " + e.getMessage());
                            }
                        }

                    }

                } catch (Exception e) {
                    Log.d(TAG, "getResponseForCartDat: " + e.getMessage());

                }
            }

        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }

    public void webCallFoodCategory(String id, int index) {
        try {

            VolleyIntialization v = new VolleyIntialization(this.thisActivity, true, true);
            WebService.getResendForCategoryItem(v, id, String.valueOf(index), new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (FragFoodItem.this.thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);

                    getResponseForCouponCode(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(FragFoodItem.this.thisActivity)) {
                        CM.showPopupCommonValidation(FragFoodItem.this.thisActivity, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getResponseForCouponCode(String response) {


        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(this.thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);


            if (jsonObject.getString("ResponseCode") != null && jsonObject.getString("ResponseCode").equals("202")) {
                CategoryItemModel model_main = CM.JsonParse(new CategoryItemModel(), jsonObject.getString("ResponseObject"));

                if (categoryModelArrays != null) {
                    categoryModelArrays.clear();
                }
                categoryModelArrays = model_main.categoryItemModelArrays;
                categoryModelArrays.size();
                if (categoryModelArrays != null && categoryModelArrays.size() > 0) {
                    for (int i = 0; i < categoryModelArrays.size(); i++) {
                        if (categoryModelArrays.get(i).bIsActive != null) {
                            if (categoryModelArrays.get(i).bIsActive.equals("true")) {
                                PojoCategoryListing pojoCategoryListing = new PojoCategoryListing();
                                if (categoryModelArrays.get(i).cProductName != null) {
                                    pojoCategoryListing.setName(categoryModelArrays.get(i).cProductName);
                                } else {
                                    pojoCategoryListing.setName("");
                                }
                                if (categoryModelArrays.get(i).nRate != null) {
                                    pojoCategoryListing.setPrice(categoryModelArrays.get(i).nRate);
                                } else {
                                    pojoCategoryListing.setPrice("");
                                }
                                if (categoryModelArrays.get(i).nProductID != null) {
                                    pojoCategoryListing.setId(categoryModelArrays.get(i).nCompanyProductID);
                                } else {
                                    pojoCategoryListing.setId("");
                                }
                                if (categoryModelArrays.get(i).cProductImagePath != null) {
                                    pojoCategoryListing.setImagePath(categoryModelArrays.get(i).cProductImagePath);
                                } else {
                                    pojoCategoryListing.setImagePath("");
                                }
                                if (categoryModelArrays.get(i).bIsActive != null) {
                                    pojoCategoryListing.setIsActive(categoryModelArrays.get(i).bIsActive);
                                } else {
                                    pojoCategoryListing.setIsActive("");
                                }
                                arrayList.add(pojoCategoryListing);
                            }
                        }

                    }
                    mAdapter.update(arrayList);
                    if (adptCategoryGridl != null) {
                        adptCategoryGridl.update(arrayList);

                    }
                    // adptCustomGrid = new adptCustomGrid(thisActivity, arrayList);
                    //   grid.setAdapter(adptCustomGrid);

                    adptCategoryGridl.SetOnItemClickListener(new OnItemClickListenerFoodItem() {
                        @Override
                        public void onItemClick(String value, int pos) {
                            if (value.equals("detail")) {

                                Intent intent = new Intent(FragFoodItem.this.thisActivity, ViewFoodItemDetail.class);
                                intent.putExtra("comProId", arrayList.get(pos).getId().toString());

                                startActivityForResult(intent, 111);
                                getActivity().overridePendingTransition(R.anim.push_in_from_left, R.anim.push_out_to_right);

                            }
                        }
                    });

                    mAdapter.SetOnItemClickListener(new OnItemClickListenerFoodItem() {
                        @Override
                        public void onItemClick(String value, int pos) {
                            if (value.equals("detail")) {

                                Intent intent = new Intent(FragFoodItem.this.thisActivity, ViewFoodItemDetail.class);
                                intent.putExtra("comProId", arrayList.get(pos).getId().toString());

                                startActivityForResult(intent, 111);
                                getActivity().overridePendingTransition(R.anim.push_in_from_left, R.anim.push_out_to_right);

                            }
                        }
                    });


                }
            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(this.thisActivity, e.getMessage(), false);
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        //  mAdapter.filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        if (mAdapter != null) {
            mAdapter.filter(newText);

        }
        if (adptCategoryGridl != null) {
            adptCategoryGridl.filter(newText);
        }
        return false;
    }

    @Override
    public void onTaskCompleted(String response) {

    }
}
