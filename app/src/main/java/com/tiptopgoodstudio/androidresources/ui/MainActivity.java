package com.tiptopgoodstudio.androidresources.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.Utilities;
import com.tiptopgoodstudio.androidresources.ui.adapters.StickyNoteAdapter;
import com.tiptopgoodstudio.androidresources.viewmodel.TopicViewModel;

public class MainActivity extends AppCompatActivity implements StickyNoteAdapter.ResourceClickListener {

    private RecyclerView mRecyclerView;
    private StickyNoteAdapter mStickyNoteAdapter;
    private View mSplashScreen;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUiViews();
        setupActivityAppBar();
        setupRecyclerView();
        setupActivityAsDataObserver();
    }

    private void initializeUiViews() {
        mToolbar = findViewById(R.id.action_toolbar_main);
        mRecyclerView = findViewById(R.id.rv_stickynotes);
        mSplashScreen = findViewById(R.id.splash_screen);
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mStickyNoteAdapter = new StickyNoteAdapter(this);
        mRecyclerView.setAdapter(mStickyNoteAdapter);
    }

    private void setupActivityAppBar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorMenuItem));
        setSupportActionBar(mToolbar);
    }

    /**
     * This method retrieves data from the ViewModel
     */
    private void setupActivityAsDataObserver() {

        TopicViewModel mTopicViewModel = ViewModelProviders.of(this).get(TopicViewModel.class);

        mTopicViewModel.getTopicsList().observe(this, topicList -> {
            //Update UI
            mStickyNoteAdapter.setTopicData(topicList);
            if (mStickyNoteAdapter.getItemCount() == 0) {
                showSplashScreen();
                if (!Utilities.isOnline(this)) {
                    Toast.makeText(this, "There is no network connection!", Toast.LENGTH_SHORT).show();
                }
            } else {
                showTopicsView();
            }
        });
    }

    /**
     * When topic listed in StickyNote RecyclerView is selected, ResourceListActivity is
     * called - ResourceListActivity displays list of selected topic resources
     *
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

    //display views helper methods
    private void showTopicsView() {
        mSplashScreen.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showSplashScreen() {
        mRecyclerView.setVisibility(View.GONE);
        mSplashScreen.setVisibility(View.VISIBLE);
    }

}
