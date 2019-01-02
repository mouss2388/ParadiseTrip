package com.abdelouahad.mustapha.myapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;

public class TravellersActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton childUp,childDown,adultUp,adultDown,seniorUp,seniorDown;
    TextView number_child,number_adult,number_senior;
    RadioButton eco,premium,business,first_class;
    String classe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travellers);
        childUp =findViewById(R.id.child_up);
        childDown =findViewById(R.id.child_down);
        adultUp =findViewById(R.id.adult_up);
        adultDown =findViewById(R.id.adult_down);
        seniorUp =findViewById(R.id.senior_up);
        seniorDown =findViewById(R.id.senior_down);

        number_child = findViewById(R.id.number_child);
        number_adult= findViewById(R.id.number_adulte);
        number_senior= findViewById(R.id.number_senior);

        eco = findViewById(R.id.radio_eco_class);
        premium = findViewById(R.id.radio_premium_class);
        first_class = findViewById(R.id.radio_first_class);
        business = findViewById(R.id.radio_business_class);





        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            String title = getResources().getString(R.string.travellers_classes);
            actionbar.setTitle(title);            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_eco_class:
                if (checked)
                    classe =eco.getText().toString();
                    // Pirates are the best
                    break;
            case R.id.radio_premium_class:
                if (checked)
                    classe =premium.getText().toString();
                // Ninjas rule
                    break;

            case R.id.radio_business_class:
                if (checked)
                    classe =business.getText().toString();
                // Ninjas rule
                    break;

            case R.id.radio_first_class:
                if (checked)
                    classe =first_class.getText().toString();
                // Ninjas rule
                    break;
        }
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

    @Override
    public void onClick(View v) {
        int nbChild = Integer.parseInt(number_child.getText().toString());
        int nbAdult = Integer.parseInt(number_adult.getText().toString());
        int nbSenior = Integer.parseInt(number_senior.getText().toString());
        int Sum = nbChild + nbAdult + nbSenior;

            switch (v.getId()) {
                case R.id.child_down:
                    if (nbChild > 0 )
                        number_child.setText(String.valueOf(nbChild - 1));
                    break;
                case R.id.child_up:
                    if (Sum < 6 )
                        number_child.setText(String.valueOf(nbChild + 1));
                    else{
                        Toast.makeText(TravellersActivity.this,
                                "6 passages maximum ",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.adult_down:
                    if (nbAdult > 0 )
                        number_adult.setText(String.valueOf(nbAdult - 1));
                    break;
                case R.id.adult_up:
                    if (Sum < 6 )
                        number_adult.setText(String.valueOf(nbAdult + 1));
                    else{
                        Toast.makeText(TravellersActivity.this,
                                "6 passages maximum ",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.senior_down:
                    if (nbSenior > 0 )

                        number_senior.setText(String.valueOf(nbSenior - 1));
                    break;
                case R.id.senior_up:
                    if (Sum < 6 )
                        number_senior.setText(String.valueOf(nbSenior + 1));
                    else{
                        Toast.makeText(TravellersActivity.this,
                                "6 passages maximum ",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
            }

    }
}
