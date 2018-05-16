package com.tiptopgoodstudio.androidresources.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.ui.adapters.SitckyNoteAdapter;
import com.tiptopgoodstudio.androidresources.viewmodel.TopicViewModel;

public class MainActivity extends AppCompatActivity implements SitckyNoteAdapter.ResourceClickListener {

    private RecyclerView mRecyclerView;
    private SitckyNoteAdapter mSitckyNoteAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_toolbar_main);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorMenuItem));
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_stickynotes);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessage = (TextView) findViewById(R.id.main_error_message_display);

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mSitckyNoteAdapter = new SitckyNoteAdapter(this);
        mRecyclerView.setAdapter(mSitckyNoteAdapter);

        // Load the topics list from the ViewModel
        loadDataFromViewModel();
    }

    /**
     * This method retrieves data from the ViewModel
     *
     * Added by Divya on 4/21/2018
     */
    private void loadDataFromViewModel(){

        try {

            showProgress();

            TopicViewModel model = ViewModelProviders.of(this).get(TopicViewModel.class);

            model.getTopicsList().observe(this, topicList -> {
                //Update UI
                mSitckyNoteAdapter.setTopicData(topicList);
                showTopicsView();
            });

        } catch(Exception e) {
            e.printStackTrace();
            showErrorMessage();
        } finally {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * This method will show the progress
     *
     * Added by Divya on 4/21/2018
     */
    private void showProgress() {

        // First, make the error textview is gone
        mErrorMessage.setVisibility(View.GONE);

        // Then, make sure the recycler view list is gone
        mRecyclerView.setVisibility(View.GONE);

        //Now show the progress bar
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the resource list visible and
     * hide the error message.
     *
     * Added by Divya on 4/21/2018
     */
    private void showTopicsView() {

         // First, make the error invisible
        mErrorMessage.setVisibility(View.INVISIBLE);

        // Then, make sure the recycler view list visible
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the topic sticky notes
     *
     * Added by Divya on 4/21/2018.
     */
    private void showErrorMessage() {

        // First, hide the currently visible data
        mRecyclerView.setVisibility(View.GONE);

        // Then, show the error
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    /**
     * When topic listed in StickyNote RecyclerView is selected, ResourceListActivity is
     * called - Activity displays list of selected topic resources
     * @param data
     */
    @Override
    public void onTopicItemClick(String data) {
        Intent intent = new Intent(this, ResourceListActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, data);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:

                Intent intent = new Intent(this, SettingsActivity.class);

                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
