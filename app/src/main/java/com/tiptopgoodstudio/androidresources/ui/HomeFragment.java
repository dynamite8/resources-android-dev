package com.tiptopgoodstudio.androidresources.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiptopgoodstudio.androidresources.MainActivity;
import com.tiptopgoodstudio.androidresources.R;

/**
 * Updated on 04/17/2018 by Olga Agafonova
 */
public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private StickyNoteView mStickyView1;
    private StickyNoteView mStickyView2;
    private StickyNoteView mStickyView3;
    private StickyNoteView mStickyView4;

    OnStickyClickListener mCallback;

    /**
     * Main Activity must implement this interface
     */
    public interface OnStickyClickListener {
        public void onStickyClicked(int data);
    }

    /*
    * This makes sure that the container activity has implemented
    * the callback interface. If not, it throws an exception
    * */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = context instanceof Activity ? (Activity) context : null;

        try {
            mCallback = (OnStickyClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStickyClickListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mStickyView1 = (StickyNoteView) view.findViewById(R.id.customView1);
        mStickyView2 = (StickyNoteView) view.findViewById(R.id.customView2);
        mStickyView3 = (StickyNoteView) view.findViewById(R.id.customView3);
        mStickyView4 = (StickyNoteView) view.findViewById(R.id.customView4);

        setupStickyViews(view);

        return view;
    }

    /*
     * TODO replace selectedResource with a real value from the back-end
     * */
    private void setupStickyViews(View view) {
        try {

            mStickyView1.getStickyImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int selectedResource = 1;
                    mCallback.onStickyClicked(selectedResource);
                }
            });

            mStickyView2.getStickyImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int selectedResource = 2;
                    mCallback.onStickyClicked(selectedResource);
                }
            });

            mStickyView3.getStickyImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int selectedResource = 3;
                    mCallback.onStickyClicked(selectedResource);
                }
            });

            mStickyView4.getStickyImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int selectedResource = 4;
                    mCallback.onStickyClicked(selectedResource);
                }
            });
        }
        catch(Exception e) {
            //Log.d(TAG, e.toString());
        }
    }

}