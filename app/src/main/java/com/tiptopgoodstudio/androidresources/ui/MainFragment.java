package com.tiptopgoodstudio.androidresources.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.ResourceListAdapter;
import com.tiptopgoodstudio.androidresources.db.entity.Resource;

import java.util.ArrayList;
import java.util.List;

/*
 *  Updated on 04/17/2018 by Olga Agafonova
 *
 *  Based on source code at https://github.com/danilao/fragments-viewpager-example/blob/master/res/layout/root_fragment.xml
 * */
public class MainFragment extends Fragment  {

    public static final String TAG = MainFragment.class.getSimpleName();
    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mFragView = inflater.inflate(R.layout.fragment_main, container, false);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        /*
         * When this Main Fragment is created, we will fill it with the Home Fragment
         */
        transaction.replace(R.id.fragment_container, new HomeFragment());

        transaction.commit();


        return mFragView;
    }
}

