package com.abdelouahad.mustapha.myapp.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.abdelouahad.mustapha.myapp.R;
import com.abdelouahad.mustapha.myapp.model.Compagny;
import com.abdelouahad.mustapha.myapp.model.FirebaseCallback;
import com.abdelouahad.mustapha.myapp.model.RequestMyTravels;

import java.util.ArrayList;

import static com.abdelouahad.mustapha.myapp.controller.MainActivity.COMPANIES_AVAILABLE;

public class ListMyTravels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_travels);
        final ListView mListView = findViewById(R.id.listView);
        final ArrayList<Compagny> compagniesList = new ArrayList<>();

        final ImageView img = findViewById(R.id.logo);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            String title = getResources().getString(R.string.my_travels);
            actionbar.setTitle(title);
            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }


        final RequestMyTravels myTravels = new RequestMyTravels();


        for(int indx = 0; indx< COMPANIES_AVAILABLE.length; indx++) {
            myTravels.getData("USERS", COMPANIES_AVAILABLE[indx], new FirebaseCallback() {
                @Override
                public void onCallback() {

                    String imageBase64 = myTravels.getLogo();
                    String startD = myTravels.getStart_date();
                    String returnD = myTravels.getReturn_date();
                    String price = myTravels.getPrice();
                    String name_compagny= myTravels.getName();
                    final Compagny compagny = new Compagny(name_compagny,price, "PREMIUM", startD, returnD, img, imageBase64);
                    compagniesList.add(compagny);

                    final CompagnyListAdapter adapter = new CompagnyListAdapter(ListMyTravels.this, R.layout.adapter_view_layout, compagniesList);


                    mListView.setAdapter(adapter);
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
