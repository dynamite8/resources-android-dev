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

import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptopgoodstudio.androidresources.db.entity.Resources;

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

    // Firebase references
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mResourcesDatabaseReference;
    private ChildEventListener mResourcesEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionAdapter = new SectionAdapter(getSupportFragmentManager());

        // Firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mResourcesDatabaseReference = mFirebaseDatabase.getReference().child("resources");
        mResourcesEventListener = new ChildEventListener() {

            // method is called when first load and also on new resources being added
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // get the value at point in time to our matching entity
                // data is deserialized into Resources class
                Resources resource = dataSnapshot.getValue(Resources.class);
                System.out.println(resource.getResourceURL());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mResourcesDatabaseReference.addChildEventListener(mResourcesEventListener);

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
