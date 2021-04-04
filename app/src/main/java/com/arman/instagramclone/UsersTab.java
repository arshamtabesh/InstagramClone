package com.arman.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class UsersTab extends Fragment {

    private ListView mListView;
    private ArrayList mArrayList;
    private ArrayAdapter mArrayAdapter;

    public UsersTab() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UsersTab newInstance(String param1, String param2) {
        UsersTab fragment = new UsersTab();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArrayList = new ArrayList();
        mArrayAdapter = new ArrayAdapter(getContext(),
                R.layout.list_view_item,mArrayList);
        fetchAndDisplayAllUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_users_tab, container, false);

        mListView = mView.findViewById(R.id.listView);



        return mView;
    }

    private void fetchAndDisplayAllUsers(){
        ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Getting Users...");
        mProgressDialog.show();
        ParseQuery<ParseUser> mParseQuery  = ParseUser.getQuery();
        mParseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        mParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                mProgressDialog.dismiss();
                if (e == null) {
                    if (users.size() > 0){
                        for (ParseUser user: users){
                            mArrayList.add(user.getUsername());

                        }
                        mListView.setAdapter(mArrayAdapter);
                        int i  =  mListView.getCount();
                    }
                }
            }
        });


    }
}