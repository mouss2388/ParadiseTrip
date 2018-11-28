package com.abdelouahad.mustapha.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Date;

import static com.abdelouahad.mustapha.myapp.ChooseDateActivity.EXTRA_RETURN_DATE;
import static com.abdelouahad.mustapha.myapp.ChooseDateActivity.EXTRA_START_DATE;

public class Result extends AppCompatActivity {
    Date min_compagny, max_compagny;   // assume these are set to something
    Date dateStart, dateReturn;          // the date in question
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Toast.makeText(Result.this, "BEFORE REQUEST FLIGHTS", Toast.LENGTH_LONG).show();

        final RequestFlights flights = new RequestFlights();
        flights.getData("FLIGHTS_AVAILABLE",new FirebaseCallback() {
            @Override
            public void onCallback() {


                Log.e("Result Activity", flights.getLogo());
                Log.e("Result Activity", flights.getPrice());


/*
                min_compagny= new Date(flights.getStart_date());
                max_compagny = new Date(flights.getReturn_date());

                String startD =getIntent().getStringExtra(EXTRA_START_DATE);
                String returnD =getIntent().getStringExtra(EXTRA_RETURN_DATE);

                dateStart = new Date(startD);
                dateReturn = new Date(returnD);*/

              //  dateAvailable();
                // Toast.makeText(Result.this, flights.getPrice(), Toast.LENGTH_LONG).show();
            }
        });

        //Toast.makeText(Result.this, "AFTER REQUEST FLIGHTS", Toast.LENGTH_LONG).show();

    }

    void dateAvailable(){
        if(dateStart.after(min_compagny) && dateReturn.before(max_compagny))
            Log.e("dateAvailable", "Between Date");

    }
}
