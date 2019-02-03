package com.abdelouahad.mustapha.myapp.model;

import android.annotation.SuppressLint;
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

public class RequestPromotions {

    private String img;

    public RequestPromotions() {
    }

    public String getImg() {
        return img;
    }

    private void setImg(String img) {
        this.img = img;
    }



    public void getData(String rootPath, final int indx, final FirebaseCallback firebaseCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(rootPath);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.e("Promotions Img", "Before");

                for (DataSnapshot image : dataSnapshot.getChildren()) {
                    Log.e("Promotions Img", image.getKey() +" : "+image.getValue());
                    if(Objects.requireNonNull(image.getKey()).contains(String.valueOf(indx))) {
                        setImg(String.valueOf(image.getValue()));
                        break;
                    }
                }
                Log.e("Promotions Img ", "After");

                firebaseCallback.onCallback();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }
}

