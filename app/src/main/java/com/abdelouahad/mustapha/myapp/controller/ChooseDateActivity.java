package com.abdelouahad.mustapha.myapp.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import static com.abdelouahad.mustapha.myapp.controller.MainActivity.EXTRA_COUNTRY;

public class ChooseDateActivity extends AppCompatActivity {

    Button btn_classes, btn_search;
    String startDate, returnDate,travelId;
    public static String EXTRA_START_DATE="EXTRA_START_DATE" ;
    public static String EXTRA_RETURN_DATE = "EXTRA_RETURN_DATE";
    DatePickerDialog datePickerDialogStart,  datePickerDialogEnd;
    TextView start_date_info, return_date_info;
    TextView country_name;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        travelId= getIntent().getStringExtra(EXTRA_COUNTRY);
        start_date_info = findViewById(R.id.start_date_info);
        return_date_info = findViewById(R.id.return_date_info);

        btn_classes = findViewById(R.id.classes);
        btn_search = findViewById(R.id.search);
        country_name = findViewById(R.id.destination);

        final Spinner start_spinner = findViewById(R.id.start_spinner);
        final Spinner return_spinner = findViewById(R.id.return_spinner);


        final RequestDataTravel info = new RequestDataTravel("Country+Airports");
        final ArrayList<String>[] listAirports = new ArrayList[1];
        final List<String> spinnerArray = new ArrayList<>();



        info.getData("TRAVEL_" +travelId, new FirebaseCallback() {
            @Override
            public void onCallback() {
                country_name.setText("Destination: "+info.getCountry());
                listAirports[0] = info.getAirports();

                for (Object airports: listAirports[0]){
                    Log.e("TAG_DATE", airports.toString());
                    spinnerArray.add(airports.toString());

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ChooseDateActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
                /////
                // (4) set the adapter on the spinner
                start_spinner.setAdapter(adapter);
                return_spinner.setAdapter(adapter);


                start_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                });
                return_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                });

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
                ChooseDateActivity.this.startActivity(intent);

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(returnDate ==null || startDate ==null) {
                    Toast.makeText(ChooseDateActivity.this, "Vous avez oublié de sélectionner une date de départ ou de retour", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(ChooseDateActivity.this, Result.class);
                    intent.putExtra(EXTRA_RETURN_DATE, returnDate);
                    intent.putExtra(EXTRA_START_DATE, startDate);
                    intent.putExtra(EXTRA_COUNTRY, travelId);

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
                if(datePickerDialogStart!=null) {
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


                    long datePicjerD = datePickerDialogStart.getDatePicker().getMonth();
                    int dayP=datePickerDialogStart.getDatePicker().getDayOfMonth();
                    int monthP= datePickerDialogStart.getDatePicker().getMonth();
                    int yearP= datePickerDialogStart.getDatePicker().getYear();
                    long date = datePickerDialogStart.getDatePicker().getMinDate();

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
}
