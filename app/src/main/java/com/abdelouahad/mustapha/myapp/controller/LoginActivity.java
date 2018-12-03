package com.abdelouahad.mustapha.myapp.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private final String EMAIL_PATTERN ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;


    TextInputLayout passwordWrapper =null;
    TextInputLayout emailWrapper =null;

    String email,password;
    Button btn = null;

    public static String mPreferences ="PASSWORD",key_passwrd="password";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        //Cache l ActionBar
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();
       /* // add back arrow to toolbar
        if (getSupportActionBar() != null){
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(false);
            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }*/

        passwordWrapper =  findViewById(R.id.passwordWrapper);
        emailWrapper=  findViewById(R.id.emailWrapper);
        btn = findViewById(R.id.btnLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = String.valueOf(emailWrapper.getEditText().getText());
                password = String.valueOf(passwordWrapper.getEditText().getText());
                Toast.makeText(LoginActivity.this,
                        email +":"+password,
                        Toast.LENGTH_LONG).show();


               /* if (!validateEmail(email)) {
                    emailWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password!");
                } else {*/
                    emailWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    doLogin();
               // }
            }
        });

    }

    public void doLogin() {
        email="colomb1506@yopmail.com";
        password="passw0rd";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setSavePassword(password);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginAcitivty", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Success! "+user.getEmail()+" : "+password, Toast.LENGTH_SHORT).show();
                            if(user.isEmailVerified()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                            }else {
                                Toast.makeText(getApplicationContext(), "You must verify your Email!", Toast.LENGTH_SHORT).show();

                            }
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginAcitivty", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed. Email or Password unknow ",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
        }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void forgotPassword(View v){
        Toast.makeText(getApplicationContext(), "OK! forgotPasswd.", Toast.LENGTH_SHORT).show();
        Intent  intent = new Intent(LoginActivity.this, ResetPsswordActivity.class);
        this.startActivity(intent);
    }

    public void register(View v){
        Toast.makeText(getApplicationContext(), "OK! register", Toast.LENGTH_SHORT).show();
        Intent  intent = new Intent(LoginActivity.this, RegisterActivity.class);
        this.startActivity(intent);

    }

    public  void setSavePassword(String password){
      //  SharedPreferences pref = this.getApplicationContext().
        SharedPreferences pref = getApplicationContext().getSharedPreferences(mPreferences, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key_passwrd, password);
        editor.apply();
    }
}

