package com.abdelouahad.mustapha.myapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;
import com.abdelouahad.mustapha.myapp.model.FirebaseCallback;
import com.abdelouahad.mustapha.myapp.model.RequestDataTravel;

import java.util.ArrayList;
import java.util.List;

import static com.abdelouahad.mustapha.myapp.controller.MainActivity.EXTRA_COUNTRY;

public class ChooseDestination extends AppCompatActivity {

    String nameDestination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_destination);

        final Button btnValidate = findViewById(R.id.validate);
        final Spinner spinner1 = findViewById(R.id.destination);

        final RequestDataTravel[] info = new RequestDataTravel[10];
        // (2) create a simple static list of strings
        final List<String> spinnerArray = new ArrayList<>();

        if (getSupportActionBar() != null) {
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            String title = getResources().getString(R.string.your_destination);
            actionbar.setTitle(title);
        }
        for (int i = 0; i < 10; i++) {
            info[i] = new RequestDataTravel("Country+Id");
            final int finalI = i;
            info[i].getData("TRAVEL_" + (i + 1), new FirebaseCallback() {
                @Override
                public void onCallback() {
                    Log.i("DATA INFO", " obj:" + info[finalI].getCountry());
                    Log.i("DATA INFO", " id:" + info[finalI].getId());

                    spinnerArray.add(info[finalI].getCountry());
                    // (3) create an adapter from the list
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            ChooseDestination.this, android.R.layout.simple_spinner_item, spinnerArray);

                    // (4) set the adapter on the spinner
                    spinner1.setAdapter(adapter);

                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItemText = (String) parent.getItemAtPosition(position);
                            // Notify the selected item text
                            Toast.makeText
                                    (getApplicationContext(), "Selected : " + position, Toast.LENGTH_SHORT)
                                    .show();
                            nameDestination = String.valueOf(parent.getSelectedItem());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    btnValidate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id=null;
                            Intent intent = new Intent(ChooseDestination.this, DescriptionActivity.class);
                            Toast.makeText
                                    (getApplicationContext(), "idDestination : " + nameDestination, Toast.LENGTH_SHORT)
                                    .show();
                            for(int i=0;i<10;i++)
                                if(nameDestination.equals(info[i].getCountry()))
                                    id= String.valueOf(info[i].getId());

                            intent.putExtra(EXTRA_COUNTRY, id);
                            ChooseDestination.this.startActivity(intent);
                        }
                    });
                }
            });
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
}
