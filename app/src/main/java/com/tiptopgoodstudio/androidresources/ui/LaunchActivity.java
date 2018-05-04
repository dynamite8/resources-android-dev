package com.tiptopgoodstudio.androidresources.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.ResourceRepository;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Divya on 3/3/2018.
 * For GrowWithGoogleChallengeScholarship Women In Technology Learning Project
 * Android Dev Resources
 *
 * This activity loads data from Firebase and then creates an Explicit Intent to
 * MainActivity. We want to make sure that there is data in the local DB before
 * MainActivity can display the topics
 *
 */

public class LaunchActivity extends AppCompatActivity {

    // A TAG to denote the classname for Logging purposes
    private static final String TAG = LaunchActivity.class.getSimpleName();

    private TextView mLoadingMessage;
    private ProgressBar mProgressBar;
    boolean isOnline = false;

    private final int TOTAL_RESOURCES = 205;
    private final AtomicInteger count = new AtomicInteger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Log.d(TAG, "In onCreate");

        mLoadingMessage = (TextView) findViewById(R.id.tv_loading);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        checkConnectivityAndLoadResources();

    }

    private void checkConnectivityAndLoadResources() {
        isOnline = isOnline();

        if(isOnline) {
            try {
                // First load data from Firebase into local DB if required
                ResourceRepository resourceRepository = new ResourceRepository(getApplication());
                DatabaseReference dbReference = resourceRepository.connectToFirebase();

                dbReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        resourceRepository.addToLocalDatabase(dataSnapshot);
                        int newCount = count.incrementAndGet();
                        int progress = (100 * newCount)/TOTAL_RESOURCES;
                        mProgressBar.setProgress(progress);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                dbReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    //When we are done with updating data from Firebase, go to MainActivity
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        startMainActivity();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mLoadingMessage.setText(getString(R.string.loading_error_message));
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                mLoadingMessage.setText(getString(R.string.loading_error_message));
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }
        else {
            mLoadingMessage.setText(getString(R.string.loading_error_message));
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    /*
    * Added on 04/23/2018 by Olga Agafonova
    * The user may have (re)connected to Wifi/Mobile while the app was paused:
    * therefore, we need to check for connectivity again
    * */
    @Override
    public void onResume(){
       super.onResume();

       checkConnectivityAndLoadResources();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}