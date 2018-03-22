package com.tiptopgoodstudio.androidresources;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga Agafonova on 3/17/18.
 */

public class SectionAdapter extends FragmentPagerAdapter{

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public SectionAdapter(FragmentManager manager) {
        super(manager);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount()  {
        return mFragmentList.size();
    }
}
