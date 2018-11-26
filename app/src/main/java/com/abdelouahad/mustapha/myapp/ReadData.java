package com.abdelouahad.mustapha.myapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ReadData {

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

    public ReadData() {
        this.boolAllData=true;
    }



    public ReadData(String label){
        this.label=splitRequest(label);
        this.boolAllData=false;
    }



    protected void getData(String rootPath,final FirebaseCallback firebaseCallback) {
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
                        Log.e("ReadData Before", "HERE");
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

    private void getSomeData(String key, String value){
        for (String field: getLabel()){
            Log.e("ReadData In", field);

            switch (field) {

                case "Id":
                    assert key != null;
                    if (key.equals(field)) {
                        setId(Integer.parseInt(value));
                        Log.e("ReadData Id", String.valueOf(getId()));
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Country":
                    assert key != null;
                    if (key.equals(field)) {
                        setCountry(value);
                        Log.e("ReadData Country", getCountry());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Banner":
                    assert key != null;
                    if (key.equals(field)) {
                        setBanner(value);
                        Log.e("ReadData getBanner", getBanner());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Description":
                    assert key != null;
                    if (key.equals(field)) {
                        setDescription(value);
                        Log.e("ReadData getDescription", getDescription());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Price":
                    assert key != null;
                    if (key.equals(field)) {
                        setPrice(value);
                        Log.e("ReadData price", getPrice());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Rate":
                    assert key != null;
                    if (key.equals(field)) {
                        setRate(Integer.parseInt(value));
                        Log.e("ReadData Rate", String.valueOf(getRate()));
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Img_1":
                    assert key != null;
                    if (key.equals(field)) {
                        setImg_str_1(value);
                        Log.e("ReadData Img_1", getImg_str_1());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Img_2":
                    assert key != null;
                    if (key.equals(field)) {
                        setImg_str_2(value);
                        Log.e("ReadData Img_2", getImg_str_2());
                        setExtractData(getExtractData()-1);
                    }
                    break;
                case "Img_3":
                    assert key != null;
                    if (key.equals(field)) {
                        setImg_str_3(value);
                        Log.e("ReadData Img_3", getImg_str_3());
                        setExtractData(getExtractData()-1);
                    }
                    break;
            }
        }
    }

    protected void getAllData(String key,String value){
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

    protected ArrayList<String> splitRequest(String request){

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


    protected int getExtractData() {
        return extractData;
    }

    protected void setExtractData(int extractData) {
        this.extractData = extractData;
    }

    protected String getCountry() {
        return country;
    }

    protected void setCountry(String country) {
        this.country = country;
    }

    protected ArrayList<String> getLabel() {
        return label;
    }

    protected void setLabel(ArrayList<String> label) {
        this.label = label;
    }

    protected Boolean getBoolAllData() {
        return boolAllData;
    }

    protected void setLabel(Boolean boolAllData) {
        this.boolAllData = boolAllData;
    }


    protected String getBanner() {
        return banner;
    }

    protected void setBanner(String banner) {
        this.banner = banner;
    }

    protected String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected String getImg_str_1() {
        return img_str_1;
    }

    protected void setImg_str_1(String img_str_1) {
        this.img_str_1 = img_str_1;
    }

    protected String getImg_str_2() {
        return img_str_2;
    }

    protected void setImg_str_2(String img_str_2) {
        this.img_str_2 = img_str_2;
    }

    protected String getImg_str_3() {
        return img_str_3;
    }

    protected void setImg_str_3(String img_str_3) {
        this.img_str_3 = img_str_3;
    }

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected String getPrice() {
        return price;
    }

    protected void setPrice(String price) {
        this.price = price;
    }

    protected int getRate() {
        return rate;
    }

    protected void setRate(int rate) {
        this.rate = rate;
    }
}
