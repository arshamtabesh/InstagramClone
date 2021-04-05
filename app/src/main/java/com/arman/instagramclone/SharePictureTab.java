package com.arman.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;


public class SharePictureTab extends Fragment implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE_FRAGMENT = 2000;
    private ImageView imgShare;
    private EditText edtDescription;
    private Button btnShareImage;

    Bitmap receivedImageBitmap;


    public SharePictureTab() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SharePictureTab newInstance(String param1, String param2) {
        SharePictureTab fragment = new SharePictureTab();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imgShare = mView.findViewById(R.id.imgShare);
        edtDescription = mView.findViewById(R.id.edtDescription);
        btnShareImage = mView.findViewById(R.id.btnShareImage);

        imgShare.setOnClickListener(SharePictureTab.this);
        btnShareImage.setOnClickListener(SharePictureTab.this);

        return mView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == imgShare.getId()) {
            if (Build.VERSION.SDK_INT >= 23 &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            } else {
                getChosenImage();
            }
        } else if (v.getId() == btnShareImage.getId()) {
            if (receivedImageBitmap != null) {
                if (edtDescription.getText().toString().trim().equals("")) {
                    FancyToast.makeText(getContext(),
                            "You must specify a description",
                            Toast.LENGTH_LONG,
                            FancyToast.ERROR, true).show();
                    return;
                } else {
                    ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
                    receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, mByteArrayOutputStream);
                    byte[] bytes = mByteArrayOutputStream.toByteArray();
                    ParseFile mParseFile = new ParseFile("ping.png",bytes);
                    ParseObject mParseObject = new ParseObject("Photo");
                    mParseObject.put("picture",mParseFile);
                    mParseObject.put("image_des",edtDescription.getText().toString());
                    mParseObject.put("username", ParseUser.getCurrentUser().getUsername());
                    final ProgressDialog dialog = new ProgressDialog(getContext());
                    dialog.setMessage("Loading");
                    dialog.show();
                    mParseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            dialog.dismiss();
                            if (e == null){
                                FancyToast.makeText(getContext(),
                                        "Done!!",
                                        Toast.LENGTH_SHORT,
                                        FancyToast.SUCCESS, true).show();
                            } else{
                                FancyToast.makeText(getContext(),
                                        e.getMessage(),
                                        Toast.LENGTH_LONG,
                                        FancyToast.ERROR, true).show();
                            }
                        }
                    });
                }
            } else {
                FancyToast.makeText(getContext(),
                        "Error: You must select an image.",
                        Toast.LENGTH_LONG,
                        FancyToast.ERROR, true).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();
            }
        }

    }


    private void getChosenImage() {
        Intent mIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(mIntent, 2000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor mCursor = getActivity().getContentResolver()
                            .query(selectedImage, filePathColumn, null, null, null);
                    mCursor.moveToFirst();
                    int columnIndex = mCursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = mCursor.getString(columnIndex);
                    mCursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imgShare.setImageBitmap(receivedImageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}