package com.arman.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("AFCHK4rNfqF3Q73xvcZUaDrfMvOcoCUNa1gGzUK4")
                // if defined
                .clientKey("iGB7BOpPysdFg03wHa3K6vBYl4wwWrGEtWlJmpp7")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
