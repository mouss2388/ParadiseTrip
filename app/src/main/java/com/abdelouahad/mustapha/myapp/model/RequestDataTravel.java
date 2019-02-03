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
    private ArrayList<String> airports;

    private int id;
    private int numberExtractData;
    private String price;
    private int rate;
    private String flag;



    public RequestDataTravel() {
        this.boolAllData=true;
        this.airports=new ArrayList<>();
    }



    public RequestDataTravel(String label){
        this.label=splitRequest(label);
        this.airports=new ArrayList<>();
        this.boolAllData=false;
    }



    public void getData(String rootPath, final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);



        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String key;
                    key = ds.getKey();
                    String value = String.valueOf(ds.getValue());

                    if(boolAllData){
                        assert key != null;
                        getAllData(key,value);

                    }else{

                        //Mettre le for ici pour optimiser
                        Log.e("RequestDataTravel ", "HERE");
                        getSomeData(key,value);
                        if(getNumberExtractData()<=0)
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
           // Log.e("RequestDataTravel In", field);
            if(key.contains("airport_"))
                Log.e("Request airport", key+" "+value);

            switch (field) {

                case "Id":
                    if (key.equals(field)) {
                        setId(Integer.parseInt(value));
                        Log.e("RequestDataTravel Id", String.valueOf(getId()));
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;
                case "Country":
                    if (key.equals(field)) {
                        setCountry(value);
                        Log.e("RequestDataTravel ", getCountry());
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;
                case "Banner":
                    if (key.equals(field)) {
                        setBanner(value);
                        Log.e("RequestDataTravel", getBanner());
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;
                case "Description":
                    if (key.equals(field)) {
                        setDescription(value);
                        Log.e("RequestDataTravel", getDescription());
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;
                case "Price":
                    if (key.equals(field)) {
                        setPrice(value);
                        Log.e("RequestDataTravel price", getPrice());
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;
                case "Rate":
                    if (key.equals(field)) {
                        setRate(Integer.parseInt(value));
                        Log.e("RequestDataTravel Rate", String.valueOf(getRate()));
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;
                case "Img_1":
                    if (key.equals(field)) {
                        setImg_str_1(value);
                        Log.e("RequestDataTravel Img_1", getImg_str_1());
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;
                case "Img_2":
                    if (key.equals(field)) {
                        setImg_str_2(value);
                        Log.e("RequestDataTravel Img_2", getImg_str_2());
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;
                case "Img_3":
                    if (key.equals(field)) {
                        setImg_str_3(value);
                        Log.e("RequestDataTravel Img_3", getImg_str_3());
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;

                case "Airports":
                    if(key.contains("airport_")) {
                        this.airports.add(value);
                    }
                    break;
                case "Flag":
                    if (key.equals(field)) {
                        setFlag(value);
                        Log.e("RequestDataTravel flag", getFlag());
                        setNumberExtractData(getNumberExtractData()-1);
                    }
                    break;
            }
        }
    }

    private void getAllData(String key, String value){
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
            case "Flag":
                setFlag(value);
                break;
        }

    }

    private ArrayList<String> splitRequest(String request){

        ArrayList<String> arrayReq= new ArrayList<>();
        if(request.contains("+")) {
            String[] req = request.split("\\+");
            Collections.addAll(arrayReq, req);
        }else{
            arrayReq.add(request);
        }
        setNumberExtractData(arrayReq.size());
        return arrayReq;
    }


    private int getNumberExtractData() {
        return numberExtractData;
    }

    private void setNumberExtractData(int numberExtractData) {
        this.numberExtractData = numberExtractData;
    }

    public String getCountry() {
        return country;
    }

    private void setCountry(String country) {
        this.country = country;
    }

    private ArrayList<String> getLabel() {
        return label;
    }




    public String getBanner() {
        return banner;
    }

    private void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getImg_str_1() {
        return img_str_1;
    }

    private void setImg_str_1(String img_str_1) {
        this.img_str_1 = img_str_1;
    }

    public String getImg_str_2() {
        return img_str_2;
    }

    private void setImg_str_2(String img_str_2) {
        this.img_str_2 = img_str_2;
    }

    public String getImg_str_3() {
        return img_str_3;
    }

    private void setImg_str_3(String img_str_3) {
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

    private void setRate(int rate) {
        this.rate = rate;
    }

    public ArrayList<String> getAirports() {
        return airports;
    }

    public String getFlag() {
        return flag;
    }

    private void setFlag(String flag) {
        this.flag = flag;
    }
}
