package com.tiptopgoodstudio.androidresources.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.ResourceListAdapter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/*
 *  Added on 04/13/2018 by Olga Agafonova.
 *  The Main Fragment is the parent of Home Fragment
 *  I am nesting fragments because that is one way of displaying results when a sticky note is clicked;
 *  the other way would be to dynamically add and remove fragments from the viewpager (which contains Main, Resource and Settings fragments)
 *
 *  Source code: http://shawn-duan.com/android/fragment/2016/03/31/nested-fragment-management/
 * */
public class MainFragment extends Fragment implements IFragmentStackHolder {

    private static final String FRAG_TAG = "FRAG";
    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mFragView = inflater.inflate(R.layout.fragment_main, container, false);

        mFragmentManager = getChildFragmentManager();

        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.d(TAG, "onBackStackChanged()");
            }
        });

        return mFragView;
    }

    // set the first inner fragment in mainFragment
    private void setFragment(Fragment frag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // clear the back stack
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        int index = getActivity().getFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
        String backStackName = backEntry.getName();

        transaction.replace(R.id.inner_resource_frag_item, frag, backStackName);
        transaction.addToBackStack(backStackName);
        transaction.commit();
    }

    @Override
    public void replaceFragment(int containerId, Fragment frag, ArrayList<Pair<View, String>> eltrans) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        int index = getActivity().getFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
        String backStackName = backEntry.getName();

        transaction.replace(containerId, frag, backStackName);
        transaction.addToBackStack(backStackName);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (eltrans != null) {
            for (Pair<View, String> et : eltrans) {
                transaction.addSharedElement(et.first, et.second);
            }
        }
        transaction.commit();
    }
}

