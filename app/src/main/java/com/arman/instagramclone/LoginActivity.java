package com.arman.instagramclone;

import static com.arman.instagramclone.SignUpActivity.isEmpty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLoginEmail, edtLoginPassword;
    private Button btnLoginActivity, btnSigUpLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        edtLoginEmail = findViewById(R.id.edLoginEmail);
        edtLoginPassword = findViewById(R.id.edLoginPassword);
        btnLoginActivity = findViewById(R.id.btnLoginActivity);
        btnSigUpLoginActivity = findViewById(R.id.btnSignUpLoginActivity);


        btnLoginActivity.setOnClickListener(this);
        btnSigUpLoginActivity.setOnClickListener(this);

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    onClick(btnLoginActivity);

                }
                return false;
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginActivity:
                if (isEmpty (edtLoginEmail) || isEmpty(edtLoginPassword)) {
                    FancyToast.makeText(LoginActivity.this,
                            "Please full all boxes",
                            Toast.LENGTH_LONG, FancyToast.ERROR,
                            true).show();
                    return;
                }
                ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            FancyToast.makeText(LoginActivity.this,
                                    user.getUsername() + " is logged in",
                                    Toast.LENGTH_LONG, FancyToast.SUCCESS,
                                    true).show();
                            transitionToSocialMediaActivity();

                        } else {
                            FancyToast.makeText(LoginActivity.this,
                                    "There was an error: " + e.getMessage(),
                                    Toast.LENGTH_LONG, FancyToast.ERROR,
                                    true).show();
                        }


                    }
                });

                break;
            case R.id.btnSignUpLoginActivity:
                break;
        }
    }


    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void transitionToSocialMediaActivity(){
        Intent mIntent = new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(mIntent);
    }
}