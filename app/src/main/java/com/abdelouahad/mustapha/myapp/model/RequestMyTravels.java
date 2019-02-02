package com.abdelouahad.mustapha.myapp.model;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    private String to_country;
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

    public RequestMyTravels(String price, String logo, String start_date, String return_date, String id, String name, String tel, String duration, String start_airport, String return_airport, String start_hour, String return_hour, String to_country, String banner, String classe, String nbChildren, String nbAdults, String nbPassengers, String nbEderly, String rating) {
        this.price = price;
        this.logo = logo;
        this.start_date = start_date;
        this.return_date = return_date;
        this.id = id;
        this.name = name;
        this.mList = mList;
        this.tel = tel;
        this.duration = duration;
        this.start_airport = start_airport;
        this.return_airport = return_airport;
        this.start_hour = start_hour;
        this.return_hour = return_hour;
        this.to_country = to_country;
        this.banner = banner;
        this.classe = classe;
        this.nbChildren = nbChildren;
        this.nbAdults = nbAdults;
        this.nbPassengers = nbPassengers;
        this.nbEderly = nbEderly;
        this.rating = rating;
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

    public void getData(String rootPath, final String name_compagny_target, final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);
        String value="";
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint("LongLogTag")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value;
                    mList.clear();
                    for (DataSnapshot Uid : dataSnapshot.getChildren()) {
                        if (Uid.getKey().equals(user.getUid())) {
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
                                                    if (values.getKey().equals("Price")) {
                                                        Log.e("Price", values.getValue().toString());

                                                        value = String.valueOf(values.getValue());
                                                        setPrice(value);
                                                    } else if (values.getKey().equals("Logo")) {
                                                        value = String.valueOf(values.getValue());
                                                        setLogo(value);
                                                    } else if (values.getKey().equals("StartDate")) {
                                                        value = String.valueOf(values.getValue());
                                                        setStart_date(value);
                                                    } else if (values.getKey().equals("ReturnDate")) {
                                                        value = String.valueOf(values.getValue());
                                                        setReturn_date(value);
                                                    }  else if (values.getKey().equals("Rating")) {
                                                        value = String.valueOf(values.getValue());
                                                        setRating(value);
                                                    } else if (values.getKey().equals("StartHour")) {
                                                        value = String.valueOf(values.getValue());
                                                        setRating(value);
                                                    } else if (values.getKey().equals("ReturnHour")) {
                                                        value = String.valueOf(values.getValue());
                                                        setRating(value);
                                                    }else if (values.getKey().equals("Tel")) {
                                                        value = String.valueOf(values.getValue());
                                                        setTel(value);
                                                    }else if (values.getKey().equals("Duration")) {
                                                        value = String.valueOf(values.getValue());
                                                        setDuration(value);
                                                    }else if (values.getKey().equals("ToCountry")) {
                                                        value = String.valueOf(values.getValue());
                                                        setTo_country(value);
                                                    }

                                                }
                                                mList.add(new RequestMyTravels(getPrice(), getLogo(),
                                                        getStart_date(), getReturn_date(), getId(),
                                                        getName(), getTel(), getDuration(),
                                                        getStart_airport(), getReturn_airport(),
                                                        getStart_hour(), getReturn_hour(),getTo_country(),
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

    public void setmList(ArrayList<RequestMyTravels> mList) {
        this.mList = mList;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStart_airport() {
        return start_airport;
    }

    public void setStart_airport(String start_airport) {
        this.start_airport = start_airport;
    }

    public String getReturn_airport() {
        return return_airport;
    }

    public void setReturn_airport(String return_airport) {
        this.return_airport = return_airport;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getReturn_hour() {
        return return_hour;
    }

    public void setReturn_hour(String return_hour) {
        this.return_hour = return_hour;
    }



    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNbChildren() {
        return nbChildren;
    }

    public void setNbChildren(String nbChildren) {
        this.nbChildren = nbChildren;
    }

    public String getNbAdults() {
        return nbAdults;
    }

    public void setNbAdults(String nbAdults) {
        this.nbAdults = nbAdults;
    }

    public String getNbPassengers() {
        return nbPassengers;
    }

    public void setNbPassengers(String nbPassengers) {
        this.nbPassengers = nbPassengers;
    }

    public String getNbEderly() {
        return nbEderly;
    }

    public void setNbEderly(String nbEderly) {
        this.nbEderly = nbEderly;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTo_country() {
        return to_country;
    }

    public void setTo_country(String to_country) {
        this.to_country = to_country;
    }
}
