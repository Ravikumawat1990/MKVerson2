package com.app.elixir.makkhankitchen.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.CategoryItemModel;
import com.app.elixir.makkhankitchen.Model.CategoryItemModelArray;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.activity.ViewFoodItemDetail;
import com.app.elixir.makkhankitchen.adapter.CustomGridViewAdapterFav;
import com.app.elixir.makkhankitchen.adapter.adptCategoryGrid;
import com.app.elixir.makkhankitchen.adapter.adptCategoryListingFav;
import com.app.elixir.makkhankitchen.interfac.ActionBarTitleSetter;
import com.app.elixir.makkhankitchen.interfac.OnFragmentInteractionListener;
import com.app.elixir.makkhankitchen.interfac.OnItemClickListenerFoodItem;
import com.app.elixir.makkhankitchen.interfac.OnTaskCompleted;
import com.app.elixir.makkhankitchen.mtplview.MtplLog;
import com.app.elixir.makkhankitchen.pojo.PojoCategoryListing;
import com.app.elixir.makkhankitchen.utils.CM;
import com.app.elixir.makkhankitchen.volly.OnVolleyHandler;
import com.app.elixir.makkhankitchen.volly.VolleyIntialization;
import com.app.elixir.makkhankitchen.volly.WebService;
import com.app.elixir.makkhankitchen.volly.WebServiceTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Elixir on 09-Aug-2016.
 */
public class FragFavarities extends Fragment implements SearchView.OnQueryTextListener, OnTaskCompleted {

    private static final String TAG = "FragFoodItem";
    Activity thisActivity;
    private OnFragmentInteractionListener mListener;
    ArrayList<PojoCategoryListing> arrayList;
    private RecyclerView mRecyclerView;
    private adptCategoryListingFav mAdapter;
    protected Handler handler;
    // public static GridView grid;
    private CardView cardView;

    public static boolean cuurentView = false;
    private ArrayList<CategoryItemModelArray> categoryModelArrays;
    private LinearLayout rootLayout;
    private LinearLayout noInternetLayout;
    private LinearLayout internetLayout;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<PojoCategoryListing> pojoCategoryListings;
    public CustomGridViewAdapterFav customGridViewAdapter;
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

