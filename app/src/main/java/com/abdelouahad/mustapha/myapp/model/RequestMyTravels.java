package com.abdelouahad.mustapha.myapp.model;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class RequestMyTravels {

    private String price;
    private String logo;
    private String start_date;
    private String return_date;
    private String id;
    private  String name;
    private ArrayList<RequestMyTravels> mList;
    private String tel;
    private String duration;
    private String start_airport;
    private String return_airport;
    private String start_hour;
    private String return_hour;
    private String toCountry;
    private String banner;
    private String classe;
    private String nbChildren;
    private String nbAdults;
    private String nbPassengers;
    private String nbEderly;
    private String rating;



    public RequestMyTravels(){

        mList = new ArrayList<>();
    }

    private RequestMyTravels(String price, String logo, String start_date, String return_date, String id, String name, String tel, String duration, String start_airport, String return_airport, String start_hour, String return_hour, String toCountry, String banner, String classe, String nbChildren, String nbAdults, String nbPassengers, String nbEderly, String rating) {
        this.price = price;
        this.logo = logo;
        this.start_date = start_date;
        this.return_date = return_date;
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.duration = duration;
        this.start_airport = start_airport;
        this.return_airport = return_airport;
        this.start_hour = start_hour;
        this.return_hour = return_hour;
        this.toCountry = toCountry;
        this.banner = banner;
        this.classe = classe;
        this.nbChildren = nbChildren;
        this.nbAdults = nbAdults;
        this.nbPassengers = nbPassengers;
        this.nbEderly = nbEderly;
        this.rating = rating;
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

    public String getStart_date() {
        return start_date;
    }

    private void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    private void setReturn_date(String return_date) {
        this.return_date = return_date;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void getData(String rootPath, final String name_compagny_target, final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @SuppressLint("LongLogTag")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value;
                    mList.clear();
                    for (DataSnapshot Uid : dataSnapshot.getChildren()) {
                        if (Objects.requireNonNull(Uid.getKey()).equals(user.getUid())) {
                            Log.e("ReqyestMyTravels",Uid.getKey());
                            Log.e("ReqyestMyTravels uid",user.getUid());

                            for (DataSnapshot myTravel : Uid.getChildren()) {
                                Log.e("ReqyestMyTravels myTravel",myTravel.getKey());

                                for (DataSnapshot id_ : myTravel.getChildren()) {
                                    Log.e("ReqyestMyTravels id_",id_.getKey());
                                    setId(id_.getKey());
                                    Log.e("ReqyestMyTravels id_.getKey()",getId());
                                    for (DataSnapshot compagny : id_.getChildren()) {
                                        Log.e("ReqyestMyTravels compagny",compagny.getKey());

                                        for (DataSnapshot compagnies : compagny.getChildren()) {
                                            Log.e("ReqyestMyTravels compagnies",compagnies.getKey());
                                            setName(compagnies.getKey());
                                            if(getName().equals(name_compagny_target)) {

                                                for (DataSnapshot values : compagnies.getChildren()) {
                                                    Log.e("ReqyestMyTravels  key ", values.getKey() + " value: " + values.getValue());
                                                    if (Objects.equals(values.getKey(), "Price")) {
                                                        Log.e("Price", Objects.requireNonNull(values.getValue()).toString());

                                                        value = String.valueOf(values.getValue());
                                                        setPrice(value);
                                                    } else if (Objects.equals(values.getKey(), "Logo")) {
                                                        value = String.valueOf(values.getValue());
                                                        setLogo(value);
                                                    } else if (Objects.equals(values.getKey(), "StartDate")) {
                                                        value = String.valueOf(values.getValue());
                                                        setStart_date(value);
                                                    } else if (Objects.equals(values.getKey(), "ReturnDate")) {
                                                        value = String.valueOf(values.getValue());
                                                        setReturn_date(value);
                                                    }  else if (Objects.equals(values.getKey(), "Rating")) {
                                                        value = String.valueOf(values.getValue());
                                                        setRating(value);
                                                    } else if (Objects.equals(values.getKey(), "StartHour")) {
                                                        value = String.valueOf(values.getValue());
                                                        setRating(value);
                                                    } else if (Objects.equals(values.getKey(), "ReturnHour")) {
                                                        value = String.valueOf(values.getValue());
                                                        setRating(value);
                                                    }else if (Objects.equals(values.getKey(), "Tel")) {
                                                        value = String.valueOf(values.getValue());
                                                        setTel(value);
                                                    }else if (Objects.equals(values.getKey(), "Duration")) {
                                                        value = String.valueOf(values.getValue());
                                                        setDuration(value);
                                                    }else if (Objects.equals(values.getKey(), "ToCountry")) {
                                                        value = String.valueOf(values.getValue());
                                                        setToCountry(value);
                                                    }

                                                }
                                                mList.add(new RequestMyTravels(getPrice(), getLogo(),
                                                        getStart_date(), getReturn_date(), getId(),
                                                        getName(), getTel(), getDuration(),
                                                        getStart_airport(), getReturn_airport(),
                                                        getStart_hour(), getReturn_hour(), getToCountry(),
                                                        getBanner(), getClasse(), getNbChildren(),
                                                        getNbAdults(), getNbPassengers(), getNbEderly(), getRating()));
                                            }
                                        }
                                    }
                                }
                            }
                            firebaseCallback.onCallback();
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

    public ArrayList<RequestMyTravels> getmList() {
        return mList;
    }

    public String getTel() {
        return tel;
    }

    private void setTel(String tel) {
        this.tel = tel;
    }

    public String getDuration() {
        return duration;
    }

    private void setDuration(String duration) {
        this.duration = duration;
    }

    private String getStart_airport() {
        return start_airport;
    }

    private String getReturn_airport() {
        return return_airport;
    }


    public String getStart_hour() {
        return start_hour;
    }


    public String getReturn_hour() {
        return return_hour;
    }




    private String getBanner() {
        return banner;
    }

    private String getClasse() {
        return classe;
    }

    private String getNbChildren() {
        return nbChildren;
    }

    private String getNbAdults() {
        return nbAdults;
    }

    private String getNbPassengers() {
        return nbPassengers;
    }

    private String getNbEderly() {
        return nbEderly;
    }

    private String getRating() {
        return rating;
    }

    private void setRating(String rating) {
        this.rating = rating;
    }

    public String getToCountry() {
        return toCountry;
    }

    private void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }
}
