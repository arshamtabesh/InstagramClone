package com.arman.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edUsernameSignup, edUsernameLogin, edPasswordSignup, edPasswordLogin;
    Button btnSignup, btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edUsernameLogin = findViewById(R.id.edtUserNameLogin);
        edUsernameSignup = findViewById(R.id.edtUserNameSignup);

        edPasswordLogin = findViewById(R.id.edtPasswordLogin);
        edPasswordSignup = findViewById(R.id.edtPasswordSignup);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(this::onClick);
        btnSignup.setOnClickListener(this::onClick);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnLogin.getId()) {
            ParseUser appUser = new ParseUser();
            appUser.logInInBackground(edUsernameLogin.getText().toString(), edPasswordLogin.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null && user != null) {
                        FancyToast.makeText(SignUpLoginActivity.this, user.getUsername() + " logged in successfully" , FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    } else {
                        FancyToast.makeText(SignUpLoginActivity.this,e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                    }
                }
            });


        } else if (v.getId() == btnSignup.getId()) {
            ParseUser appUser = new ParseUser();
            appUser.setUsername(edUsernameSignup.getText().toString());
            appUser.setPassword(edPasswordSignup.getText().toString());
            appUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUpLoginActivity.this, appUser.getUsername() + " is registered successfully" , FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    } else {
                        FancyToast.makeText(SignUpLoginActivity.this,e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                    }
                }
            });
        }
    }
}
