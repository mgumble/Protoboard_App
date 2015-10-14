package com.example.emilie.practiceapplication;

import com.dropbox.client2.session.Session.AccessType;

/**
 * Created by Mose on 10/13/2015.
 */
public class Constants {
    public static final String OVERRIDEMSG = "File name with this name already exists.Do you want to replace this file?";
    final static public String DROPBOX_APP_KEY = "nc8y7ktqwyuzjx7";
    final static public String DROPBOX_APP_SECRET = "e1t1p8msv1j4t42";
    public static boolean mLoggedIn = false;

    final static public AccessType ACCESS_TYPE = AccessType.DROPBOX;

    final static public String ACCOUNT_PREFS_NAME = "prefs";
    final static public String ACCESS_KEY_NAME = "ACCESS_KEY";
    final static public String ACCESS_SECRET_NAME = "ACCESS_SECRET";
}
