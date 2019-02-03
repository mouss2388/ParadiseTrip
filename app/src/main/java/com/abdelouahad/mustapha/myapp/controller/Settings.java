package com.abdelouahad.mustapha.myapp.controller;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
    protected Switch nigthMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkBxVibrator = findViewById(R.id.chckBxVibrator);
        checkBxSound = findViewById(R.id.chckBxSound);
        Switch switchNotif = findViewById(R.id.switchNotif);
        nigthMode=findViewById(R.id.switchNight);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.reservation);


        checkBxVibrator.setChecked(VIBRATOR_ENABLE);
        checkBxSound.setChecked(SOUND_ENABLED);
        switchNotif.setChecked(NOTIF_ENABLE);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            String title = getResources().getString(R.string.settings);
            actionbar.setTitle(title);

            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }

        checkBxVibrator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //  Vibrator vb = (Vibrator)   getSystemService(Context.VIBRATOR_SERVICE);
// vb.vibrate(100);
                VIBRATOR_ENABLE = checkBxVibrator.isChecked();

            }
        });


        checkBxSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SOUND_ENABLED = checkBxSound.isChecked();
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
                NIGHT_ENABLE = isChecked;
            }
        });
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
}
