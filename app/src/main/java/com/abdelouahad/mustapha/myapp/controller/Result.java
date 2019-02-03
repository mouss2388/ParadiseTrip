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
import com.abdelouahad.mustapha.myapp.model.RequestFlights;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.abdelouahad.mustapha.myapp.model.EXTRA.COMPANIES_AVAILABLE;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_ADULT_PASSENGER;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_ALL_PASSENGERS;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_BANNER;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_CHILDREN_PASSENGER;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_CLASS;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_COUNTRY_FLAG;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_COUNTRY_ID;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_COUNTRY_NAME;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_ELDERLY_PASSENGER;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_RATING;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_RETURN_AIRPORT;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_RETURN_DATE;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_START_AIRPORT;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_START_DATE;


public class Result extends AppCompatActivity {
    public static final String TAG="Result";

    String travelId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final ImageView img = findViewById(R.id.logo);

        travelId= getIntent().getStringExtra(EXTRA_COUNTRY_ID);
        final String banner = getIntent().getStringExtra(EXTRA_BANNER);
        final String nbPasengers = getIntent().getStringExtra(EXTRA_ALL_PASSENGERS);
        final String nbChildrens = getIntent().getStringExtra(EXTRA_CHILDREN_PASSENGER);
        final String nbAdults = getIntent().getStringExtra(EXTRA_ADULT_PASSENGER);
        final String nbElderly = getIntent().getStringExtra(EXTRA_ELDERLY_PASSENGER);
        final String start_airports = getIntent().getStringExtra(EXTRA_START_AIRPORT);
        final String return_airports = getIntent().getStringExtra(EXTRA_RETURN_AIRPORT);
        final int rating = getIntent().getIntExtra(EXTRA_RATING,0);
        final String flag = getIntent().getStringExtra(EXTRA_COUNTRY_FLAG);
        final String classe = getIntent().getStringExtra(EXTRA_CLASS);

