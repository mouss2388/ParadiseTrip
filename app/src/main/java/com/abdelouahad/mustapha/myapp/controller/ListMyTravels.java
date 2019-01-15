package com.abdelouahad.mustapha.myapp.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;
import com.abdelouahad.mustapha.myapp.model.Compagny;
import com.abdelouahad.mustapha.myapp.model.FirebaseCallback;
import com.abdelouahad.mustapha.myapp.model.RequestMyTravels;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                    for(RequestMyTravels mTravel : myTravels.getmList()){
                        Log.i("ListMyTravels", " name: " + mTravel.getName() + " price: " + mTravel.getPrice());
                        final Compagny compagny = new Compagny(mTravel.getName(), mTravel.getPrice(), "PREMIUM", mTravel.getStart_date(), mTravel.getReturn_date(), img, mTravel.getLogo());
                        compagniesList.add(compagny);
                    }


                    final CompagnyListAdapter adapter = new CompagnyListAdapter(ListMyTravels.this, R.layout.adapter_view_layout, compagniesList);

                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String tag_name_compagny = compagniesList.get(position).getName();
                                String tag_logo_base64 = compagniesList.get(position).getImageBase64();
                                String tag_start_date = compagniesList.get(position).getStart_date();
                                String tag_return_date = compagniesList.get(position).getReturn_date();
                                String tag_price = compagniesList.get(position).getPrice();

                                Toast.makeText(ListMyTravels.this, tag_name_compagny+" "+ tag_price, Toast.LENGTH_SHORT).show();


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
