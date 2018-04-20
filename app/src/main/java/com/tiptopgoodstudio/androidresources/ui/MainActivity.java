package com.tiptopgoodstudio.androidresources.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiptopgoodstudio.androidresources.R;
import com.tiptopgoodstudio.androidresources.db.entity.Resources;

public class MainActivity extends AppCompatActivity {

    private StickyNoteView mStickyView1;
    private StickyNoteView mStickyView2;
    private StickyNoteView mStickyView3;
    private StickyNoteView mStickyView4;

    // Firebase references
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mResourcesDatabaseReference;
    private ChildEventListener mResourcesEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mStickyView1 = findViewById(R.id.customView1);
        mStickyView2 = findViewById(R.id.customView2);
        mStickyView3 = findViewById(R.id.customView3);
        mStickyView4 = findViewById(R.id.customView4);

        setupStickyViews();

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

    }

     /*
     *  Currently this method uses hardcoded StickyViews, but these sticky views need to dynamically
     *  generated based on number of topics. Then there would be one method to assign the onClicklistener
     *  to all the dynamically generated custom views. RecyclerView could be used to dynamically generate
     *  the sticky views as well as assign the onClickListeners
     *
     * TODO replace selectedResource with a real value from the back-end
     * */
    private void setupStickyViews() {
        try {

            mStickyView1.getStickyImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onStickyClicked(mStickyView1.getStickyText());
                }
            });

            mStickyView2.getStickyImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onStickyClicked(mStickyView2.getStickyText());
                }
            });

            mStickyView3.getStickyImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onStickyClicked(mStickyView3.getStickyText());
                }
            });

            mStickyView4.getStickyImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onStickyClicked(mStickyView4.getStickyText());
                }
            });
        }
        catch(Exception e) {
            //Log.d(TAG, e.toString());
        }
    }

    /**
     * This method will be called when the sticky note is clicked
     * This method creates an Explicit Intent to ResourceListActivity
     * and sends the topic as an Intent extra
     *
     */

    public void onStickyClicked(String topic){
        Intent intent = new Intent(this, ResourceListActivity.class);

        intent.putExtra(Intent.EXTRA_TEXT, topic);

        startActivity(intent);

    }

}
