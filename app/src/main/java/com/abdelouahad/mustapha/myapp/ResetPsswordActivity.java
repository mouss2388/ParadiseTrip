package com.abdelouahad.mustapha.myapp;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }

        editTextEmail = findViewById(R.id.reset_email);
        buttonReset = findViewById(R.id.reset_btn);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailResetPasswd();
            }
        });


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
}
