package com.example.selim.h2h.utils;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by selim on 26/01/2016.
 */
public class MyApp extends Application {

 public ParseUser userPatient;
    public ParseObject parseObject;

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "5MFXNBCly5I4Do6W0lQSk5ztXjJZWGNweVfIzfsM", "7ICppP0Hqno8Lue1F0f8jpxFBLK84j7ax2MMIQzb");
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }


}
