package com.abdelouahad.mustapha.myapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //InputMethodManager inputManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }
}
