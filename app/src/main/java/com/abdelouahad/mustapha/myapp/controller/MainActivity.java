package com.abdelouahad.mustapha.myapp.controller;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;
import com.abdelouahad.mustapha.myapp.model.FirebaseCallback;
import com.abdelouahad.mustapha.myapp.model.RequestDataTravel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.abdelouahad.mustapha.myapp.controller.LoginActivity.key_passwrd;
import static com.abdelouahad.mustapha.myapp.controller.LoginActivity.mPreferences;
import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_COUNTRY_ID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private int[] mImgIds;
    private LayoutInflater mInflater;
    private Boolean login_state= false;
    protected Button yourDestination;
    private TextView name, email;


    public static Boolean NOTIF_ENABLE=false, SOUND_ENABLED= false, VIBRATOR_ENABLE= false, NIGHT_ENABLE=false;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.nav_view);

        View headerLayout = navigationView.getHeaderView(0);

        name= headerLayout.findViewById(R.id.username_data);
        email = headerLayout.findViewById(R.id.email_data);

        yourDestination = findViewById(R.id.yourDestination);


        //crée la gallerie des destinations tendence
        mInflater = LayoutInflater.from(this);
        initData();
        initView();

        toolbar = findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);

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
                /*Toast.makeText(MainActivity.this,
                        "onDrawerOpened",
                        Toast.LENGTH_LONG).show();*/

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
               /* Toast.makeText(MainActivity.this,
                        "onDrawerClosed",
                        Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();




        ///boutton Votre yourDestination Onclick
        yourDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseDestination.class);
                MainActivity.this.startActivity(intent);

            }
        });





      /* CHANGE MENU DYNAMICALLY
      if(!login_state) {
            @SuppressLint("InflateParams") View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_logout, null);
            navigationView.addHeaderView(nav_header);
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer_logout); //inflate new items.

        }else{
            @SuppressLint("InflateParams") View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_login, null);
            navigationView.addHeaderView(nav_header);
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer); //inflate new items.
        }*/
        //////NOT WORK///
        // View headerview = navigationView.getHeaderView(0);

      /*  headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();
                if(v.getId()== R.id.button_toLogin){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            }
        });*/

        //laisse les couleurs d origine
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(this);


    }


    //Tab contenant les images des destinations
    private void initData()
    {
        mImgIds = new int[] { R.drawable.paris, R.drawable.ibiza, R.drawable.new_york, R.drawable.paris, R.drawable.ibiza
        };
    }

    private void initView()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference []myRef = new DatabaseReference[5];
        // DatabaseReference myRef = database.getReference("test");
        final LinearLayout gallery = findViewById(R.id.id_gallery);



        final RequestDataTravel[] info = new RequestDataTravel[10];
        for(int i=0; i<10; i++){
            info[i] = new RequestDataTravel("Id+Banner");
            final int finalI = i;
            info[i].getData("TRAVEL_"+(i+1),new FirebaseCallback() {
                @Override
                public void onCallback() {
                    Log.i("DATA INFO"," obj:"+ info[finalI].getId());
                    Log.i("DATA INFO"," obj:"+ info[finalI].getBanner());
                    //  Log.i("DATA INFO"," obj:"+ info[finalI].getDescription());


                    byte[] decodedString = Base64.decode(String.valueOf(info[finalI].getBanner()), Base64.DEFAULT);

                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    //////////
                    View view = mInflater.inflate(R.layout.activity_gallery_item,
                            gallery, false);
                    ImageView img = view
                            .findViewById(R.id.id_index_gallery_item_image);
                    img.setImageBitmap(decodedByte);

                    //Detection clic sur image
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,
                                    "The favorite list would appear on clicking this icon",
                                    Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                            String id= String.valueOf(info[finalI].getId());
                            intent.putExtra(EXTRA_COUNTRY_ID, id);
                            MainActivity.this.startActivity(intent);

                        }
                    });
                    gallery.addView(view);

                    gallery.callOnClick();

                }
            });
        }


       /* for (int mImgId : mImgIds) {

            View view = mInflater.inflate(R.layout.activity_gallery_item,
                    gallery, false);
            ImageView img = view
                    .findViewById(R.id.id_index_gallery_item_image);
            img.setImageResource(mImgId);

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


        }*/
    }

    private void userSignIn(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(this,"User sign In",Toast.LENGTH_LONG).show();
            Toast.makeText(this,"His Email is "+user.getEmail(),Toast.LENGTH_LONG).show();
            email.setText(user.getEmail());
            name.setText(user.getDisplayName());


        } else {
            Toast.makeText(this,"User No signed",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        //Evenements lors de l appuis de la touche back
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            displayDialog();
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

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        Intent intent ;
        switch (item.getItemId()) {
            case (R.id.my_travels):
                intent = new Intent(MainActivity.this, ListMyTravels.class);
                this.startActivity(intent);
                break;
            case (R.id.my_account):
                intent = new Intent(MainActivity.this, UpdateActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                break;
            case (R.id.parameters):
                Toast.makeText(MainActivity.this,
                        "push on paramaters",
                        Toast.LENGTH_LONG).show();
                intent = new Intent(MainActivity.this, Settings.class);
                this.startActivity(intent);
                break;
            case (R.id.promotions):
                Toast.makeText(MainActivity.this,
                        "push on promotions",
                        Toast.LENGTH_LONG).show();
                intent = new Intent(MainActivity.this, Promotions.class);
                this.startActivity(intent);
                break;
            case (R.id.logout):
                Toast.makeText(MainActivity.this,
                        "push on logout",
                        Toast.LENGTH_LONG).show();
                displayDialog();
                break;

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*private  void checkLog(){
        if(login_state) {
            View oldHeaderView = navigationView.getHeaderView(0);
            @SuppressLint("InflateParams") View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_login, null);

            navigationView.removeHeaderView(oldHeaderView);
            navigationView.addHeaderView(nav_header);
            //navigationView.getHeaderView(0).refreshDrawableState();
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer); //inflate new items.


        }else{
            View oldHeaderView = navigationView.getHeaderView(0);
            @SuppressLint("InflateParams") View nav_header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_logout, null);

            navigationView.removeHeaderView(oldHeaderView);
            navigationView.addHeaderView(nav_header);
            navigationView.getMenu().clear(); //clear old inflated items.
            navigationView.inflateMenu(R.menu.activity_main_drawer_logout); //inflate new items.
        }
    }*/

    @SuppressLint("LongLogTag")
    @Override
    protected void onStart() {
        super.onStart();
        userSignIn();
        SharedPreferences prefs = getSharedPreferences(mPreferences, MODE_PRIVATE);
        String restoredText = prefs.getString(key_passwrd, null);
        Log.i("LOG_DETECTED MaintActivity","onStart");
        Log.i("LOG_DETECTED MaintActivity",restoredText);

    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LOG_DETECTED MaintActivity","onPause");

    }
    @SuppressLint("LongLogTag")
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LOG_DETECTED MaintActivity","onResume");
        if(NIGHT_ENABLE) {
            //toolbar.setBackgroundResource(R.color.colorBlack);
        }
        //userSignIn();
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LOG_DETECTED MaintActivity","onDestroy");
    }

    protected void displayDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Etes vous sûr de vouloir vous déconnecter")
                .setTitle("Information");
        builder.setIcon(R.drawable.logout);

// Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                 finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });


// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
