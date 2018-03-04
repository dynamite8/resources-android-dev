package com.tiptopgoodstudio.androidresources;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /** Temporarily creating a button to access resources list page
     * Added by Divya on 3/3/2018.
     * Definitely can be removed
     */
    public void gotoResourcesPage(View view) {
        Intent resourceIntent = new Intent(this, ResourceListActivity.class);
        startActivity(resourceIntent);
    }
}
