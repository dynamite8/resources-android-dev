package com.tiptopgoodstudio.androidresources; /**
 * Created by Divya on 3/3/2018.
 * For GrowWithGoogleChallengeScholarship Women In Technology Learning Project
 * Android Dev Resources
 *
 * This class creates the adapter for the items in the Recycler View
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResourceListAdapter
        extends RecyclerView.Adapter<ResourceListAdapter.ResourceListAdapterViewHolder>{

    private String [] mResourceData;

    public ResourceListAdapter() {

    }

    @Override
    public ResourceListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.resource_item, parent, false);
        ResourceListAdapterViewHolder viewHolder = new ResourceListAdapterViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ResourceListAdapterViewHolder holder, int position) {
        holder.mResourceTextView.setText(mResourceData[position]);
    }

    @Override
    public int getItemCount() {
        if(mResourceData == null) {
            return 0;
        }
        return mResourceData.length;
    }

    public void setResourceData(String [] resourceData) {
        mResourceData = resourceData;
        notifyDataSetChanged();
    }

    public class ResourceListAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mResourceTextView;

        public ResourceListAdapterViewHolder(View itemView) {
            super(itemView);
            mResourceTextView = (TextView) itemView.findViewById(R.id.tv_resource_title);
        }
    }

}
