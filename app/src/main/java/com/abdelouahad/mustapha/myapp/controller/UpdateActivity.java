package com.abdelouahad.mustapha.myapp.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.abdelouahad.mustapha.myapp.controller.LoginActivity.key_passwrd;
import static com.abdelouahad.mustapha.myapp.controller.LoginActivity.mPreferences;



public class UpdateActivity extends AppCompatActivity {

    private final String EMAIL_PATTERN ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private EditText name,firstname,email,password;
    private Button btn_update,btn_delete;
    SharedPreferences prefs;
    private String restoredPasswrd;


    TextInputLayout nameWrapper, firstnameWrapper,emailWrapper,passwordWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name = findViewById(R.id.name);
        firstname = findViewById(R.id.firstname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_update = findViewById(R.id.btn_update);
        btn_delete= findViewById(R.id.btn_delete);

        nameWrapper = findViewById(R.id.nameWrapper);
        firstnameWrapper = findViewById(R.id.firstnameWrapper);
        passwordWrapper = findViewById(R.id.passwordWrapper);
        emailWrapper = findViewById(R.id.emailWrapper);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            String title = getResources().getString(R.string.account);
            actionbar.setTitle(title);
            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }



        userSignIn();

    }


    private void deleteUser(final FirebaseUser user){
        String email = user.getEmail();
        String psswrd = restoredPasswrd;
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, psswrd);


        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UpdateActivity.this, "User reauthenticate ",
                                Toast.LENGTH_SHORT).show();

                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(UpdateActivity.this, "User Account deleted ",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent intent = new Intent(UpdateActivity.this,LoginActivity.class);
                                            UpdateActivity.this.startActivity(intent);

                                        }
                                    }
                                });
                    }
                });
    }

    private void userSignIn(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String[] data_name = user.getDisplayName().split(" ");
            if(data_name.length%2==0){
                firstname.setText(data_name[0]);
                name.setText(data_name[1]);
            }
            email.setText(user.getEmail());


            if(restoredPasswrd != null)
                password.setText(restoredPasswrd);
            else
                password.setText("DEFAULT");

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUI(user);
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUser(user);
                }
            });

        } else {
            Toast.makeText(this,"User No signed",Toast.LENGTH_LONG).show();
        }
    }

    private void updateUI(FirebaseUser user){
        // Name, email address, and profile photo Url
        String emailUpdate =email.getText().toString();
        String nameUpdate =firstname.getText().toString()+" "+name.getText().toString();
        String passwdUpdate = password.getText().toString();


        if(!validateName(name.getText().toString())){
            nameWrapper.setError("Not a valid name!");
        }
        else if(!validateFirstname(firstname.getText().toString())){
            firstnameWrapper.setError("Not a valid firstname!");
        }
        else if (!validateEmail(emailUpdate)) {
            emailWrapper.setError("Not a valid email address!");
        } else if (!validatePassword(passwdUpdate)) {
            passwordWrapper.setError("Not a valid password!");
        } else {
            nameWrapper.setErrorEnabled(false);
            firstnameWrapper.setErrorEnabled(false);
            emailWrapper.setErrorEnabled(false);
            passwordWrapper.setErrorEnabled(false);


            if(!emailUpdate.equalsIgnoreCase(user.getEmail()))
                Toast.makeText(this,"EMAIL "+emailUpdate+" "+user.getEmail(),Toast.LENGTH_LONG).show();

            //user.updateEmail(emailUpdate);
            if(!nameUpdate.equalsIgnoreCase(user.getDisplayName())) {
                Toast.makeText(this,"FULLNAME "+nameUpdate+" "+user.getDisplayName(),Toast.LENGTH_LONG).show();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nameUpdate).build();
                user.updateProfile(profileUpdates);
            }
            if(!passwdUpdate.equals(restoredPasswrd)) {
                user.updatePassword(passwdUpdate);
                setSavePassword(passwdUpdate);
                Toast.makeText(this, "PASSWORD UPDATED " + passwdUpdate, Toast.LENGTH_LONG).show();
            }

            //user.updatePassword(passwdUpdate);
            Toast.makeText(this,"ACCOUNT UPDATED ",Toast.LENGTH_LONG).show();

            hideKeyboard(UpdateActivity.this);

        }
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

    public boolean validateName(String name) {
        return name.length() > 0;
    }

    public boolean validateFirstname(String firstname) {
        return firstname.length() > 0;
    }



    @SuppressLint("LongLogTag")
    @Override
    protected void onStart() {
        super.onStart();
        getSavePassword();
        userSignIn();
        Log.i("LOG_DETECTED UpdateActivity","onStart");
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LOG_DETECTED UpdateActivity","onPause");
    }
    @SuppressLint("LongLogTag")
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LOG_DETECTED UpdateActivity","onResume");
        //userSignIn();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LOG_DETECTED UpdateActivity","onDestroy");
    }

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    @SuppressLint("LongLogTag")
    private void getSavePassword(){
        prefs = getSharedPreferences(mPreferences, MODE_PRIVATE);
        restoredPasswrd = prefs.getString(key_passwrd, null);
        Log.i("UpdateActivity restoredPasswrd", restoredPasswrd);
    }

    public  void setSavePassword(String password){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(mPreferences, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key_passwrd, password);
        editor.apply();
    }
}
