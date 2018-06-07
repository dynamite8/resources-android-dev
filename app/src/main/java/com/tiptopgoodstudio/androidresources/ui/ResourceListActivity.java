package com.tiptopgoodstudio.androidresources.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.Utilities;
import com.tiptopgoodstudio.androidresources.ui.adapters.ResourceListAdapter;
import com.tiptopgoodstudio.androidresources.viewmodel.ResourceViewModel;

/**
 * Activity displays Android resource list filtered by user-selected topic
 */
public class ResourceListActivity extends AppCompatActivity
        implements ResourceListAdapter.ResourceClickListener {

    private RecyclerView mRecyclerView;
    private ResourceListAdapter mResourceListAdapter;
    private String mResourceTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_list);

        //retrieve extra values from intent starting this, sets Activity title
        Intent intentFromParent = getIntent();
        if (intentFromParent.hasExtra(Intent.EXTRA_TEXT)) {
            mResourceTopic = intentFromParent.getStringExtra(Intent.EXTRA_TEXT);
            setTitle(mResourceTopic);
        }

        setupRecyclerView();
        setupActivityAsDataObserver();
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_resource_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mResourceListAdapter = new ResourceListAdapter(this);
        mRecyclerView.setAdapter(mResourceListAdapter);
    }

    private void setupActivityAsDataObserver() {
        ResourceViewModel model = ViewModelProviders.of(this).get(ResourceViewModel.class);
        if (mResourceTopic != null && !mResourceTopic.equals("")) {
            model.getTopicResources(mResourceTopic).observe(this, resourceList -> {
                mResourceListAdapter.setResourceData(resourceList);
            });
        } else {
            model.getAllResources().observe(this, resourceList -> {
                mResourceListAdapter.setResourceData(resourceList);
            });
        }
    }

    /**
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