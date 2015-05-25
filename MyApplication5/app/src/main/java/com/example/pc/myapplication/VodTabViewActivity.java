package com.example.pc.myapplication;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class VodTabViewActivity extends SherlockFragmentActivity {
    // store the active tab here
    public static String ACTIVE_TAB = "activeTab";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voa_tabview);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // add the first tab which is an instance of TabFragment1
        ActionBar.Tab  tab1 = actionBar.newTab()
                .setText("TabTitle1")
                .setTabListener(new TabListener(getApplicationContext(),
                        this, "tab1", TabHighRateFragment.class));
        actionBar.addTab(tab1);

        // add the second tab which is an instance of TabFragment2
        ActionBar.Tab tab2 = actionBar.newTab()
                .setText("TabTitle2")
                .setTabListener(new TabListener(getApplicationContext(),
                        this, "tab2", TabHighRateFragment.class));
        actionBar.addTab(tab2);

        // add the second tab which is an instance of TabFragment2
        ActionBar.Tab tab3 = actionBar.newTab()
                .setText("TabTitle3")
                .setTabListener(new TabListener(getApplicationContext(),
                        this, "tab2", TabHighRateFragment.class));
        actionBar.addTab(tab3);

        // add the second tab which is an instance of TabFragment2
        ActionBar.Tab tab4 = actionBar.newTab()
                .setText("TabTitle4")
                .setTabListener(new TabListener(getApplicationContext(),
                        this, "tab2", TabHighRateFragment.class));
        actionBar.addTab(tab4);

        // check if there is a saved state to select active tab
        if( savedInstanceState != null ){
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(ACTIVE_TAB));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // save active tab
        outState.putInt(ACTIVE_TAB,
                getSupportActionBar().getSelectedNavigationIndex());
        super.onSaveInstanceState(outState);
    }
}