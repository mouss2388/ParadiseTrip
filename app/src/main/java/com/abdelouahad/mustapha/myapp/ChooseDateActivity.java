package com.abdelouahad.mustapha.myapp;

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

public class ChooseDateActivity extends AppCompatActivity {

    Button btn_classes, btn_search;
    String startDate, returnDate;
    public static String EXTRA_START_DATE="EXTRA_START_DATE" ;
    public static String EXTRA_RETURN_DATE = "EXTRA_RETURN_DATE";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);



        btn_classes = findViewById(R.id.classes);
        btn_search = findViewById(R.id.search);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        ImageButton selectDateStart = findViewById(R.id.icn_date_from);
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

                Intent intent = new Intent(ChooseDateActivity.this, Result.class);
                intent.putExtra(EXTRA_RETURN_DATE, returnDate);
                intent.putExtra(EXTRA_START_DATE, startDate);

                ChooseDateActivity.this.startActivity(intent);

            }
        });


        selectDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ChooseDateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                               startDate= String.valueOf(day + "/" + month + "/" + year);

                                Toast.makeText(ChooseDateActivity.this, startDate, Toast.LENGTH_LONG).show();

                            }
                        }, year, month, day);

                datePickerDialog.show();


            }
        });

        selectDateReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ChooseDateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                returnDate = String.valueOf(day + "/" + month + "/" + year);

                                Toast.makeText(ChooseDateActivity.this, returnDate, Toast.LENGTH_LONG).show();

                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

    }
}
