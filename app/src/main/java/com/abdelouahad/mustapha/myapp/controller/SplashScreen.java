package com.abdelouahad.mustapha.myapp.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;

import java.net.InetAddress;

public class SplashScreen extends AppCompatActivity {

    private boolean networkOk= false;
    ProgressDialog pd =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pd= new ProgressDialog(SplashScreen.this);
                pd.setMessage("Check Network Status");
                pd.show();
                networkOk=isNetworkAvailable(getBaseContext());

                if(networkOk) {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    SplashScreen.this.startActivity(intent);
                    finish();
                }else
                    Toast.makeText(SplashScreen.this,
                            "Connection Réseau impossible veuillez vérifier votre connection",
                            Toast.LENGTH_LONG).show();
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pd.dismiss();
    }
}
