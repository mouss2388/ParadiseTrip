package com.abdelouahad.mustapha.myapp.controller;

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

import com.abdelouahad.mustapha.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
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

    String mEmail = "colomb1451@yopmail.com";
    String mPassword = "passw0rd";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        civility = findViewById(R.id.civility);
        name = findViewById(R.id.name);
        firstname = findViewById(R.id.firstname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        delete = findViewById(R.id.delete);





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
                createAccount();


            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }

    private void deleteUser(){
        AuthCredential credential = EmailAuthProvider
                .getCredential("colomb1451@yopmail.com", "passw0rd");

        final FirebaseUser user = mAuth.getCurrentUser();

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RegisterActivity.this, "User reauthenticate ",
                                Toast.LENGTH_SHORT).show();

                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "User reauthenticate ",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });

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
        testUserExists();
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

                            String fullName = firstname.getText()+" "+name.getText();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fullName).build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "UPDATED",
                                                        Toast.LENGTH_SHORT).show();
//                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                if(!user.isEmailVerified())
                                                    user.sendEmailVerification();

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
