package com.abdelouahad.mustapha.myapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.abdelouahad.mustapha.myapp.R;
import com.abdelouahad.mustapha.myapp.model.Compagny;
import com.abdelouahad.mustapha.myapp.model.FirebaseCallback;
import com.abdelouahad.mustapha.myapp.model.RequestMyTravels;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static com.abdelouahad.mustapha.myapp.model.EXTRA.COMPANIES_AVAILABLE;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_COMPAGNY_NAME;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_COUNTRY_ID;


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


        for (String aCOMPANIES_AVAILABLE : COMPANIES_AVAILABLE) {
            myTravels.getData("USERS", aCOMPANIES_AVAILABLE, new FirebaseCallback() {
                @Override
                public void onCallback() {

                    for (RequestMyTravels mTravel : myTravels.getmList()) {
                        Log.i("ListMyTravels", " name: " + mTravel.getToCountry() + " price: " + mTravel.getPrice());

                        Compagny compagny = new Compagny(mTravel.getId(),mTravel.getPrice(), mTravel.getToCountry(),
                                mTravel.getStart_date(), mTravel.getReturn_date(),
                                img, mTravel.getLogo(), mTravel.getName(),
                                mTravel.getStart_hour(), mTravel.getReturn_hour(),
                                mTravel.getTel(), mTravel.getDuration());

                        compagniesList.add(compagny);
                    }


                    final CompagnyListAdapter adapter = new CompagnyListAdapter(ListMyTravels.this, R.layout.adapter_view_layout, compagniesList);

                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {

                                Intent intent = new Intent(ListMyTravels.this, MyTravel.class);
                                intent.putExtra(EXTRA_COUNTRY_ID, compagniesList.get(position).getId());
                                intent.putExtra(EXTRA_COMPAGNY_NAME, compagniesList.get(position).getName());
                                ListMyTravels.this.startActivity(intent);

                            }
                        }
                    });
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
