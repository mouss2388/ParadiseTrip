package com.abdelouahad.mustapha.myapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestMyTravels {

    private String price;
    private String logo;
    private String start_date;
    private String return_date;
    private String id;
    private  String name;


    public RequestMyTravels(){

    }

    public RequestMyTravels(String id){
        this.id=id;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected void getData(String rootPath, final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);
        String value="";
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value;

                    for (DataSnapshot Uid : dataSnapshot.getChildren()) {
                        if (Uid.getKey().equals(user.getUid())) {
                            Log.e("ReqyestMyTravels",Uid.getKey());
                            Log.e("ReqyestMyTravels uid",user.getUid());

                            for (DataSnapshot myTravel : Uid.getChildren()) {
                                for (DataSnapshot id_ : myTravel.getChildren()) {
                                    setId(id_.getKey());
                                    for (DataSnapshot compagny : id_.getChildren()) {
                                        Log.e("compagny",compagny.getKey());
                                        for (DataSnapshot compagnies : compagny.getChildren()) {
                                            for (DataSnapshot values : compagnies.getChildren()) {
                                                if (values.getKey().equals("PRICE")) {
                                                    Log.e("PRICE", values.getValue().toString());

                                                    value = String.valueOf(values.getValue());
                                                    setPrice(value);
                                                } else if (values.getKey().equals("Logo_B64")) {
                                                    value = String.valueOf(values.getValue());
                                                    setLogo(value);
                                                } else if (values.getKey().equals("START_DATE")) {
                                                    value = String.valueOf(values.getValue());
                                                    setStart_date(value);
                                                } else if (values.getKey().equals("RETURN_DATE")) {
                                                    value = String.valueOf(values.getValue());
                                                    setReturn_date(value);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            firebaseCallback.onCallback();
                            break;
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
