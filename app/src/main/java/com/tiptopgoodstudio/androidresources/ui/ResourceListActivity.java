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
import com.tiptopgoodstudio.androidresources.Utilities;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;
import com.tiptopgoodstudio.androidresources.ui.adapters.ResourceListAdapter;
import com.tiptopgoodstudio.androidresources.viewmodel.ResourceViewModel;

import java.util.List;

/**
 * Activity displays Android resource list by user-selected topic
 */
public class ResourceListActivity extends AppCompatActivity
        implements ResourceListAdapter.ResourceClickListener {

    private static final String TAG = ResourceListActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ResourceListAdapter mResourceListAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private String mResourceTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resources_list);

        // progress bar displayed while loading the results
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // TextView used to display errors and will be hidden if there are no errors
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        //setup RecyclerView which displays list of resources
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_resource_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mResourceListAdapter = new ResourceListAdapter(this);
        mRecyclerView.setAdapter(mResourceListAdapter);

        //retrieve extra values from intent starting this, sets Activity title
        Intent intentFromParent = getIntent();
        if (intentFromParent.hasExtra(Intent.EXTRA_TEXT)) {
            mResourceTopic = intentFromParent.getStringExtra(Intent.EXTRA_TEXT);
            setTitle(mResourceTopic);
        }

        loadDataFromViewModel();
    }

    /**
     * Retrieves LiveData List<Topic> from ViewModel and sets this
     * activity as LiveData Observer - updates adapter data on LiveData change
     */
    private void loadDataFromViewModel() {

        try {
            mLoadingIndicator.setVisibility(View.VISIBLE);

            ResourceViewModel model = ViewModelProviders.of(this).get(ResourceViewModel.class);

            if (mResourceTopic != null && !mResourceTopic.equals("")) {
                model.getTopicResources(mResourceTopic).observe(this, resourceList -> {
                    updateResourceListInUI(resourceList);
                });
            } else {
                model.getAllResources().observe(this, resourceList -> {
                    updateResourceListInUI(resourceList);
                });
            }

            showResourceListView();

        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage();
        } finally {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * updates List<Resource> in RecyclerView Adapter*
     * @param resourceList
     */
    private void updateResourceListInUI(List<Resource> resourceList) {
        mResourceListAdapter.setResourceData(resourceList);
    }

    /**
     * make the resource list visible and hide the error message.
     */
    private void showResourceListView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * make the error message visible and hide the resource list view
     */
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * called when the resource item is clicked
     * opens user-requested resource in appropriate app
     */
    @Override
    public void onResourceItemClick(String url) {
        openWebPage(url);
    }

    /**
     * create an Implicit Intent to open the url in appropriate app
     */
    public void openWebPage(String url) {
        //if no network connection available, inform user and return
        if (!Utilities.isOnline(this)) {
            Toast.makeText(this, R.string.string_no_network_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Uri webpage = Uri.parse(url);
            Intent webpageIntent = new Intent(Intent.ACTION_VIEW, webpage);
            if (webpageIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(webpageIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.string_an_error_occurred, Toast.LENGTH_LONG).show();
        }
    }
}