    adptCategoryGrid adptCategoryGridl;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.catlisting, container, false);
        thisActivity = getActivity();
      //  ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.favorites));
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
        catId = "3";
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
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(thisActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerViewGrid = (RecyclerView) rootView.findViewById(R.id.my_recycler_viewGrid);
        lLayout = new GridLayoutManager(thisActivity, 2);
        adptCategoryGridl = new adptCategoryGrid(thisActivity, arrayList);
        mRecyclerViewGrid.setLayoutManager(lLayout);
        mRecyclerViewGrid.setAdapter(adptCategoryGridl);
        //grid = (GridView) rootView.findViewById(R.id.grid);
        rootLayout = (LinearLayout) rootView.findViewById(R.id.mainListing);
        noInternetLayout = (LinearLayout) rootView.findViewById(R.id.noInternet);
        internetLayout = (LinearLayout) rootView.findViewById(R.id.Internet);

        if (CM.isInternetAvailable(getActivity())) {
            webCallFav(CM.getSp(thisActivity, "customerId", "").toString());
            noInternetLayout.setVisibility(View.GONE);
            internetLayout.setVisibility(View.VISIBLE);
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            internetLayout.setVisibility(View.GONE);
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.cartlisting, menu);
        //MenuItem menuItem = menu.findItem(R.id.cartMenu);
        if (menu.findItem(R.id.notiCount) != null) {
            menu.findItem(R.id.notiCount).setVisible(false);
        }

        if (menu.findItem(R.id.cartMenu) != null) {
            menu.findItem(R.id.cartMenu).setVisible(false);
        }

        if (menu.findItem(R.id.profile) != null) {
            menu.findItem(R.id.profile).setVisible(false);
        }

        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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


    public void webCallFav(String cusId) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getResponseGetFav(v, cusId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForFav(response);

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


    private void getResponseForFav(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);


            if (jsonObject.getString("ResponseCode") != null && jsonObject.getString("ResponseCode").equals("202")) {
                CategoryItemModel model_main = CM.JsonParse(new CategoryItemModel(), jsonObject.getString("ResponseObject"));
                categoryModelArrays = model_main.categoryItemModelArrays;
                categoryModelArrays.size();
                Collections.reverse(categoryModelArrays);

                arrayList.clear();
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

                    Collections.reverse(arrayList);

                    mAdapter = new adptCategoryListingFav(thisActivity, arrayList);
                    mRecyclerView.setAdapter(mAdapter);
                    if (adptCategoryGridl != null) {
                        adptCategoryGridl.update(arrayList);

                    }


                    adptCategoryGridl.SetOnItemClickListener(new OnItemClickListenerFoodItem() {
                        @Override
                        public void onItemClick(String value, int pos) {
                            if (value.equals("detail")) {

                                Intent intent = new Intent(thisActivity, ViewFoodItemDetail.class);
                                intent.putExtra("comProId", arrayList.get(pos).getId().toString());

                                startActivityForResult(intent, 111);
                                getActivity().overridePendingTransition(R.anim.push_in_from_left, R.anim.push_out_to_right);

                            } else if (value.equals("delete")) {
                                showPopup(thisActivity, CM.getSp(thisActivity, "customerId", "").toString(), arrayList.get(pos).getId().toString());

                            }
                        }
                    });


                    mAdapter.SetOnItemClickListener(new OnItemClickListenerFoodItem() {
                        @Override
                        public void onItemClick(String value, int pos) {
                            if (value.equals("detail")) {
                                Intent intent = new Intent(thisActivity, ViewFoodItemDetail.class);
                                intent.putExtra("comProId", arrayList.get(pos).getId().toString());
                                startActivityForResult(intent, 111);
                                getActivity().overridePendingTransition(R.anim.push_in_from_left, R.anim.push_out_to_right);
                            } else if (value.equals("delete")) {
                                showPopup(thisActivity, CM.getSp(thisActivity, "customerId", "").toString(), arrayList.get(pos).getId().toString());
                            }

                        }
                    });


                } else {
                    mAdapter = new adptCategoryListingFav(thisActivity, arrayList);
                    mRecyclerView.setAdapter(mAdapter);


                    if (adptCategoryGridl != null) {
                        adptCategoryGridl.update(arrayList);

                    }


                    adptCategoryGridl.SetOnItemClickListener(new OnItemClickListenerFoodItem() {
                        @Override
                        public void onItemClick(String value, int pos) {
                            if (value.equals("detail")) {
                                Intent intent = new Intent(thisActivity, ViewFoodItemDetail.class);
                                intent.putExtra("comProId", arrayList.get(pos).getId().toString());
                                startActivityForResult(intent, 111);
                                getActivity().overridePendingTransition(R.anim.push_in_from_left, R.anim.push_out_to_right);
                            } else if (value.equals("delete")) {
                                showPopup(thisActivity, CM.getSp(thisActivity, "customerId", "").toString(), arrayList.get(pos).getId().toString());
                            }
                        }
                    });

                    mAdapter.SetOnItemClickListener(new OnItemClickListenerFoodItem() {
                        @Override
                        public void onItemClick(String value, int pos) {
                            if (value.equals("detail")) {
                                Intent intent = new Intent(thisActivity, ViewFoodItemDetail.class);
                                intent.putExtra("comProId", arrayList.get(pos).getId().toString());
                                startActivityForResult(intent, 111);
                                getActivity().overridePendingTransition(R.anim.push_in_from_left, R.anim.push_out_to_right);
                            } else if (value.equals("delete")) {

                                showPopup(thisActivity, CM.getSp(thisActivity, "customerId", "").toString(), arrayList.get(pos).getId().toString());


                            }

                        }
                    });
                }
            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }


    public void webCallRemoveFav(String cusId, String comPrid) {
        try {
            VolleyIntialization v = new VolleyIntialization(thisActivity, true, true);
            WebService.getResendForRemoveFav(v, cusId, comPrid, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (thisActivity.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForRemoveFav(response);

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


    private void getResponseForRemoveFav(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(thisActivity, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("ResponseCode") != null && jsonObject.getString("ResponseCode").toString().equals("202")) {
                webCallFav(CM.getSp(thisActivity, "customerId", "").toString());
            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(thisActivity, e.getMessage(), false);
        }
    }

    public void showPopup(Context context, final String custId, final String comProdId) {


        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to Remove Favorite Item?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        webCallRemoveFav(custId, comProdId);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.applogo).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
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
