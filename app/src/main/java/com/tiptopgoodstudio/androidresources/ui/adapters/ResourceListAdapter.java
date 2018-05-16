package com.tiptopgoodstudio.androidresources.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.List;

/**
 * Adapter for ResourceListActivity RecyclerView
 */
public class ResourceListAdapter
        extends RecyclerView.Adapter<ResourceListAdapter.ResourceListAdapterViewHolder> {

    public static final String RESOURCE_TYPE_PDF = "pdf";
    public static final String RESOURCE_TYPE_VIDEO = "video";
    public static final String RESOURCE_TYPE_IMAGE = "image";
    public static final String RESOURCE_TYPE_URL = "url";

    // A list of com.tiptopgoodstudio.androidresources.db.entity.Resource items
    private List<Resource> mResourceData;

    final private ResourceClickListener mOnClickListener;

    public ResourceListAdapter(ResourceClickListener listener) {
        mOnClickListener = listener;
    }

    public interface ResourceClickListener {
        void onResourceItemClick(String url);
    }

    @Override
    public ResourceListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.resource_item, parent, false);
        return new ResourceListAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResourceListAdapterViewHolder holder, int position) {
        Resource currentResource = mResourceData.get(position);

        holder.mResourceUrl.setText(currentResource.getResourceUrl());
        holder.mResourceDescription.setText(currentResource.getResourceDescription());

        String resourceFormat = currentResource.getResourceFormat();
        int resourceId = R.drawable.resource_url_icon;
        if (resourceFormat == null) {
            resourceFormat = RESOURCE_TYPE_URL;
        } else {
            switch (resourceFormat) {
                case RESOURCE_TYPE_PDF:
                    resourceId = R.drawable.resource_pdf_icon;
                    break;
                case RESOURCE_TYPE_VIDEO:
                    resourceId = R.drawable.resource_video_icon;
                    break;
                case RESOURCE_TYPE_IMAGE:
                    resourceId = R.drawable.resource_image_icon;
                    break;
                default:
                    resourceId = R.drawable.resource_url_icon;
                    break;
            }
        }
        holder.mResourceIcon.setImageResource(resourceId);
        holder.mResourceIcon.setContentDescription(resourceFormat);
    }

    @Override
    public int getItemCount() {
        if (mResourceData == null) {
            return 0;
        }
        return mResourceData.size();
    }

    public void setResourceData(List<Resource> resourceData) {
        mResourceData = resourceData;
        notifyDataSetChanged();
    }

    public class ResourceListAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView mResourceDescription;
        public final TextView mResourceUrl;
        public final ImageView mResourceIcon;


        public ResourceListAdapterViewHolder(View itemView) {
            super(itemView);
            mResourceDescription = (TextView) itemView.findViewById(R.id.tv_resource_description);
            mResourceUrl = (TextView) itemView.findViewById(R.id.tv_resource_url);
            mResourceIcon = (ImageView) itemView.findViewById(R.id.resource_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            String url = mResourceData.get(clickedPosition).getResourceUrl();
            mOnClickListener.onResourceItemClick(url);
        }
    }
}
