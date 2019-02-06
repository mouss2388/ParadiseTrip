package com.abdelouahad.mustapha.myapp.controller;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdelouahad.mustapha.myapp.R;
import com.abdelouahad.mustapha.myapp.model.FirebaseCallback;
import com.abdelouahad.mustapha.myapp.model.RequestThisTravel;

import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_COMPAGNY_NAME;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_COUNTRY_ID;

public class MyTravel extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_travel);

        if (getSupportActionBar() != null) {
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            String title = getResources().getString(R.string.my_travel);
            actionbar.setTitle(title);
        }

        final ImageView banner = findViewById(R.id.header_travel);
        final TextView countryName = findViewById(R.id.country);
        final TextView tel = findViewById(R.id.numero_tel);
        final  ImageView logo = findViewById(R.id.logo_compagny);
        final  ImageView countryFlag = findViewById(R.id.flag_country);
        final  TextView classe = findViewById(R.id.class_flight);
        final  ImageView star1 = findViewById(R.id.star_1);
        final  ImageView star2 = findViewById(R.id.star_2);
        final  ImageView star3 = findViewById(R.id.star_3);
        final  ImageView star4 = findViewById(R.id.star_4);
        final  ImageView star5 = findViewById(R.id.star_5);
        final  TextView price = findViewById(R.id.price);
        final  TextView allPassengers =findViewById(R.id.howMuchPassenger);
        final  TextView children =findViewById(R.id.howMuchChild);
        final  TextView adults =findViewById(R.id.howMuchAdult);
        final  TextView elderly =findViewById(R.id.howMuchElderly);
        final  TextView startAiport =findViewById(R.id.start_airport_detail);
        final  TextView startDate =findViewById(R.id.start_date_detail);
        final  TextView startHour =findViewById(R.id.start_hour_detail);
        final  TextView startDuration =findViewById(R.id.start_duration_detail);
        final  TextView returnAiport =findViewById(R.id.return_airport_detail);
        final  TextView returnDate =findViewById(R.id.return_date_detail);
        final  TextView returnHour =findViewById(R.id.return_hour_detail);
        final  TextView returnDuration =findViewById(R.id.return_duration_detail);
        final  Button delete = findViewById(R.id.btn_delete_travel);


        String travelId= getIntent().getStringExtra(EXTRA_COUNTRY_ID);

        String nameCompagny= getIntent().getStringExtra(EXTRA_COMPAGNY_NAME);

        Log.i("MyTravel", travelId+" name Comapgny:"+nameCompagny);


        final RequestThisTravel myTravel = new RequestThisTravel();


        String id = travelId.replace("ID_","");
        myTravel.getImages("TRAVEL_"+id, new FirebaseCallback() {
            @Override
            public void onCallback() {

               byte[] decodedString = Base64.decode(String.valueOf(myTravel.getBanner()), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                banner.setImageBitmap(decodedByte);

                decodedString = Base64.decode(String.valueOf(myTravel.getFlagCountry()), Base64.DEFAULT);
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                countryFlag.setImageBitmap(decodedByte);

            }
        });



        myTravel.getData("USERS", travelId, nameCompagny, new FirebaseCallback() {
            @Override
            public void onCallback() {


                byte[] decodedString = Base64.decode(String.valueOf(myTravel.getLogoCompagny()), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                logo.setImageBitmap(decodedByte);


                startDuration.setText(myTravel.getDuration());
                returnDuration.setText(myTravel.getDuration());
                children.setText(myTravel.getNbChildren());
                adults.setText(myTravel.getNbAdults());
                elderly.setText(myTravel.getNbElderly());
                allPassengers.setText(myTravel.getNbPassengers());
                price.setText(myTravel.getPrice()+" Ttc");
                returnAiport.setText(myTravel.getReturnAirport());
                returnDate.setText(myTravel.getReturnDate());
                returnHour.setText(myTravel.getReturnHour());
                startAiport.setText(myTravel.getStartAirport());
                startDate.setText(myTravel.getStartDate());
                startHour.setText(myTravel.getStartHour());
                classe.setText(myTravel.getClasse());
                countryName.setText(" "+myTravel.getCountry());
                countryName.setPaintFlags(countryName.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                tel.setText(myTravel.getTel());

            }
        });




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
