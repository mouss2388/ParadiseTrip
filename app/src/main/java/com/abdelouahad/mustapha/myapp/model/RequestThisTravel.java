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

import java.util.Objects;

public class RequestThisTravel {
    private String idCountry;
    private String banner;
    private String classe;
    private String duration;
    private String flagCountry;
    private  String logoCompagny;
    private String nbAdults;
    private String nbChildren;
    private String nbElderly;
    private String nbPassengers;
    private String price;
    private String rating;
    private String returnAirport;
    private String returnDate;
    private String returnHour;
    private String startAirport;
    private String startDate;
    private String startHour;
    private String tel;
    private String country;
    private  String nameCopagny;


    public RequestThisTravel() {

    }

    public RequestThisTravel(String idCountry, String banner, String classe, String duration, String flagCountry, String logoCompagny, String nbAdults, String nbChildren, String nbElderly, String nbPassengers, String price, String rating, String returnAirport, String returnDate, String returnHour, String startAirport, String startDate, String startHour, String tel, String country) {
        this.idCountry = idCountry;
        this.banner = banner;
        this.classe = classe;
        this.duration = duration;
        this.flagCountry = flagCountry;
        this.logoCompagny = logoCompagny;
        this.nbAdults = nbAdults;
        this.nbChildren = nbChildren;
        this.nbElderly = nbElderly;
        this.nbPassengers = nbPassengers;
        this.price = price;
        this.rating = rating;
        this.returnAirport = returnAirport;
        this.returnDate = returnDate;
        this.returnHour = returnHour;
        this.startAirport = startAirport;
        this.startDate = startDate;
        this.startHour = startHour;
        this.tel = tel;
        this.country = country;
    }

