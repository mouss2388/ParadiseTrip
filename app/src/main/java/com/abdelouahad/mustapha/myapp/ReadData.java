package com.abdelouahad.mustapha.myapp;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadData {

    private Boolean allData;
    private String label;
    private String country;
    private String description;
    private String banner;
    private String img_str_1;
    private String img_str_2;
    private String img_str_3;



    public ReadData() {
        this.allData=true;
    }

    public ReadData(String label){
        this.label=label;
        this.allData=false;

    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getAlldata() {
        return allData;
    }

    public void setLabel(Boolean allData) {
        this.allData = allData;
    }


    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_str_1() {
        return img_str_1;
    }

    public void setImg_str_1(String img_str_1) {
        this.img_str_1 = img_str_1;
    }

    public String getImg_str_2() {
        return img_str_2;
    }

    public void setImg_str_2(String img_str_2) {
        this.img_str_2 = img_str_2;
    }

    public String getImg_str_3() {
        return img_str_3;
    }

    public void setImg_str_3(String img_str_3) {
        this.img_str_3 = img_str_3;
    }


    public void getData(String rootPath,final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);



        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String key = ds.getKey();
                    String value = String.valueOf(ds.getValue());

                    if(getAlldata()){
                        switch (key){
                            case "Country":
                                setCountry(value);
                                break;
                            case "Banner":
                                setBanner(value);
                                break;
                            case "Description":
                                setDescription(value);
                                break;
                        }
                    }else{
                        switch (getLabel()){
                            case "Country":
                                if(key.equals(getLabel()))
                                    setCountry(value);
                                break;
                            case "Banner":
                                if(key.equals(getLabel()))
                                    setBanner(value);
                                break;
                            case "Description":
                                if(key.equals(getLabel()))
                                    setDescription(value);
                                break;
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


}
