package com.abdelouahad.mustapha.myapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private final String EMAIL_PATTERN ="^[a–zA–Z0-9#_~!$&\\'()*+,;=:.\\\"(),:;<>@\\\\[\\\\]\\\\\\\\]+@[a-zA-Z0-9-]+(\\\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;


    TextInputLayout passwordWrapper =null;
    TextInputLayout usernameWrapper =null;
    Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(false);
            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }

        passwordWrapper =  findViewById(R.id.passwordWrapper);
        usernameWrapper=  findViewById(R.id.emailWrapper);
        btn = findViewById(R.id.btnLogin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = String.valueOf(usernameWrapper.getEditText().getText());
                String password = String.valueOf(passwordWrapper.getEditText().getText());
                Toast.makeText(LoginActivity.this,
                        username +":"+password,
                        Toast.LENGTH_LONG).show();


                if (!validateEmail(username)) {
                    usernameWrapper.setError("Not a valid email address!");
                } else if (!validatePassword(password)) {
                    passwordWrapper.setError("Not a valid password!");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    doLogin();
                }
            }
        });

    }

    public void doLogin() {
        Toast.makeText(getApplicationContext(), "OK! I'm performing login.", Toast.LENGTH_SHORT).show();
        // login procedure; not within the scope of this tutorial.
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
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

