package com.tiptopgoodstudio.androidresources.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tiptopgoodstudio.androidresources.R;

/*
*  Added on 04/13/2018 by Olga Agafonova.
*  Creates a custom view that contains an ImageView and a TextView
*  (the TextView is centered on top of ImageView)
* */
class StickyNoteView extends RelativeLayout
{
    private static final String TAG = HomeFragment.class.getSimpleName();

    private Drawable mStickyDrawable;
    private ImageView mStickyImageView;

    private String mStickyString;
    private TextView mStickyTextView;

    public StickyNoteView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.StickyNoteView, 0, 0);

        try {
            mStickyString = a.getString(R.styleable.StickyNoteView_stickyString);
            mStickyDrawable = a.getDrawable(R.styleable.StickyNoteView_stickyImage);
        }
        catch(Exception e)
        {
            Log.d(TAG, e.toString());
        }
        finally {
            a.recycle();
        }

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_view, this, true);

        mStickyImageView = (ImageView) getChildAt(0);
        if(mStickyDrawable != null) {
            mStickyImageView.setBackground(mStickyDrawable);
        }

        mStickyTextView = (TextView) getChildAt(1);
        mStickyTextView.setText(mStickyString);
    }

    public StickyNoteView(Context context) {
        this(context, null);
    }

}
