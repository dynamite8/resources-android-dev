package com.tiptopgoodstudio.androidresources.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.db.entity.Topic;

import java.util.List;

public class SitckyNoteAdapter extends RecyclerView.Adapter<SitckyNoteAdapter.SitckyNoteAdapterViewHolder> {

    private List<Topic> mTopicData;

    final private SitckyNoteAdapter.ResourceClickListener mOnClickListener;

    public SitckyNoteAdapter(SitckyNoteAdapter.ResourceClickListener listener) {
        mOnClickListener = listener;
    }

    public interface ResourceClickListener {
        void onTopicItemClick(String data);
    }

    @Override
    public SitckyNoteAdapter.SitckyNoteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.custom_view, parent, false);
        SitckyNoteAdapter.SitckyNoteAdapterViewHolder viewHolder = new SitckyNoteAdapter.SitckyNoteAdapterViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SitckyNoteAdapter.SitckyNoteAdapterViewHolder holder, int position) {

        Topic currentTopic = mTopicData.get(position);

        String topicName = currentTopic.getTopicName();
        int stickyImage = R.drawable.ic_note;

        holder.mTopicDescription.setText(topicName);
        holder.mStickyIcon.setImageResource(stickyImage);
        holder.mStickyIcon.setContentDescription(topicName);

    }

    @Override
    public int getItemCount() {
        if(mTopicData == null) {
            return 0;
        }
        return mTopicData.size();
    }

    public void setTopicData(List<Topic> topicData) {
        mTopicData = topicData;
        notifyDataSetChanged();
    }

    public class SitckyNoteAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView mTopicDescription;
        public final ImageView mStickyIcon;


        public SitckyNoteAdapterViewHolder(View itemView) {
            super(itemView);
            mTopicDescription = (TextView) itemView.findViewById(R.id.stickyNoteText);
            mStickyIcon = (ImageView) itemView.findViewById(R.id.stickyNote);
            mStickyIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            String topicName = mTopicData.get(clickedPosition).getTopicName();
            mOnClickListener.onTopicItemClick(topicName);
        }
    }

}