    public  void getImages(String rootPath, final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Boolean[] findFlag = {false};
        final Boolean[] findCountryImg = {false};
        if (user != null) {
            // Read from the database
            Log.e("TEST","BEFORE");
            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("TEST","BEFORE_1");

                    // whenever data at this location is updated.

                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                       String key = String.valueOf(ds.getKey());
                       if(key.equals("Banner")){
                           String value = String.valueOf(ds.getValue());
                           setBanner(value);
                           findCountryImg[0] =true;
                       }else if(key.equals("Flag")){
                           String value = String.valueOf(ds.getValue());
                           findFlag[0] =true;
                           setFlagCountry(value);
                       }else  if(findFlag[0] && findCountryImg[0])
                           break;


                    }
                    Log.e("TEST","AFTER");


                    firebaseCallback.onCallback();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void getData(String rootPath, final String idTarget, final String nameCompagnyTarget, final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final Boolean[] compagnyFind = {false};
        if (user != null) {
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @SuppressLint("LongLogTag")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value;
                    for (DataSnapshot Uid : dataSnapshot.getChildren()) {
                        if (Objects.requireNonNull(Uid.getKey()).equals(user.getUid())) {
                            Log.e("RequestThisTravel",Uid.getKey());
                            Log.e("RequestThisTravel uid",user.getUid());

                            for (DataSnapshot myTravel : Uid.getChildren()) {
                                Log.e("RequestThisTravel myTravel",myTravel.getKey());

                                for (DataSnapshot id : myTravel.getChildren()) {
                                    Log.e("RequestThisTravel id_",id.getKey());
                                    Log.e("RequestThisTravel idTarget",idTarget);

                                    if(id.getKey().equals(idTarget)) {

                                        setIdCountry(id.getKey());

                                        for (DataSnapshot compagny : id.getChildren()) {
                                            Log.e("RequestThisTravel compagny.key",compagny.getKey());


                                            for (DataSnapshot compagnies : compagny.getChildren()) {
                                                if (compagnies.getKey().equals(nameCompagnyTarget)) {
                                                    Log.e("RequestThisTravel compagnies", compagnies.getKey());

                                                    for (DataSnapshot values : compagnies.getChildren()) {
                                                        if (Objects.equals(values.getKey(), "Banner")) {
                                                            value = String.valueOf(values.getValue());
                                                            setBanner(value);
                                                        } else if (Objects.equals(values.getKey(), "Class")) {
                                                            value = String.valueOf(values.getValue());
                                                            setClasse(value);
                                                        } else if (Objects.equals(values.getKey(), "Duration")) {
                                                            value = String.valueOf(values.getValue());
                                                            setDuration(value);
                                                        } else if (Objects.equals(values.getKey(), "Flag")) {
                                                            value = String.valueOf(values.getValue());
                                                            setFlagCountry(value);
                                                        } else if (Objects.equals(values.getKey(), "Logo")) {
                                                            value = String.valueOf(values.getValue());
                                                            setLogoCompagny(value);
                                                        } else if (Objects.equals(values.getKey(), "NbAdults")) {
                                                            value = String.valueOf(values.getValue());
                                                            setNbAdults(value);
                                                        } else if (Objects.equals(values.getKey(), "NbChildren")) {
                                                            value = String.valueOf(values.getValue());
                                                            setNbChildren(value);
                                                        } else if (Objects.equals(values.getKey(), "NbElderly")) {
                                                            value = String.valueOf(values.getValue());
                                                            setNbElderly(value);
                                                        } else if (Objects.equals(values.getKey(), "NbPassengers")) {
                                                            value = String.valueOf(values.getValue());
                                                            setNbPassengers(value);
                                                        } else if (Objects.equals(values.getKey(), "Price")) {
                                                            value = String.valueOf(values.getValue());
                                                            setPrice(value);
                                                        }else if (Objects.equals(values.getKey(), "Rating")) {
                                                            value = String.valueOf(values.getValue());
                                                            setRating(value);
                                                        }else if (Objects.equals(values.getKey(), "ReturnAirport")) {
                                                            value = String.valueOf(values.getValue());
                                                            setReturnAirport(value);
                                                        }else if (Objects.equals(values.getKey(), "ReturnDate")) {
                                                            value = String.valueOf(values.getValue());
                                                            setReturnDate(value);
                                                        }else if (Objects.equals(values.getKey(), "ReturnHour")) {
                                                            value = String.valueOf(values.getValue());
                                                            setReturnHour(value);
                                                        }else if (Objects.equals(values.getKey(), "StartAirport")) {
                                                            value = String.valueOf(values.getValue());
                                                            setStartAirport(value);
                                                        }else if (Objects.equals(values.getKey(), "StartDate")) {
                                                            value = String.valueOf(values.getValue());
                                                            setStartDate(value);
                                                        }else if (Objects.equals(values.getKey(), "StartHour")) {
                                                            value = String.valueOf(values.getValue());
                                                            setStartHour(value);
                                                        }else if (Objects.equals(values.getKey(), "Tel")) {
                                                            value = String.valueOf(values.getValue());
                                                            setTel(value);
                                                        }else{
                                                            value = String.valueOf(values.getValue());
                                                            setCountry(value);
                                                        }

                                                    }
                                                    compagnyFind[0] =true;
                                                    if(compagnyFind[0])
                                                        break;
                                                }
                                            }
                                            if(compagnyFind[0])
                                                break;
                                        }
                                    }
                                    if(compagnyFind[0])
                                        break;
                                }
                                if(compagnyFind[0])
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFlagCountry() {
        return flagCountry;
    }

    public void setFlagCountry(String flagCountry) {
        this.flagCountry = flagCountry;
    }

    public String getLogoCompagny() {
        return logoCompagny;
    }

    public void setLogoCompagny(String logoCompagny) {
        this.logoCompagny = logoCompagny;
    }

    public String getNbAdults() {
        return nbAdults;
    }

    public void setNbAdults(String nbAdults) {
        this.nbAdults = nbAdults;
    }

    public String getNbChildren() {
        return nbChildren;
    }

    public void setNbChildren(String nbChildren) {
        this.nbChildren = nbChildren;
    }

    public String getNbElderly() {
        return nbElderly;
    }

    public void setNbElderly(String nbElderly) {
        this.nbElderly = nbElderly;
    }

    public String getNbPassengers() {
        return nbPassengers;
    }

    public void setNbPassengers(String nbPassengers) {
        this.nbPassengers = nbPassengers;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReturnAirport() {
        return returnAirport;
    }

    public void setReturnAirport(String returnAirport) {
        this.returnAirport = returnAirport;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnHour() {
        return returnHour;
    }

    public void setReturnHour(String returnHour) {
        this.returnHour = returnHour;
    }

    public String getStartAirport() {
        return startAirport;
    }

    public void setStartAirport(String startAirport) {
        this.startAirport = startAirport;
    }



    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNameCopagny() {
        return nameCopagny;
    }

    public void setNameCopagny(String nameCopagny) {
        this.nameCopagny = nameCopagny;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }
}
