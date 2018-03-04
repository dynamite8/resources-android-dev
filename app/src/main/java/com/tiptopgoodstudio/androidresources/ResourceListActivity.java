package com.tiptopgoodstudio.androidresources; /**
 * Created by Divya on R
 * For GrowWithGoogleChallengeScholarship Women In Technology Learning Project
 * Android Dev Resources
 *
 * This class displays the recycler view of list of items. Each item consists of
 * a URL Title and a favicon
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResourceListActivity extends AppCompatActivity {

    // A private RecyclerView variable called mRecyclerView
    private RecyclerView mRecyclerView;

    // A com.tiptopgoodstudio.androidresources.ResourceListAdapter variable called mResourceListAdapter
    private ResourceListAdapter mResourceListAdapter;

    // A progress bar before displaying results
    private ProgressBar mLoadingIndicator;

    // An error message to display errors
    private TextView mErrorMessageDisplay;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.resource_list);

        // Use findViewById to get a reference to the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_resource_item);

        // Create a LinearLayoutManager with VERTICAL orientation and reverseLayout == false
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                                                                    LinearLayoutManager.VERTICAL,
                                                                    false);
        // Set the layoutManager on mRecyclerView
        mRecyclerView.setLayoutManager(layoutManager);

        // Use setHasFixedSize(true) to designate that all items in the list will have the same size
        mRecyclerView.setHasFixedSize(true);

        // Assign a new com.tiptopgoodstudio.androidresources.ResourceListAdapter object to our member variable
        mResourceListAdapter = new ResourceListAdapter();

        // Assign the adapter object to the mRecyclerView
        mRecyclerView.setAdapter(mResourceListAdapter);

        // This progress bar is displayed while loading the results
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // This TextView is used to display errors and will be hidden if there are no errors
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        //Now load the resources data
        loadResourcesData();
    }

    /**
     * This method will get the title of all the resources in the resource
     * category. The resource category is passed as an extra string in the intent from the parent class
     *
     * TODO - Add a mechanism to retrieve favicon (an image) and display it
     * TODO - Also get the URLS for each and start an intent to launch this URL in the browser
     * when the item is clicked
     * TODO - Use AsyncTaskLoader to get data from server over the network ??
     * TODO - Get data from RoomDB to get data from local SQL DB as well ??
     */
    private void loadResourcesData(){
        //showResourceListView();

        String [] resourceData = new String [20];

        for(int stringCount = 1; stringCount <= 20; stringCount++) {
            resourceData[stringCount - 1] = "Title " + stringCount;
        }

        mResourceListAdapter.setResourceData(resourceData);
    }

    /**
     * This method will make the resource list visible and
     * hide the error message.
     */
    private void showResourceListView() {
        // First, make the error invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

         // Then, make sure the recycler view list visible
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     */
    private void showErrorMessage() {
        // First, hide the currently visible data
        mRecyclerView.setVisibility(View.INVISIBLE);

        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

}
