package com.abdelouahad.mustapha.myapp.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.abdelouahad.mustapha.myapp.R;

public class TravellersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travellers);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_eco_class:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_premium_class:
                if (checked)
                    // Ninjas rule
                    break;

            case R.id.radio_business_class:
                if (checked)
                    // Ninjas rule
                    break;

            case R.id.radio_first_class:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
