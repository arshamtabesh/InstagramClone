package com.arman.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtUsername, edtPassword;
    private Button btnSignUp, btnLogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        edtEmail = findViewById(R.id.edSLEmail);
        edtUsername = findViewById(R.id.edSLUsername);
        edtPassword = findViewById(R.id.edSLPassword);
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    onClick(btnSignUp);

                }
                return false;
            }
        });

        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null)
            ParseUser.getCurrentUser().logOut();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                if (isEmpty (edtEmail) || isEmpty(edtPassword) || isEmpty(edtUsername)) {
                    FancyToast.makeText(SignUpActivity.this,
                            "Please full all boxes",
                            Toast.LENGTH_LONG, FancyToast.ERROR,
                            true).show();
                    return;
                }
                final ParseUser user = new ParseUser();
                user.setEmail(edtEmail.getText().toString());
                user.setUsername(edtUsername.getText().toString());
                user.setPassword(edtPassword.getText().toString());

                ProgressDialog mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Signing up " + edtUsername.getText().toString());
                mProgressDialog.show();

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        mProgressDialog.dismiss();
                        if (e == null) {
                            FancyToast.makeText(SignUpActivity.this,
                                    user.getUsername() + " is signed up",
                                    Toast.LENGTH_LONG, FancyToast.SUCCESS,
                                    true).show();
                        } else {
                            FancyToast.makeText(SignUpActivity.this,
                                    "There was an error: " + e.getMessage(),
                                    Toast.LENGTH_LONG, FancyToast.ERROR,
                                    true).show();
                        }
                    }
                });
                break;
            case R.id.btnLogin:
                Intent mIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(mIntent);


                break;
        }
    }

    public  static Boolean isEmpty(EditText edt){
        return edt.getText().toString().trim().equals("");
    }

    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}