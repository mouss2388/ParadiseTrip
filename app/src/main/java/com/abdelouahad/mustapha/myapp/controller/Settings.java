package com.abdelouahad.mustapha.myapp.controller;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.abdelouahad.mustapha.myapp.R;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.abdelouahad.mustapha.myapp.controller.MainActivity.NIGHT_ENABLE;
import static com.abdelouahad.mustapha.myapp.controller.MainActivity.NOTIF_ENABLE;
import static com.abdelouahad.mustapha.myapp.controller.MainActivity.SOUND_ENABLED;
import static com.abdelouahad.mustapha.myapp.controller.MainActivity.VIBRATOR_ENABLE;


public class Settings extends AppCompatActivity {

    private CheckBox checkBxVibrator, checkBxSound;
    private Switch switchNotif, nigthMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkBxVibrator = findViewById(R.id.chckBxVibrator);
        checkBxSound = findViewById(R.id.chckBxSound);
        switchNotif= findViewById(R.id.switchNotif);
        nigthMode=findViewById(R.id.switchNight);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.reservation);


        checkBxVibrator.setChecked(VIBRATOR_ENABLE);
        checkBxSound.setChecked(SOUND_ENABLED);
        switchNotif.setChecked(NOTIF_ENABLE);


        checkBxVibrator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBxVibrator.isChecked()) {
                    VIBRATOR_ENABLE = true;
                    //  Vibrator vb = (Vibrator)   getSystemService(Context.VIBRATOR_SERVICE);
                    // vb.vibrate(100);
                }else{
                    VIBRATOR_ENABLE = false;
                }

            }
        });


        checkBxSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBxSound.isChecked()) {
                    SOUND_ENABLED = true;
                }else
                    SOUND_ENABLED = false;
            }
        });

        //A FINIR
        switchNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    NOTIF_ENABLE=true;
                    FirebaseMessaging.getInstance().subscribeToTopic("news");
                }else{
                    NOTIF_ENABLE=false;
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("news");
                }
            }
        });



        nigthMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    NIGHT_ENABLE=true;
                }else{
                    NIGHT_ENABLE=false;
                }
            }
        });
    }
}
