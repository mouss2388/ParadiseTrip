package com.abdelouahad.mustapha.myapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    private Spinner civility;
    private TextInputEditText name;
    private TextInputEditText firstname;
    private TextInputEditText email;
    private TextInputEditText password;
    private Button register, delete;

    String mEmail = "colomb1506@yopmail.com";
    String mPassword = "passw0rd";
    String nom;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        civility = findViewById(R.id.civility);
        name = findViewById(R.id.name_user);
        firstname = findViewById(R.id.firstname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        delete = findViewById(R.id.delete);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.delete();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this,
                        "Register: " + civility.getSelectedItem().toString() + " " +
                                name.getText() + " " +
                                firstname.getText() + " " +
                                email.getText() + " " +
                                password.getText() + " "
                        ,
                        Toast.LENGTH_LONG).show();
                nom = name.getText().toString();
                createAccount();


            }
        });


    }

    private void createAccount() {

        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "createUserWithEmail:success",
                                    Toast.LENGTH_SHORT).show();


                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
        user = mAuth.getCurrentUser();
        //testUserExists();
        setDisplayName();
    }


    private void setDisplayName() {

            mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("LoginAcitivty", "signInWithEmail:success");
                                final FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "Success! "+user.getEmail()+" : "+password, Toast.LENGTH_SHORT).show();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName("My name test").build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "UPDATED",
                                                            Toast.LENGTH_SHORT).show();
                                                    getInfoAccount(user);
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, "UPDATED FAILED",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("LoginAcitivty", "signInWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }



    private void getInfoAccount(FirebaseUser user){
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            Toast.makeText(this,name+" : "+email+" : "+emailVerified,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"User UNKNOW",Toast.LENGTH_LONG).show();

        }
    }

    private void testUserExists(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(this,"User sign In",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"User No signed",Toast.LENGTH_LONG).show();
        }

    }
}
