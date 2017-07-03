package com.app.elixir.makkhankitchen.activity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.app.elixir.makkhankitchen.R;
import com.app.elixir.makkhankitchen.adapter.ExpandableListAdapter;
import com.app.elixir.makkhankitchen.database.ItemDetail;
import com.app.elixir.makkhankitchen.database.Items;
import com.app.elixir.makkhankitchen.mtplview.MtplTextView;
import com.app.elixir.makkhankitchen.pojo.PojoArray;
import com.app.elixir.makkhankitchen.pojo.PojoItemDetail;
import com.app.elixir.makkhankitchen.pojo.PojoItems;
import com.app.elixir.makkhankitchen.utils.CM;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewCustomize extends AppCompatActivity {


    private ArrayList<PojoItems> pojoCustomizes;
    private ArrayList<PojoItemDetail> pojoItemDetails;
    public static ExpandableListView expListView;
    public ArrayList<String> listDataHeader;
    public HashMap<String, ArrayList<PojoArray>> listDataChild1;
    public static ExpandableListAdapter listAdapter;
    public ArrayList<PojoItems> pojoItemses;
    public ArrayList<PojoItemDetail> pojoItemDetails1;
    public ArrayList<PojoArray> pojoArrays;
    public static TextView item;
    public static MtplTextView mtplTextViewItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customize);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CM.finishActivity(ViewCustomize.this);
            }
        });
        toolbar.setTitle(getString(R.string.customize));
        TextView titleTextView = null;
        try {
            Field f = (toolbar).getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
            Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_robotolight_0));
            titleTextView.setTypeface(font);
            titleTextView.setTextSize(20);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }


