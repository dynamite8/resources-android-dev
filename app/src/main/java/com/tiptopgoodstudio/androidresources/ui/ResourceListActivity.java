package com.tiptopgoodstudio.androidresources.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.ui.adapters.ResourceListAdapter;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;
import com.tiptopgoodstudio.androidresources.viewmodel.ResourceViewModel;

import java.util.List;


/**
 * Created by Divya on 3/3/2018.
 * For GrowWithGoogleChallengeScholarship Women In Technology Learning Project
 * Android Dev Resources
 *
 * This class displays the recycler view of list of com.tiptopgoodstudio.androidresources.db.entity.Resource items
 * Temporarily mock data is generated and displayed
 *
 * The original ResourceList Activity class was converted to Fragment class on 03/22/2018 by Olga Agafonova.
 *
 * A simple {@link Fragment} subclass.
 *
 * Converting back to ResourceListActivity to keep it simple for version 1 on 04/19/2018 by Divya
 *
 * Added a method to get data from the ViewModel and removed the mock data on 04/21/2018 by Divya
 *
 */
public class ResourceListActivity extends AppCompatActivity
        implements ResourceListAdapter.ResourceClickListener {

    // A TAG to denote the classname for Logging purposes
    private static final String TAG = ResourceListActivity.class.getSimpleName();

    // A private RecyclerView variable called mRecyclerView
    private RecyclerView mRecyclerView;

    // A com.tiptopgoodstudio.androidresources.ui.adapters.ResourceListAdapter variable called mResourceListAdapter
    private ResourceListAdapter mResourceListAdapter;

    // A progress bar before displaying results
    private ProgressBar mLoadingIndicator;

    // An error message to display errors
    private TextView mErrorMessageDisplay;

    // A variable to store the resourceTopic passed through an Intent
    private String resourceTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.d(TAG, "In onCreate()");

        setContentView(R.layout.resources_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_resource_item);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);

        mRecyclerView.setLayoutManager(layoutManager);

        // Use setHasFixedSize(true) to designate that all items in the list will have the same size
        mRecyclerView.setHasFixedSize(true);

        // Assign a new com.tiptopgoodstudio.androidresources.ui.Adapters.ResourceListAdapter object to our member variable
        mResourceListAdapter = new ResourceListAdapter(this);

        mRecyclerView.setAdapter(mResourceListAdapter);

        // This progress bar is displayed while loading the results
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // This TextView is used to display errors and will be hidden if there are no errors
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        // The getIntent method retrieves the Intent that started this Activity
        Intent intentFromParent = getIntent();

        if(intentFromParent.hasExtra(Intent.EXTRA_TEXT)) {
            resourceTopic = intentFromParent.getStringExtra(Intent.EXTRA_TEXT);

            // Set the title of the activity to this resource Topic
            setTitle(resourceTopic);
        }

        // Call the ViewModel methods to retrieve data
        loadDataFromViewModel();

    }

    /**
     * This method retrieves data from the ViewModel
     *
     * Added by Divya on 4/20/2018.
     *
     */

    private void loadDataFromViewModel(){

        Log.d(TAG, "In loadDataFromViewModel()");

        try {

            mLoadingIndicator.setVisibility(View.VISIBLE);

            ResourceViewModel model = ViewModelProviders.of(this).get(ResourceViewModel.class);

            if(resourceTopic != null && !resourceTopic.equals("")) {
                model.getTopicResources(resourceTopic).observe(this, resourceList -> {
                    //Update UI
                    updateResourceListInUI(resourceList);
                });
            } else {
                model.getAllResources().observe(this, resourceList -> {
                    //Update UI
                    updateResourceListInUI(resourceList);
                });
            }

            showResourceListView();

        } catch(Exception e) {
            e.printStackTrace();
            showErrorMessage();
        } finally {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * This method sends the data to RecyclerView Adapter
     * @param resourceList
     *
     * Added by Divya on 4/20/2018.
     *
     */
    private void updateResourceListInUI(List<Resource> resourceList) {
        mResourceListAdapter.setResourceData(resourceList);
    }

    /**
     * This method will make the resource list visible and
     * hide the error message.
     */
    private void showResourceListView() {

        Log.d(TAG, "In showResourceListView()");

        // First, make the error invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        // Then, make sure the recycler view list visible
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the resource list view
     */
    private void showErrorMessage() {

        Log.d(TAG, "In showErrorMessage()");

        // First, hide the currently visible data
        mRecyclerView.setVisibility(View.INVISIBLE);

        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * This method will be called when the resource item is clicked
     *
     * Added by Divya on 3/24/2018.
     */
    @Override
    public void onResourceItemClick(String url) {

        Log.d(TAG, "In onResourceItemClick()");

        openWebPage(url);
    }

    /**
     * This method creates an Implicit Intent to open the url in an appropriate app
     *
     * Added by Divya on 3/24/2018.
     */
    public void openWebPage(String url) {

        Log.d(TAG, "In openWebPage()");

        try {

            Uri webpage = Uri.parse(url);

            Intent webpageIntent = new Intent(Intent.ACTION_VIEW, webpage);

            if (webpageIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(webpageIntent);
            }
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Some error occured. Could not open the resource!", Toast.LENGTH_LONG).show();
        }
    }
}