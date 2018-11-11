package com.abdelouahad.mustapha.myapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private int[] mImgIds;
    private LayoutInflater mInflater;
    private Boolean login_state= false;
    private Button destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        destination = findViewById(R.id.yourDestination);

        //crée la gallerie des destinations tendence
        mInflater = LayoutInflater.from(this);
        initData();
        initView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Toast.makeText(MainActivity.this,
                        "onDrawerOpened",
                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Toast.makeText(MainActivity.this,
                        "onDrawerClosed",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();


        ///boutton Votre destination Onclick
        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseDateActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });


        navigationView = findViewById(R.id.nav_view);


        if(!login_state) {
            View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_logout, null);
            navigationView.addHeaderView(nav_header);
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer_logout); //inflate new items.

        }else{
            View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_login, null);
            navigationView.addHeaderView(nav_header);
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer); //inflate new items.
        }
        //////NOT WORK///
        View headerview = navigationView.getHeaderView(0);

        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();
                if(v.getId()== R.id.button_toLogin){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        });

        //laisse les couleurs d origine
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(this);


      /*  // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("second Message");
        myRef.setValue("Hello World");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("MAIN_ACTIVITY", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MAIN_ACTIVITY", "Failed to read value.", error.toException());
            }
        });*/

    }


    //Tab contenant les images des destinations
    private void initData()
    {
        mImgIds = new int[] { R.drawable.paris, R.drawable.ibiza, R.drawable.new_york, R.drawable.paris, R.drawable.ibiza
        };
    }

    private void initView()
    {
        LinearLayout gallery = findViewById(R.id.id_gallery);

        for (int mImgId : mImgIds) {

            View view = mInflater.inflate(R.layout.activity_gallery_item,
                    gallery, false);
            ImageView img = view
                    .findViewById(R.id.id_index_gallery_item_image);
            img.setImageResource(mImgId);
          /*  TextView txt = (TextView) view
                    .findViewById(R.id.id_index_gallery_item_text);
            txt.setText("info "+i);*/
            //Detection clic sur image
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,
                            "The favorite list would appear on clicking this icon",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                    MainActivity.this.startActivity(intent);

                }
            });
            gallery.addView(view);

            gallery.callOnClick();


        }
    }

    @Override
    public void onBackPressed() {
        //Evenements lors de l appuis de la touche back
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_contactMe) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent =null;
        switch (item.getItemId()) {
            case (R.id.my_travels):
                if(!login_state)
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                else
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                break;
            case (R.id.my_account):
                if(!login_state)
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                else
                    intent = new Intent(MainActivity.this, UpdateActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                break;
            case (R.id.parameters):
                break;
            case (R.id.promotions):
                break;
            case (R.id.logout):
                break;
            case (R.id.debug_account):
                //change automatiquement
                Log.i("LOG STATE before:", String.valueOf(login_state));
                login_state= !login_state;
                Log.i("LOG STATE after:", String.valueOf(login_state));
                checkLog();
                break;
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private  void checkLog(){
        if(login_state) {
            View oldHeaderView = navigationView.getHeaderView(0);
            View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_login, null);

            navigationView.removeHeaderView(oldHeaderView);
            navigationView.addHeaderView(nav_header);
            //navigationView.getHeaderView(0).refreshDrawableState();
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer); //inflate new items.


        }else{
            View oldHeaderView = navigationView.getHeaderView(0);
            View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_logout, null);

            navigationView.removeHeaderView(oldHeaderView);
            navigationView.addHeaderView(nav_header);
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer_logout); //inflate new items.
        }
    }



}
