package com.tiptopgoodstudio.androidresources;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.ArrayList;
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
 */
public class ResourcesFragment extends Fragment
                                implements ResourceListAdapter.ResourceClickListener{

    private static final String TAG = HomeFragment.class.getSimpleName();

    // A private RecyclerView variable called mRecyclerView
    private RecyclerView mRecyclerView;

    // A com.tiptopgoodstudio.androidresources.ResourceListAdapter variable called mResourceListAdapter
    private ResourceListAdapter mResourceListAdapter;

    // A progress bar before displaying results
    private ProgressBar mLoadingIndicator;

    // An error message to display errors
    private TextView mErrorMessageDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resources, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_resource_item);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        // Use setHasFixedSize(true) to designate that all items in the list will have the same size
        mRecyclerView.setHasFixedSize(true);

        // Assign a new com.tiptopgoodstudio.androidresources.ResourceListAdapter object to our member variable
        mResourceListAdapter = new ResourceListAdapter(this);

        mRecyclerView.setAdapter(mResourceListAdapter);

        // This progress bar is displayed while loading the results
        mLoadingIndicator = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);

        // This TextView is used to display errors and will be hidden if there are no errors
        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display);

        //Now load the resources data
        loadResourcesData();

        return view;
    }

    private void loadResourcesData(){

        mResourceListAdapter.setResourceData(generateMockData());

    }

    /**
     * This method will generate the mock data for now
     * TODO - To be replaced by the data we get from ViewModel
     *
     * Added by Divya on 3/24/2018.
     *
     */
    private List<Resource> generateMockData() {
        List<Resource> resourcesList = new ArrayList<Resource>();

        for(int i = 1; i <= 20; i++) {
            Resource currentResource = new Resource("Topic " + (i % 4),
                                                    "Title " + i,
                                                     "https://www.google.com/search?q=Title+"+i);
            resourcesList.add(currentResource);
            Log.d(TAG, "Added resource with title " + i);
        }

        return resourcesList;

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


    /**
     * This method will be called when the resource item is clicked
     * This method creates an Implicit Intent to open the url in a browser app
     *
     * Added by Divya on 3/24/2018.
     */
    @Override
    public void onResourceItemClick(String url) {
        openWebPage(url);
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);

        Intent webpageIntent = new Intent(Intent.ACTION_VIEW, webpage);

        if (webpageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(webpageIntent);
        }
    }
}