//        CM.setSp(ViewCustomize.this, "data", "[\n" +
//                "  {\n" +
//                "    \"nMapperID\": 28,\n" +
//                "    \"nAttributeID\": 20,\n" +
//                "    \"cAttributeLabel\": \"Meat Temp\",\n" +
//                "    \"isMultiple\": false,\n" +
//                "    \"ListProductAttributeMapperDetail\": [\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 65,\n" +
//                "        \"nMapperID\": 28,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Rare\",\n" +
//                "        \"nPrice\": 0.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 63,\n" +
//                "        \"nMapperID\": 28,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Medium Rare\",\n" +
//                "        \"nPrice\": 0.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 66,\n" +
//                "        \"nMapperID\": 28,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Well Done\",\n" +
//                "        \"nPrice\": 0.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 64,\n" +
//                "        \"nMapperID\": 28,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Medium Well\",\n" +
//                "        \"nPrice\": 0.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 62,\n" +
//                "        \"nMapperID\": 28,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Medium\",\n" +
//                "        \"nPrice\": 0.0\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"nMapperID\": 33,\n" +
//                "    \"nAttributeID\": 1,\n" +
//                "    \"cAttributeLabel\": \"Size\",\n" +
//                "    \"isMultiple\": false,\n" +
//                "    \"ListProductAttributeMapperDetail\": [\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 85,\n" +
//                "        \"nMapperID\": 33,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Small\",\n" +
//                "        \"nPrice\": 1.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 83,\n" +
//                "        \"nMapperID\": 33,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Large\",\n" +
//                "        \"nPrice\": 20.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 84,\n" +
//                "        \"nMapperID\": 33,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Medium\",\n" +
//                "        \"nPrice\": 10.0\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"nMapperID\": 45,\n" +
//                "    \"nAttributeID\": 17,\n" +
//                "    \"cAttributeLabel\": \"Sauce\",\n" +
//                "    \"isMultiple\": false,\n" +
//                "    \"ListProductAttributeMapperDetail\": [\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 175,\n" +
//                "        \"nMapperID\": 45,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Kickin’ BBQ\",\n" +
//                "        \"nPrice\": 0.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 173,\n" +
//                "        \"nMapperID\": 45,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Honey BBQ\",\n" +
//                "        \"nPrice\": 0.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 178,\n" +
//                "        \"nMapperID\": 45,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Tomato\",\n" +
//                "        \"nPrice\": 2.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 171,\n" +
//                "        \"nMapperID\": 45,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"BBQ\",\n" +
//                "        \"nPrice\": 1.5\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 176,\n" +
//                "        \"nMapperID\": 45,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Marinara Sauce\",\n" +
//                "        \"nPrice\": 0.5\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 174,\n" +
//                "        \"nMapperID\": 45,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Honey Mustard\",\n" +
//                "        \"nPrice\": 0.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 172,\n" +
//                "        \"nMapperID\": 45,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Garlic\",\n" +
//                "        \"nPrice\": 0.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 177,\n" +
//                "        \"nMapperID\": 45,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Ranch Dipping Sauce\",\n" +
//                "        \"nPrice\": 1.0\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"nMapperID\": 46,\n" +
//                "    \"nAttributeID\": 18,\n" +
//                "    \"cAttributeLabel\": \"Toppings\",\n" +
//                "    \"isMultiple\": true,\n" +
//                "    \"ListProductAttributeMapperDetail\": [\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 182,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Jalapeno\",\n" +
//                "        \"nPrice\": 0.5\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 187,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Red Paprika\",\n" +
//                "        \"nPrice\": 0.5\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 180,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Crisp Capsicum\",\n" +
//                "        \"nPrice\": 1.5\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 185,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Onions\",\n" +
//                "        \"nPrice\": 0.3\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 183,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Mushrooms\",\n" +
//                "        \"nPrice\": 2.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 188,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Tomato\",\n" +
//                "        \"nPrice\": 1.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 181,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Golden Corn\",\n" +
//                "        \"nPrice\": 1.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 186,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Paneer\",\n" +
//                "        \"nPrice\": 10.0\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 179,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Babycorn\",\n" +
//                "        \"nPrice\": 0.5\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"nAttributeID\": 0,\n" +
//                "        \"cAttributeLabel\": null,\n" +
//                "        \"nMapperDetailID\": 184,\n" +
//                "        \"nMapperID\": 46,\n" +
//                "        \"nAttributeValueMasterID\": 0,\n" +
//                "        \"cAttributeValueLabel\": \"Olives\",\n" +
//                "        \"nPrice\": 1.0\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  }\n" +
//                "]");

        initView();

    }


    public void initView() {
        pojoCustomizes = new ArrayList<>();
        expListView = (ExpandableListView) findViewById(R.id.expListView);
        mtplTextViewItems = (MtplTextView) findViewById(R.id.items);
        addDate();
    }

    public void addDate() {
        prepareListData();

    }


    private void prepareListData() {
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
                listDataHeader.add(pojoItemses.get(j).getcAttributeLabel());
                pojoItemDetails1 = ItemDetail.getSelectedIdRecord(pojoItemses.get(j).getnMapperID());
                pojoArrays = new ArrayList<>();
                pojoArrays.clear();
                for (int i = 0; i < pojoItemDetails1.size(); i++) {
                    PojoArray pojoArray = new PojoArray();
                    pojoArray.setnAttributeID(pojoItemDetails1.get(i).getnAttributeID());
                    pojoArray.setcAttributeLabel(pojoItemDetails1.get(i).getcAttributeLabel());
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
                listDataChild1.put(pojoItemses.get(j).getcAttributeLabel(), pojoArrays);
            }
            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild1);
            expListView.setAdapter(listAdapter);


            item = (TextView) findViewById(R.id.textViewGrandTotal);
            String tot = ItemDetail.getAllCheckeditemRecord();
            try {
                double d = (double) Float.parseFloat(tot);
                item.setText("Total " + CM.getSp(ViewCustomize.this, "currency", "").toString() + " " + String.valueOf(d));
            } catch (Exception e) {

            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customize, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.custom:
                CM.finishActivity(ViewCustomize.this);
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewCustomize.this);
    }
}
