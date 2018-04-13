package com.tiptopgoodstudio.androidresources.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.ui.HomeFragment;

public class SettingsFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        return view;
    }

}
