package com.app.elixir.makkhankitchen.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Fragment.FragBookOrder;
import com.app.elixir.makkhankitchen.Fragment.FragContactUs;
import com.app.elixir.makkhankitchen.Fragment.FragCouponCode;
import com.app.elixir.makkhankitchen.Fragment.FragDisclaminer;
import com.app.elixir.makkhankitchen.Fragment.FragFavarities;
import com.app.elixir.makkhankitchen.Fragment.FragFeedBack;
import com.app.elixir.makkhankitchen.Fragment.FragFoodCategory;
import com.app.elixir.makkhankitchen.Fragment.FragFoodItem;
import com.app.elixir.makkhankitchen.Fragment.FragMyProfile;
import com.app.elixir.makkhankitchen.Fragment.FragNotification;
import com.app.elixir.makkhankitchen.Fragment.FragOrderHistory;
import com.app.elixir.makkhankitchen.Fragment.FragReturnPolicy;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.database.tbl_notification;
import com.app.elixir.makkhankitchen.interfac.ActionBarTitleSetter;
import com.app.elixir.makkhankitchen.interfac.OnFragmentInteractionListener;
import com.app.elixir.makkhankitchen.mtplview.MtplLog;
import com.app.elixir.makkhankitchen.utils.CM;
import com.app.elixir.makkhankitchen.utils.CV;
import com.app.elixir.makkhankitchen.utils.CustomTypefaceSpan;
import com.app.elixir.makkhankitchen.volly.OnVolleyHandler;
import com.app.elixir.makkhankitchen.volly.VolleyIntialization;
import com.app.elixir.makkhankitchen.volly.WebService;
import com.app.elixir.makkhankitchen.volly.WebServiceTag;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class ViewDrawerActivty extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActionBarTitleSetter, OnFragmentInteractionListener {

    private static final String LOG_TAG = "Check Internet";
    private String mTitle;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private static final String TAG = "logout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drawer_activty);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.food_category));
        setSupportActionBar(toolbar);
        /**
         * @title font set
         */
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /**
         * Drawer MenuItem Font Set
         */
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        if (savedInstanceState == null) {
            FragmentManager fm = this.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = new FragFoodCategory();
            ft.replace(R.id.container, fragment);
            ft.commit();
        }
        //FragFoodCategory

        //FragCategory

        // setFragment(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcast_reciever, new IntentFilter("finish_activityCust"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        hideSoftKeyboard();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
            if (f instanceof FragFoodCategory) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }
            } else if (f instanceof FragFoodItem) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }
            } else if (f instanceof FragMyProfile) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }


            } else if (f instanceof FragOrderHistory) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }


            } else if (f instanceof FragOrderHistory) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }


            } else if (f instanceof FragContactUs) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }


            } else if (f instanceof FragCouponCode) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }

            } else if (f instanceof FragFeedBack) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }


            } else if (f instanceof FragReturnPolicy) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }
            } else if (f instanceof FragDisclaminer) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }
            } else if (f instanceof FragNotification) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }
            } else if (f instanceof FragBookOrder) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }
            } else if (f instanceof FragFavarities) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    setTitle(getString(R.string.app_name));
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                    fm.popBackStack();
                } else {
                    showPopup(ViewDrawerActivty.this);
                }
            } else {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                } else {
                    finish();
                }
            }
        }

    }

    /**
     * Opening Drawer in each Sub Fragment
     *
     * @param showDrawerToggle
     */
    @Override
    public void showDrawerToggle(final boolean showDrawerToggle) {
        if (!showDrawerToggle) {
            toggle.setDrawerIndicatorEnabled(showDrawerToggle);
            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_white));
            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard();
                    Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
                    if (f instanceof FragFoodCategory) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }
                    } else if (f instanceof FragFoodItem) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_white));
                            fm.popBackStack();
                            setTitle(getString(R.string.app_name));

                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }
                    } else if (f instanceof FragMyProfile) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }


                    } else if (f instanceof FragOrderHistory) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }


                    } else if (f instanceof FragContactUs) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }


                    } else if (f instanceof FragCouponCode) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }


                    } else if (f instanceof FragFeedBack) {

                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }


                    } else if (f instanceof FragReturnPolicy) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }
                    } else if (f instanceof FragDisclaminer) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }
                    } else if (f instanceof FragNotification) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }

                    } else if (f instanceof FragFavarities) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }


                    } else if (f instanceof FragBookOrder) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }

                    } else if (f instanceof FragFavarities) {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            setTitle(getString(R.string.app_name));
                            toggle.setDrawerIndicatorEnabled(true);
                            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }


                    } else {
                        FragmentManager fm = getSupportFragmentManager();
                        if (fm.getBackStackEntryCount() > 0) {
                            fm.popBackStack();
                        } else {
                            showPopup(ViewDrawerActivty.this);
                        }
                    }

                }
            });
        } else {
            toggle.setDrawerIndicatorEnabled(showDrawerToggle);
            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu));
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setFragment(0);
        } else if (id == R.id.nav_profile) {
            setFragment(1);
        } else if (id == R.id.nav_contactUs) {
            setFragment(2);
        } else if (id == R.id.nav_fav) {
            setFragment(3);
        } else if (id == R.id.nav_feedback) {
            setFragment(4);
        } else if (id == R.id.orderHist) {
            setFragment(5);
        } else if (id == R.id.nav_returnPolicy) {
            setFragment(6);
        } else if (id == R.id.nav_disclaminer) {
            setFragment(7);
        } else if (id == R.id.nav_share) {
            setFragment(8);
        } else if (id == R.id.nav_Offers) {
            setFragment(9);
        } else if (id == R.id.nav_logout) {
            setFragment(10);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setFragment(int i) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new FragFoodCategory();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.container, fragment);
                ft.commit();
                break;
            case 1:
                fragment = new FragMyProfile();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.replace(R.id.container, fragment).addToBackStack("FragFoodCategory");
                ft.commit();
                break;
            case 2:
                fragment = new FragContactUs();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.add(R.id.container, fragment).addToBackStack("FragFoodCategory");
                ft.commit();
                break;
            case 3:
                fragment = new FragFavarities();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.add(R.id.container, fragment).addToBackStack("FragFoodCategory");
                ft.commit();
                break;
            case 4:
                fragment = new FragFeedBack();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.add(R.id.container, fragment).addToBackStack("FragFoodCategory");
                ft.commit();
                break;
            case 5:
                fragment = new FragOrderHistory();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.add(R.id.container, fragment).addToBackStack("FragFoodCategory");
                ft.commit();
                break;
            case 6:
                fragment = new FragReturnPolicy();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.add(R.id.container, fragment).addToBackStack("FragFoodCategory");
                ft.commit();
                break;
            case 7:
                fragment = new FragDisclaminer();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                ft.add(R.id.container, fragment).addToBackStack("FragFoodCategory");
                ft.commit();
                break;
            case 8:
                CM.shareApp(this);
                break;
            case 9:
                CM.startActivity(ViewDrawerActivty.this, ViewOfferScreen.class);
                break;
            case 10:
                showPopupForLogOut(ViewDrawerActivty.this);
                break;

            default:
                break;
        }
    }


    /**
     * Title in each folder
     *
     * @param title
     */
    @Override
    public void setTitle(String title) {
        try {
            mTitle = title;
            toolbar.setTitle(mTitle);
            Log.e("title", mTitle);
        } catch (Exception e) {

        }
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void showPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.applogo).show();
    }

    public void showPopupForLogOut(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        webCallLogOut(CM.getSp(ViewDrawerActivty.this, "customerId", "").toString(), CM.getSp(ViewDrawerActivty.this, "regId", "").toString());


                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.applogo).show();
    }


    /**
     * Apply font in Drawer MenuItem
     *
     * @param mi
     */
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_robotolight_0));
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            String action = intent.getAction();
            if (action.equals("finish_activityCust")) {
                finish();


                // DO WHATEVER YOU WANT.
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast_reciever);
    }

    public void webCallLogOut(String cusId, String deviceId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewDrawerActivty.this, false, false);
            WebService.getResponseRemoveDevice(v, cusId, deviceId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForLogOut(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewDrawerActivty.this)) {
                        CM.showPopupCommonValidation(ViewDrawerActivty.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getResponseForLogOut(String response) {


        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewDrawerActivty.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("ResponseCode") != null && jsonObject.getString("ResponseCode").equals("203")) {
                try {
                    CM.setSp(ViewDrawerActivty.this, TAG, "1");
                    CM.setSp(ViewDrawerActivty.this, "custLog", "");
                    CM.setSp(ViewDrawerActivty.this, "cartId", "");
                    CM.setSp(ViewDrawerActivty.this, "number", "");
                    CM.setSp(ViewDrawerActivty.this, "fname", "");
                    CM.setSp(ViewDrawerActivty.this, "lname", "");
                    CM.setSp(ViewDrawerActivty.this, "email", "");
                    CM.setSp(ViewDrawerActivty.this, "pNo", "");
                    CM.setSp(ViewDrawerActivty.this, "customerId", "");
                    CM.setSp(ViewDrawerActivty.this, "time", "");
                    CM.setSp(ViewDrawerActivty.this, "date", "");
                    CM.setSp(ViewDrawerActivty.this, "openingTime", "");
                    CM.setSp(ViewDrawerActivty.this, "closingTime", "");
                    CM.setSp(ViewDrawerActivty.this, "timeInterval", "");
                    CM.setSp(ViewDrawerActivty.this, "itemPrice", "");
                    CM.setSp(ViewDrawerActivty.this, "quantity", "");
                    CM.setSp(ViewDrawerActivty.this, "serverDate", "");
                    CM.setSp(ViewDrawerActivty.this, "serverTime", "");
                    CM.setSp(ViewDrawerActivty.this, "bIsDiscountAfterTax", "");


                    try {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(CV.TOPIC_GLOBAL);
                        tbl_notification.deleteAll();
                    } catch (Exception e) {
                        e.getMessage();
                    }

                } catch (Exception e) {
                    e.getMessage();
                }

                CM.startActivity(ViewDrawerActivty.this, ViewLoginActivity.class);
                finish();
            } else {

                if (jsonObject.getString("ResponseObject") != null) {
                    CM.showToast(ViewDrawerActivty.this, jsonObject.getString("ResponseObject"));

                } else {
                    CM.showToast(ViewDrawerActivty.this, "Invalid Response");

                }
            }


        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewDrawerActivty.this, e.getMessage(), false);
        }
    }
}
