package com.tiptopgoodstudio.androidresources;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tiptopgoodstudio.androidresources.ui.HomeFragment;
import com.tiptopgoodstudio.androidresources.ui.InnerResourceFrag;
import com.tiptopgoodstudio.androidresources.ui.MainFragment;
import com.tiptopgoodstudio.androidresources.ui.ResourcesFragment;
import com.tiptopgoodstudio.androidresources.ui.SectionAdapter;
import com.tiptopgoodstudio.androidresources.ui.SettingsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnStickyClickListener {

    private BottomNavigationView mBottomNavigationView;
    private SectionAdapter mSectionAdapter;
    private ViewPager mViewPager;
    private MenuItem prevMenuItem;
    private List<Fragment> mFragList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the ViewPager with the section adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mSectionAdapter = new SectionAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionAdapter);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                mViewPager.setCurrentItem(0);

                                break;
                            case R.id.resources:
                                mViewPager.setCurrentItem(1);

                                break;
                            case R.id.settings:
                                mViewPager.setCurrentItem(2);

                                break;
                        }
                        return false;
                    }
                });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = mBottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onStickyClicked(int data){

            InnerResourceFrag innerFrag = InnerResourceFrag.newInstance(data);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, innerFrag);
            transaction.addToBackStack(null);
            transaction.commit();

            mSectionAdapter.notifyDataSetChanged();
    }

}
