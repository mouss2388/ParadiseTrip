package com.abdelouahad.mustapha.myapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChooseDateActivity extends AppCompatActivity {

    Button btn_classes;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        btn_classes = findViewById(R.id.classes);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);

        ImageButton selectDate = findViewById(R.id.icn_date_from);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ChooseDateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {


                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
                String dateStr =  String.valueOf(dayOfMonth + "/" + month + "/" + year);
                Toast.makeText(ChooseDateActivity.this,dateStr,Toast.LENGTH_LONG).show();


            }
        });



        btn_classes = findViewById(R.id.classes);

        btn_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseDateActivity.this, TravellersActivity.class);
                ChooseDateActivity.this.startActivity(intent);

            }
        });

    }
}
