package com.groupon.devicemanagement;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class DeviceManagementApp extends Application {


    public static final String PARSE_APP_KEY = "1RCI0lbSGFAyJb9m7USLOC8XX08tQ3vGEqxTEHOM";
    public static final String PARSE_CLIENT_KEY = "actrjJjdHLiYa6FFwQ2faS7Xk0mXw2iL7IqnVPyp";

    @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, PARSE_APP_KEY, PARSE_CLIENT_KEY);

    ParseUser.enableAutomaticUser();
    ParseUser.getCurrentUser().saveInBackground();

    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
