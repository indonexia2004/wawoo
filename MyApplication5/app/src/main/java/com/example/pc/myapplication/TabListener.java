package com.example.pc.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

public class TabListener <T extends SherlockFragment> implements ActionBar.TabListener{
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;
    private final Context context;

    public TabListener(Context ctx, Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
        context = ctx;
    }

    public void onTabSelected(ActionBar.Tab  tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = SherlockFragment.instantiate(
                    mActivity, mClass.getName());
            //mFragment.setProviderId(mTag); // id for event provider
            ft.add(android.R.id.content, mFragment, mTag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }

    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
            Toast.makeText(context, "bUnselected!", Toast.LENGTH_LONG).show();
        }
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
        Toast.makeText(context, "Reselected!", Toast.LENGTH_LONG).show();
    }
}