package com.abdelouahad.mustapha.myapp.model;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RequestFlights {

    private String price;
    private String logo;
    private String id;
    private String name_compagny;
    private String destination;
    private String startHour;
    private String returnHour;
    private String tel;
    private String duration;



    public RequestFlights(String id){
        this.id=id;
    }


    public void getData(String rootPath, final String compagny, final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value;

                for (DataSnapshot compagny_flight: dataSnapshot.getChildren()) {
                    assert compagny!=null;
                    if(Objects.equals(compagny_flight.getKey(), compagny)) {
                        value = compagny_flight.getKey();
                        setName_compagny(value);
                        Log.i("RequestFlights", getName_compagny());
                        for (DataSnapshot data : compagny_flight.getChildren()) {
                            if (Objects.equals(data.getKey(), "Price")) {
                                value = String.valueOf(data.getValue());
                                setPrice(value);
                            } else if (Objects.equals(data.getKey(), "Logo")) {
                                value = String.valueOf(data.getValue());
                                setLogo(value);
                            }else if (Objects.equals(data.getKey(), "ReturnHour")) {
                                value = String.valueOf(data.getValue());
                                setReturnHour(value);
                            }else if (Objects.equals(data.getKey(), "StartHour")) {
                                value = String.valueOf(data.getValue());
                                setStartHour(value);
                            }else if (Objects.equals(data.getKey(), "Tel")) {
                                value = String.valueOf(data.getValue());
                                setTel(value);
                            }else if (Objects.equals(data.getKey(), "Duration")) {
                                value = String.valueOf(data.getValue());
                                setDuration(value);
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

    private void setLogo(String logo) {
        this.logo = logo;
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

    private void setName_compagny(String name_compagny) {
        this.name_compagny = name_compagny;
    }

    public String getStartHour() {
        return startHour;
    }

    private void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getReturnHour() {
        return returnHour;
    }

    private void setReturnHour(String returnHour) {
        this.returnHour = returnHour;
    }

    public String getDuration() {
        return duration;
    }

    private void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTel() {
        return tel;
    }

    private void setTel(String tel) {
        this.tel = tel;
    }
}
