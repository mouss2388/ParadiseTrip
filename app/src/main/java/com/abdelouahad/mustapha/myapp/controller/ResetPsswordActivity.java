package com.abdelouahad.mustapha.myapp.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPsswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pssword);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            String title = getResources().getString(R.string.reset_password);
            actionbar.setTitle(title);            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }

        editTextEmail = findViewById(R.id.reset_email);
        buttonReset = findViewById(R.id.reset_btn);
        final ImageView img = findViewById(R.id.reset_logo);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isEmpty())
                    sendEmailResetPasswd();
            }
        });

      /*  FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                byte[] decodedString = Base64.decode(String.valueOf(value), Base64.DEFAULT);

                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                img.setImageBitmap(decodedByte);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RESETPASSWORD", "Failed to read value.", error.toException());
            }
        });
       // Log.d("RESETPASSWORD", "imgBase64Encode is: " + imgBase64Encode);


        /**DEcode 64**/
         //byte[] imageAsBytes = Base64.decode(String.valueOf(imgBase64Encode),Base64.DEFAULT);
        // img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0,imageAsBytes.length));




    }

    private void sendEmailResetPasswd(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = editTextEmail.getText().toString();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPsswordActivity.this,"Email sent.",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ResetPsswordActivity.this,"Error email",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private Boolean isEmpty(){
        return editTextEmail.getText().length()> 5;
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
