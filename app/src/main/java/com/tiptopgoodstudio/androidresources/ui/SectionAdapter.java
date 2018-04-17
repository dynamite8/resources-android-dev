package com.tiptopgoodstudio.androidresources.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.tiptopgoodstudio.androidresources.R;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Agafonova on 3/17/18.
 */

public class SectionAdapter extends FragmentStatePagerAdapter {

    private static int NUM_ITEMS = 3;

    public SectionAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0)
            return new MainFragment();
        else if (position == 1)
            return new ResourcesFragment();
        else if (position == 2)
            return new SettingsFragment();
        else
            return null;
    }

    @Override
    public int getCount()  {
      return NUM_ITEMS;
    }
}
