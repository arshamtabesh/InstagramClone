package com.arman.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave, btnTransition;
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private TextView txtGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSave = findViewById(R.id.btnSave);
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);

        txtGetData = findViewById(R.id.txtGetData);
        btnTransition.findViewById(R.id.btnTransition);

        btnSave.setOnClickListener(SignUpActivity.this);
        txtGetData.setOnClickListener(SignUpActivity.this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(edtKickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null)
                        FancyToast.makeText(SignUpActivity.this, kickBoxer.getObjectId() + ", " + kickBoxer.get("name") + " is saved to server", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    else
                        FancyToast.makeText(SignUpActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                }
            });
        } else if (v.getId() == R.id.txtGetData) {
            ParseQuery<ParseObject> mParseQuery = ParseQuery.getQuery("KickBoxer");
            mParseQuery.whereGreaterThan("punchPower",1000);
            mParseQuery.setLimit(1);
            mParseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size()> 0){

                            for (ParseObject po: objects){
                                FancyToast.makeText(SignUpActivity.this, po.get("name") + ", " + po.get("punchSpeed") +", " + po.get("punchPower") + ", " + po.get("kickSpeed") + ", " + po.get("kickPower")  , FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                            }
                        }
                    }
                }
            });
            mParseQuery.getInBackground("p36RAXuf7Z", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object != null){

                        edtName.setText(object.get("name").toString());
                        edtPunchSpeed.setText(object.get("punchSpeed").toString());
                        edtPunchPower.setText(object.get("punchPower").toString());
                        edtKickSpeed.setText(object.get("kickSpeed").toString());
                        edtKickPower.setText(object.get("kickPower").toString());
                    }
                }
            });
        }
    }

    public void btnSwitchTapped(View v){

    }
}