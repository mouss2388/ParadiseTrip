package com.abdelouahad.mustapha.myapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import static com.abdelouahad.mustapha.myapp.ChooseDateActivity.EXTRA_RETURN_DATE;
import static com.abdelouahad.mustapha.myapp.ChooseDateActivity.EXTRA_START_DATE;


public class Result extends AppCompatActivity {
    public static final String TAG="Result";

    Date min_compagny, max_compagny;   // assume these are set to something
    Date dateStart, dateReturn;          // the date in question
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final ImageView img = findViewById(R.id.logo);

        Log.d(TAG,"Oncreate: Started");
        final ListView mListView = findViewById(R.id.listView);
        final ArrayList<Compagny> compagniesList = new ArrayList<>();

        // Toast.makeText(Result.this, "BEFORE REQUEST FLIGHTS", Toast.LENGTH_LONG).show();

        final RequestFlights flights = new RequestFlights();
        flights.getData("FLIGHTS_AVAILABLE",new FirebaseCallback() {
            @Override
            public void onCallback() {

                Log.e(TAG,flights.getLogo());

                //if(decodedByte!=null)
                //  img.setImageBitmap(decodedByte);

                String imageBase64 = flights.getLogo();
                String startD =getIntent().getStringExtra(EXTRA_START_DATE);
                String returnD =getIntent().getStringExtra(EXTRA_RETURN_DATE);
                String price = flights.getPrice();
                final Compagny easyjet = new Compagny(price,"Economique",startD,returnD,img, imageBase64);
                compagniesList.add(easyjet);

                final CompagnyListAdapter adapter = new CompagnyListAdapter(Result.this, R.layout.adapter_view_layout, compagniesList);


                mListView.setAdapter(adapter);

                mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView tagText =  view.findViewById(R.id.price);
                        String tag = tagText.getText().toString();
                        Toast.makeText(Result.this,"You selected : " + tag,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Toast.makeText(Result.this, "AFTER REQUEST FLIGHTS", Toast.LENGTH_LONG).show();






    }

    void dateAvailable(){
        if(dateStart.after(min_compagny) && dateReturn.before(max_compagny))
            Log.e("dateAvailable", "Between Date");

    }
}
