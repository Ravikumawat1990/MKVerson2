package com.app.elixir.makkhankitchen.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.Model.AppModel;
import com.app.elixir.makkhankitchen.Model.NotificationModel;
import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.adapter.ItemAdapter;
import com.app.elixir.makkhankitchen.adapter.adptNotification;
import com.app.elixir.makkhankitchen.database.tbl_notification;
import com.app.elixir.makkhankitchen.utils.CM;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ViewNotification extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private Toolbar toolbar;
    private RecyclerView mRecyclerView;


    private ListView listview;
    private ItemAdapter adapter;
    private EditText editsearch;
    private ArrayList<AppModel> arrlistItems = new ArrayList<AppModel>();
    int selectionCount = 0;
    private Button btnDone;
    private ImageView checkBox;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Intent intent = getIntent();
        if (intent.getStringExtra("key") != null) {
            tbl_notification.updateTbl(intent.getStringExtra("key"));
        } else {

        }
        // getIntent().getExtras().getString("extra");

        checkBox = (ImageView) findViewById(R.id.allChecked);

        toolbar.setNavigationIcon(R.drawable.ic_back_white);
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
        toolbar.setTitle("Notifications");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.startActivity(ViewNotification.this, ViewDrawerActivty.class);
                finish();

            }
        });


        initView();

    }

    private void initView() {

        listview = (ListView) findViewById(R.id.listview);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<NotificationModel> notificationModels = new ArrayList<>();


        for (int i = 0; i < 50; i++) {
            NotificationModel notificationModel = new NotificationModel();
            notificationModel.setTitle("Ravi" + i);
            notificationModel.setDatetime("1" + i);
            notificationModel.setImageUrl("");
            notificationModels.add(notificationModel);
            AppModel appModel = new AppModel();
            appModel.setAppName("Ravi" + i);
            arrlistItems.add(appModel);
        }

        adapter = new ItemAdapter(ViewNotification.this, arrlistItems, checkBox);
        listview.setAdapter(adapter);

        adptNotification adptNotification = new adptNotification(ViewNotification.this, notificationModels);
        mRecyclerView.setAdapter(adptNotification);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu

        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                        //  adapter.filter(appModels1);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return true; // Return true to expand a
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        // final List<AppModel> filteredModelList = filter(appModels1, newText);

        adapter.filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<AppModel> filter(List<AppModel> models, String query) {
        query = query.toLowerCase();
        final List<AppModel> filteredModelList = new ArrayList<>();
        for (AppModel model : models) {
            final String text = model.getAppName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


}
