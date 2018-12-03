package com.abdelouahad.mustapha.myapp.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class RequestDataTravel {

    private String banner;
    private String img_str_1,img_str_2,img_str_3;
    private String country;
    private String description;

    private Boolean boolAllData;
    private ArrayList<String> label;

    private int id;
    private int extractData;
    private String price;
    private int rate;

    public RequestDataTravel() {
        this.boolAllData=true;
    }



    public RequestDataTravel(String label){
        this.label=splitRequest(label);
        this.boolAllData=false;
    }



    public void getData(String rootPath, final FirebaseCallback firebaseCallback) {
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

                    if(boolAllData){
                        getAllData(key,value);

                    }else{
                        Log.e("RequestDataTravel ", "HERE");
                        getSomeData(key,value);
                        if(getExtractData()<=0)
                            break;
                    }
                }
                firebaseCallback.onCallback();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getSomeData(String key, String value){
        for (String field: getLabel()){
            Log.e("RequestDataTravel In", field);

            switch (field) {

                case "Id":
                    assert key != null;
                    if (key.equals(field)) {
                        setId(Integer.parseInt(value));
                        Log.e("RequestDataTravel Id", String.valueOf(getId()));
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Country":
                    assert key != null;
                    if (key.equals(field)) {
                        setCountry(value);
                        Log.e("RequestDataTravel ", getCountry());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Banner":
                    assert key != null;
                    if (key.equals(field)) {
                        setBanner(value);
                        Log.e("RequestDataTravel", getBanner());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Description":
                    assert key != null;
                    if (key.equals(field)) {
                        setDescription(value);
                        Log.e("RequestDataTravel", getDescription());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Price":
                    assert key != null;
                    if (key.equals(field)) {
                        setPrice(value);
                        Log.e("RequestDataTravel price", getPrice());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Rate":
                    assert key != null;
                    if (key.equals(field)) {
                        setRate(Integer.parseInt(value));
                        Log.e("RequestDataTravel Rate", String.valueOf(getRate()));
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Img_1":
                    assert key != null;
                    if (key.equals(field)) {
                        setImg_str_1(value);
                        Log.e("RequestDataTravel Img_1", getImg_str_1());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Img_2":
                    assert key != null;
                    if (key.equals(field)) {
                        setImg_str_2(value);
                        Log.e("RequestDataTravel Img_2", getImg_str_2());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Img_3":
                    assert key != null;
                    if (key.equals(field)) {
                        setImg_str_3(value);
                        Log.e("RequestDataTravel Img_3", getImg_str_3());
                        setExtractData(getExtractData()-1);
                    }
                    break;
            }
        }
    }

    public void getAllData(String key,String value){
        assert key != null;
        switch (key) {
            case "Id":
                setId(Integer.parseInt(value));
                break;
            case "Country":
                setCountry(value);
                break;
            case "Banner":
                setBanner(value);
                break;
            case "Img_1":
                setImg_str_1(value);
                break;
            case "Img_2":
                setImg_str_2(value);
                break;
            case "Img_3":
                setImg_str_3(value);
                break;
            case "Description":
                setDescription(value);
                break;
            case "Rate":
                setRate(Integer.parseInt(value));
                break;
            case "Price":
                setPrice(value);
                break;
        }

    }

    public ArrayList<String> splitRequest(String request){

        ArrayList<String> arrayReq= new ArrayList<>();
        if(request.contains("+")) {
            String[] req = request.split("\\+");
            Collections.addAll(arrayReq, req);
        }else{
            arrayReq.add(request);
        }
        setExtractData(arrayReq.size());
        return arrayReq;
    }


    public int getExtractData() {
        return extractData;
    }

    public void setExtractData(int extractData) {
        this.extractData = extractData;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getLabel() {
        return label;
    }

    public void setLabel(ArrayList<String> label) {
        this.label = label;
    }

    public Boolean getBoolAllData() {
        return boolAllData;
    }

    public void setLabel(Boolean boolAllData) {
        this.boolAllData = boolAllData;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