        Toast.makeText(Result.this, String.valueOf(rating), Toast.LENGTH_LONG).show();


//        Log.i("RESULT_ACTIVITY",nbPasengers);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            String title = getResources().getString(R.string.flights_available);
            actionbar.setTitle(title);            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }


        Log.d(TAG,"Oncreate: Started");
        final ListView mListView = findViewById(R.id.listView);
        final ArrayList<Compagny> compagniesList = new ArrayList<>();

        // Toast.makeText(Result.this, "BEFORE REQUEST FLIGHTS", Toast.LENGTH_LONG).show();

        final RequestFlights flights = new RequestFlights("ALL");

        for (String aCOMPANIES_AVAILABLE : COMPANIES_AVAILABLE) {


            flights.getData("FLIGHTS_AVAILABLE/ID_" + travelId + "/COMPAGNIES/", aCOMPANIES_AVAILABLE, new FirebaseCallback() {
                @Override
                public void onCallback() {

                    String logo = flights.getLogo();
                    String start_date = getIntent().getStringExtra(EXTRA_START_DATE);
                    String return_date = getIntent().getStringExtra(EXTRA_RETURN_DATE);
                    String price = flights.getPrice();
                    String name_compagny = flights.getName_compagny();
                    String start_hour = flights.getStartHour();
                    String return_hour = flights.getReturnHour();
                    String tel = flights.getTel();
                    String duration = flights.getDuration();

                    final String destination = getIntent().getStringExtra(EXTRA_COUNTRY_NAME);

                    Toast.makeText(Result.this, "Name Compagny : " + name_compagny, Toast.LENGTH_SHORT).show();
                    final Compagny compagny = new Compagny(price, destination, start_date, return_date, img, logo, name_compagny, start_hour, return_hour, tel, duration);
                    compagniesList.add(compagny);

                    final CompagnyListAdapter adapter = new CompagnyListAdapter(Result.this, R.layout.adapter_view_layout, compagniesList);


                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                Toast.makeText(Result.this, "You are login : ", Toast.LENGTH_SHORT).show();

                          /*  TextView start_date = view.findViewById(R.id.start_date);
                            TextView return_date = view.findViewById(R.id.return_date);
                            TextView price =  view.findViewById(R.id.price);*/
                           /* String tag_start_date = start_date.getText().toString();
                            String tag_return_date = return_date.getText().toString();
                            String tag_price = price.getText().toString();*/
                                String tag_name_compagny = compagniesList.get(position).getName();
                                String tag_logo_base64 = compagniesList.get(position).getImageBase64();
                                String tag_start_date = compagniesList.get(position).getStart_date();
                                String tag_return_date = compagniesList.get(position).getReturn_date();
                                String tag_price = compagniesList.get(position).getPrice();
                                String tel = compagniesList.get(position).getTel();
                                String return_hour = compagniesList.get(position).getReturn_hour();
                                String start_hour = compagniesList.get(position).getStart_hour();
                                String duration = compagniesList.get(position).getDuration();


                                Toast.makeText(Result.this, "You selected : ", Toast.LENGTH_SHORT).show();

                                // Write a message to the database
                                FirebaseDatabase database = FirebaseDatabase.getInstance();

                                String rootPathUsers = "/USERS/" + user.getUid() + "/MY_TRAVELS/ID_" + travelId + "/COMPAGNIES/" + tag_name_compagny + "/";
                                ArrayList<DatabaseReference> myRef = new ArrayList<>();
                                myRef.add(database.getReference(rootPathUsers));
                                myRef.add(database.getReference(rootPathUsers + "Price"));
                                myRef.add(database.getReference(rootPathUsers + "StartDate"));
                                myRef.add(database.getReference(rootPathUsers + "ReturnDate"));
                                myRef.add(database.getReference(rootPathUsers + "Logo"));
                                myRef.add(database.getReference(rootPathUsers + "Banner"));
                                myRef.add(database.getReference(rootPathUsers + "NbPassengers"));
                                myRef.add(database.getReference(rootPathUsers + "NbAdults"));
                                myRef.add(database.getReference(rootPathUsers + "NbChildren"));
                                myRef.add(database.getReference(rootPathUsers + "NbElderly"));
                                myRef.add(database.getReference(rootPathUsers + "Rating"));
                                myRef.add(database.getReference(rootPathUsers + "StartAirport"));
                                myRef.add(database.getReference(rootPathUsers + "ReturnAirport"));
                                myRef.add(database.getReference(rootPathUsers + "ToCountry"));
                                myRef.add(database.getReference(rootPathUsers + "Flag"));
                                myRef.add(database.getReference(rootPathUsers + "Class"));
                                myRef.add(database.getReference(rootPathUsers + "ReturnHour"));
                                myRef.add(database.getReference(rootPathUsers + "StartHour"));
                                myRef.add(database.getReference(rootPathUsers + "Tel"));
                                myRef.add(database.getReference(rootPathUsers + "Duration"));


                                for (DatabaseReference ref : myRef) {
                                    if (ref.getPath().toString().equals(rootPathUsers + "Price")) {
                                        ref.setValue(tag_price);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "StartDate")) {
                                        ref.setValue(tag_start_date);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "ReturnDate")) {
                                        ref.setValue(tag_return_date);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "Logo")) {
                                        ref.setValue(tag_logo_base64);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "Banner")) {
                                        ref.setValue(banner);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "NbPassengers")) {
                                        ref.setValue(nbPasengers);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "NbAdults")) {
                                        ref.setValue(nbAdults);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "NbChildren")) {
                                        ref.setValue(nbChildrens);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "NbElderly")) {
                                        ref.setValue(nbElderly);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "Rating")) {
                                        ref.setValue(rating);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "StartAirport")) {
                                        ref.setValue(start_airports);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "ReturnAirport")) {
                                        ref.setValue(return_airports);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "ToCountry")) {
                                        ref.setValue(destination);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "Flag")) {
                                        ref.setValue(flag);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "Class")) {
                                        ref.setValue(classe);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "StartHour")) {
                                        ref.setValue(start_hour);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "ReturnHour")) {
                                        ref.setValue(return_hour);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "Tel")) {
                                        ref.setValue(tel);
                                    } else if (ref.getPath().toString().equals(rootPathUsers + "Duration")) {
                                        ref.setValue(duration);
                                    }
                                }
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
