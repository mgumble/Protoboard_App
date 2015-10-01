package com.example.emilie.practiceapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;

import java.util.ArrayList;

public class LTBoardActivity extends Activity implements OnClickListener {

    private LinearLayout container;
    private DropboxAPI dropboxApi;
    private boolean isUserLoggedIn;
    private Button loginBtn;
    private Button uploadFileBtn;
    private Button listFilesBtn;

    private final static String DROPBOX_FILE_DIR = "/AndroidDropboxImplementationExample/";
    private final static String DROPBOX_NAME = "dropbox_prefs";
    private final static String ACCESS_KEY = "nc8y7ktqwyuzjx7";
    private final static String ACCESS_SECRET = "e1t1p8msv1j4t42";
    private final static AccessType ACCESS_TYPE = AccessType.DROPBOX;
    private DropboxAPI<AndroidAuthSession> mDBApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltboard);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        uploadFileBtn = (Button) findViewById(R.id.uploadFileBtn);
        uploadFileBtn.setOnClickListener(this);
        listFilesBtn = (Button) findViewById(R.id.listFilesBtn);
        listFilesBtn.setOnClickListener(this);
        container = (LinearLayout) findViewById(R.id.container_files);

        loggedIn(false);

        AppKeyPair appKeyPair = new AppKeyPair(ACCESS_KEY, ACCESS_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);;
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        mDBApi.getSession().startOAuth2Authentication(LTBoardActivity.this);
        SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
        //dropboxApi = new DropboxAPI(session);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AndroidAuthSession session = mDBApi.getSession();

        if (session.authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                session.finishAuthentication();
                loggedIn(true);
                TokenPair accessToken = session.getAccessTokenPair();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }



    private final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            ArrayList<String> result = message.getData().getStringArrayList("data");
            for (String fileName : result) {
                TextView textView = new TextView(LTBoardActivity.this);
                textView.setText(fileName);
                container.addView(textView);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (isUserLoggedIn) {
                    mDBApi.getSession().unlink();
                    loggedIn(false);
                } else {
                    mDBApi.getSession().startOAuth2Authentication(LTBoardActivity.this);
                }
                break;
            case R.id.uploadFileBtn:
                DropboxAPI.Entry entry = new DropboxAPI.Entry();
                UploadFile uploadFile = new UploadFile(this,mDBApi,DROPBOX_FILE_DIR);
                uploadFile.execute();
                break;
            case R.id.listFilesBtn:
                ListFiles listFiles = new ListFiles(mDBApi, DROPBOX_FILE_DIR, handler);
                listFiles.execute();

                break;
            default:
                break;
        }
    }

    public void loggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
        uploadFileBtn.setEnabled(userLoggedIn);
        uploadFileBtn.setBackgroundColor(userLoggedIn ? Color.BLUE : Color.GRAY);
        listFilesBtn.setEnabled(userLoggedIn);
        listFilesBtn.setBackgroundColor(userLoggedIn ? Color.BLUE : Color.GRAY);
        loginBtn.setText(userLoggedIn ? "Logout" : "Log in");
    }
}
