package com.abdelouahad.mustapha.myapp.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestFlights {

    private String price;
    private String logo;
    private String startDate;
    private String returnDate;
    private String id;
    private String name_compagny;
    private String destination;



    public RequestFlights(String id){
        this.id=id;
    }


    public void getData(String rootPath, final String compagny, final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value;

                for (DataSnapshot compagny_flight: dataSnapshot.getChildren()) {
                    if(compagny_flight.getKey().equals(compagny)) {
                        value = compagny_flight.getKey();
                        setName_compagny(value);
                        Log.i("RequestFlights", getName_compagny());
                        for (DataSnapshot data : compagny_flight.getChildren()) {
                            if (data.getKey().equals("Price")) {
                                value = String.valueOf(data.getValue());
                                setPrice(value);
                            } else if (data.getKey().equals("Logo")) {
                                value = String.valueOf(data.getValue());
                                setLogo(value);
                            }
                        }
                    }

                }
                firebaseCallback.onCallback();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getstartDate() {
        return startDate;
    }

    public void setstartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getreturnDate() {
        return returnDate;
    }

    public void setreturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_compagny() {
        return name_compagny;
    }

    public void setName_compagny(String name_compagny) {
        this.name_compagny = name_compagny;
    }
}
