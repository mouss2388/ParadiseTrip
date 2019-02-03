package com.abdelouahad.mustapha.myapp.controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;
import com.abdelouahad.mustapha.myapp.model.FirebaseCallback;
import com.abdelouahad.mustapha.myapp.model.RequestDataTravel;

import java.util.ArrayList;
import java.util.List;

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

public class ChooseDateActivity extends AppCompatActivity {

    Button btn_classes, btn_search;
    String startDate, returnDate,travelId;
    String nbPassengers, nbChildren, nbAdults, nbElderly, classe;

    DatePickerDialog datePickerDialogStart,  datePickerDialogEnd;
    TextView start_date_info, return_date_info;
    TextView country_name;
    Boolean dataGot;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        travelId= getIntent().getStringExtra(EXTRA_COUNTRY_ID);
        start_date_info = findViewById(R.id.start_date_info);
        return_date_info = findViewById(R.id.return_date_info);

        btn_classes = findViewById(R.id.classes);
        btn_search = findViewById(R.id.search);
        country_name = findViewById(R.id.destination);

        dataGot = false;
        final Spinner start_spinner = findViewById(R.id.start_spinner);
        final Spinner return_spinner = findViewById(R.id.return_spinner);


        if (getSupportActionBar() != null) {
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            String title = getResources().getString(R.string.config_travel);
            actionbar.setTitle(title);
        }


        final RequestDataTravel info = new RequestDataTravel("Banner+Country+Airports+Rate+Flag");
        final ArrayList[] listAirports = new ArrayList[1];
        final List<String> spinnerArray = new ArrayList<>();



        info.getData("TRAVEL_" +travelId, new FirebaseCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCallback() {
                country_name.setText("Destination: "+info.getCountry());
                listAirports[0] = info.getAirports();

                for (Object airports: listAirports[0]){
                    Log.e("TAG_DATE", airports.toString());
                    spinnerArray.add(airports.toString());

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        ChooseDateActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
                /////
                // (4) set the adapter on the spinner
                //start_spinner.setAdapter(adapter);
                return_spinner.setAdapter(adapter);


              /*  start_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItemText = (String) parent.getItemAtPosition(position);
                        // Notify the selected item text
                        Toast.makeText
                                (getApplicationContext(), "Selected : " + position, Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/

            }});

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        final ImageButton selectDateStart = findViewById(R.id.icn_date_from);
        ImageButton selectDateReturn = findViewById(R.id.icn_date_to);



        btn_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ChooseDateActivity.this, TravellersActivity.class);
                ChooseDateActivity.this.startActivityForResult(intent,200);

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(returnDate ==null || startDate ==null) {
                    Toast.makeText(ChooseDateActivity.this, "Vous avez oublié de sélectionner une date de départ ou de retour", Toast.LENGTH_LONG).show();
                }else if(!dataGot) {
                    Toast.makeText(ChooseDateActivity.this, "Vous avez oublié de sélectionner un nombre de passagers et une classe", Toast.LENGTH_LONG).show();

                }else{
                    String startAirport = String.valueOf(start_spinner.getSelectedItem());
                    String returnAirport = String.valueOf(return_spinner.getSelectedItem());

                    Intent intent = new Intent(ChooseDateActivity.this, Result.class);
                    intent.putExtra(EXTRA_RETURN_DATE, returnDate);
                    intent.putExtra(EXTRA_START_DATE, startDate);
                    intent.putExtra(EXTRA_COUNTRY_ID, travelId);
                    intent.putExtra(EXTRA_COUNTRY_NAME, info.getCountry());
                    intent.putExtra(EXTRA_START_AIRPORT, startAirport);
                    intent.putExtra(EXTRA_RETURN_AIRPORT, returnAirport);
                    intent.putExtra(EXTRA_RATING, info.getRate());
                    intent.putExtra(EXTRA_CLASS, classe);
                    intent.putExtra(EXTRA_BANNER, info.getBanner());
                    intent.putExtra(EXTRA_COUNTRY_FLAG, info.getFlag());
                    intent.putExtra(EXTRA_ALL_PASSENGERS, nbPassengers);
                    intent.putExtra(EXTRA_ADULT_PASSENGER, nbAdults);
                    intent.putExtra(EXTRA_CHILDREN_PASSENGER, nbChildren);
                    intent.putExtra(EXTRA_ELDERLY_PASSENGER, nbElderly);


                    Toast.makeText(ChooseDateActivity.this, String.valueOf(info.getRate()), Toast.LENGTH_LONG).show();

                    ChooseDateActivity.this.startActivity(intent);
                }


            }
        });


        selectDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialogStart= new DatePickerDialog(ChooseDateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                startDate= String.valueOf(day + "/" + (month+1) + "/" + year);
                                Toast.makeText(ChooseDateActivity.this, startDate, Toast.LENGTH_LONG).show();
                                start_date_info.setText(startDate);
                                start_date_info.setVisibility(View.VISIBLE);


                            }
                        }, year, month, day);

                datePickerDialogStart.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialogStart.show();


            }
        });



        selectDateReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datePickerDialogStart!=null && !start_date_info.getText().equals("xx/xx/xxxx")) {
                    datePickerDialogEnd = new DatePickerDialog(ChooseDateActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    returnDate = String.valueOf(day + "/" + (month + 1) + "/" + year);
                                    return_date_info.setText(returnDate);
                                    return_date_info.setVisibility(View.VISIBLE);

                                    Toast.makeText(ChooseDateActivity.this, returnDate, Toast.LENGTH_LONG).show();

                                }
                            }, year, month, day);



                    Calendar calendar = Calendar.getInstance();
                    calendar.set(datePickerDialogStart.getDatePicker().getYear(), datePickerDialogStart.getDatePicker().getMonth(), datePickerDialogStart.getDatePicker().getDayOfMonth(),
                            0, 0, 0);
                    long startTime = calendar.getTimeInMillis();


                    datePickerDialogEnd.getDatePicker().setMinDate(startTime);


                    datePickerDialogEnd.show();
                }else{
                    Toast.makeText(ChooseDateActivity.this, "Sélectionnez d'abord une date de départ", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (200) : {
                if (resultCode == RESULT_OK) {
                    dataGot = true;
                    nbChildren = data.getStringExtra(EXTRA_CHILDREN_PASSENGER);
                    nbAdults = data.getStringExtra(EXTRA_ADULT_PASSENGER);
                    nbElderly = data.getStringExtra(EXTRA_ELDERLY_PASSENGER);
                    nbPassengers = data.getStringExtra(EXTRA_ALL_PASSENGERS);
                    classe = data.getStringExtra(EXTRA_CLASS);
                    Toast.makeText(ChooseDateActivity.this, "children: "+nbChildren
                            +" adults: "+nbAdults+ " senior: "+nbElderly+
                            "classe: "+classe, Toast.LENGTH_LONG).show();

                }
                break;
            }
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
