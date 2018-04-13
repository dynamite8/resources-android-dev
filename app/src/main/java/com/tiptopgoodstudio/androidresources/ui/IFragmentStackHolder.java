package com.tiptopgoodstudio.androidresources.ui;

import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;

public interface IFragmentStackHolder {

    /**
     * Source: http://shawn-duan.com/android/fragment/2016/03/31/nested-fragment-management/
     *
     * @param containerId   in which container you want to replace your new fragment to.
     * @param frag          the new fragment used to replace
     * @param eltrans       a list of hints for cool transitions
     */
    void replaceFragment(int containerId, Fragment frag, ArrayList<Pair<View, String>> eltrans);
}
