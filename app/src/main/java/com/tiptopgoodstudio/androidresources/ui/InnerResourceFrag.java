package com.tiptopgoodstudio.androidresources.ui;


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
import android.widget.Toast;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.ResourceListAdapter;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 *  Added on 04/16/2018 by Olga Agafonova
 */
public class InnerResourceFrag extends Fragment implements ResourceListAdapter.ResourceClickListener {

    public static final String TAG = MainFragment.class.getSimpleName();
    private static final String RESOURCE_KEY = "RESOURCE_KEY";

    private RecyclerView mRecyclerView;
    private ResourceListAdapter mResourceListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inner_resource_frag, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.inner_resource_frag_item);

        if(mRecyclerView != null) {

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);

            mResourceListAdapter = new ResourceListAdapter(this);
            mRecyclerView.setAdapter(mResourceListAdapter);

            mResourceListAdapter.setResourceData(generateMockData("Blah"));
        }
        return view;
    }

    public static InnerResourceFrag newInstance (int selectedResource) {
        InnerResourceFrag fragment = new InnerResourceFrag();

        Bundle arguments = new Bundle();
        arguments.putInt(RESOURCE_KEY, selectedResource);
        fragment.setArguments(arguments);
        return fragment;
    }

    public void onResourceItemClick(String clickResult) {
        Toast toast = Toast.makeText(getContext(), clickResult, Toast.LENGTH_SHORT);
        toast.show();
    }

    private List<Resource> generateMockData(String stickyOption) {
        List<Resource> resourcesList = new ArrayList<Resource>();

        for(int i = 1; i <= 10; i++) {
            Resource currentResource = new Resource("Fake Resource " + (i % 4),
                    "Fake Resource " + i,stickyOption,
                    "url");
            resourcesList.add(currentResource);
        }

        return resourcesList;
    }
}
