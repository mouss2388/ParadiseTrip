package com.abdelouahad.mustapha.myapp.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.abdelouahad.mustapha.myapp.controller.MainActivity.EXTRA_COUNTRY;

public class ChooseDateActivity extends AppCompatActivity {

    Button btn_classes, btn_search;
    String startDate, returnDate,travelId;
    public static String EXTRA_START_DATE="EXTRA_START_DATE" ;
    public static String EXTRA_RETURN_DATE = "EXTRA_RETURN_DATE";
    DatePickerDialog datePickerDialogStart,  datePickerDialogEnd;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        travelId= getIntent().getStringExtra(EXTRA_COUNTRY);




        btn_classes = findViewById(R.id.classes);
        btn_search = findViewById(R.id.search);

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

                            }
                        }, year, month, day);

                datePickerDialogStart.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialogStart.show();


            }
        });

        selectDateReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogEnd = new DatePickerDialog(ChooseDateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                returnDate = String.valueOf(day + "/" + (month+1) + "/" + year);

                                Toast.makeText(ChooseDateActivity.this, returnDate, Toast.LENGTH_LONG).show();

                            }
                        }, year, month, day);


                datePickerDialogEnd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialogEnd.show();

            }
        });

    }
}
