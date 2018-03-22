package com.tiptopgoodstudio.androidresources;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResourcesFragment extends Fragment {

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
        mResourceListAdapter = new ResourceListAdapter();

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
