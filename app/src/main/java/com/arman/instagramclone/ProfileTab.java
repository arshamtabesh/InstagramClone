package com.arman.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class ProfileTab extends Fragment implements View.OnClickListener {


    private EditText edtProfileName, edtProfileBio, edtProfileProfession,
            edtProfileHobbies, edtProfileFavSport;
    private Button btnUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileFavSport = view.findViewById(R.id.edtProfileFavouriteSport);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);
        btnUpdateInfo.setOnClickListener(this);

        fetchAndShowCurrentUserInfo();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnUpdateInfo.getId()) {
            ParseUser mParseUser = ParseUser.getCurrentUser();
            mParseUser.put("profileName", edtProfileName.getText().toString());
            mParseUser.put("profileBio", edtProfileBio.getText().toString());
            mParseUser.put("profileProfession", edtProfileProfession.getText().toString());
            mParseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
            mParseUser.put("profileFavSport", edtProfileFavSport.getText().toString());
            ProgressDialog mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Updating Info...");
            mProgressDialog.show();
            mParseUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    mProgressDialog.dismiss();
                    if (e == null) {
                        FancyToast.makeText(getContext(), "Info Updated",
                                Toast.LENGTH_SHORT, FancyToast.INFO,
                                true).show();
                    } else {
                        FancyToast.makeText(getContext(), e.getMessage(),
                                Toast.LENGTH_LONG, FancyToast.ERROR,
                                true).show();
                    }
                }
            });

        }
    }

    private void fetchAndShowCurrentUserInfo(){
        ParseUser mParseUser = ParseUser.getCurrentUser();
        edtProfileName.setText(SafeText(mParseUser.get("profileName")));
        edtProfileBio.setText(SafeText(mParseUser.get("profileBio")));
        edtProfileProfession.setText(SafeText(mParseUser.get("profileProfession")));
        edtProfileHobbies.setText(SafeText(mParseUser.get("profileHobbies")));
        edtProfileFavSport.setText(SafeText(mParseUser.get("profileFavSport")));
    }
    private String SafeText (Object object){
        if (object == null) return  "";
        return object.toString();
    }
}
