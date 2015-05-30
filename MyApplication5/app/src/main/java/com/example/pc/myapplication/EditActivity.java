package com.example.pc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import java.util.ArrayList;

public class EditActivity extends SherlockActivity {

    private static final String TAG = "Edit";
    EditEkAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_list_view);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);

        Button cancelButton = (Button) findViewById(R.id.action_next);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });

        // init listview
        adapter = new EditEkAdapter(this, GetDataForList(this));
        listView = (ListView) findViewById(R.id.list_view);

        listView.setDivider(getResources().getDrawable(
                android.R.color.transparent));
        listView.setDividerHeight(getResources().getDimensionPixelSize(
                R.dimen.padding));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

            /** implements OnClickListener **/
            public void onItemClick(AdapterView<?> a, View view, int position,
                    long id) {
                Log.i("listview", "listView.getCount(): " + listView.getCount());

                ItemData kuv = (ItemData) listView.getItemAtPosition(position);
                if (kuv.isCheck()) {
                    kuv.setCheck(false);
                } else {
                    kuv.setCheck(true);
                }

               // boolean isDelete = false;
                for (int i = 0; i < listView.getCount(); i++) {
                    ItemData kuv1 = (ItemData) listView.getItemAtPosition(i);

                    if (kuv1.isCheck()) {
                        //isDelete = true;
                    }
                }
//                if (isDelete) {
//                    deleteButton.setVisibility(View.VISIBLE);
//                } else {
//                    deleteButton.setVisibility(View.INVISIBLE);
//                }
                adapter.notifyDataSetChanged();
            }

        });

    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        //registerReceiver(mHandleDataReceiver, new IntentFilter(
                //MainActivity.REFRESH_DATA_ACTION));
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        //unregisterReceiver(mHandleDataReceiver);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        // super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    /**
     * Called to get data from database
     */
    private ArrayList<ItemData> GetDataForList(Context context) {
        Log.i(TAG, "Get kuv list in Offer");

        // get offer list from database
       // ArrayList<EkData> kuvList = EkDatabaseHelper.getCategoryHelper(
                //getApplicationContext()).getKuvList();
        ArrayList<EkData> kuvList = new ArrayList<EkData>();
        //Collections.reverse(kuvList);

        // map offer list to list view
        ArrayList<ItemData> arrayList = new ArrayList<ItemData>();
        for (EkData kuv : kuvList) {
            ItemData item = new ItemData(kuv.getMessage(), "");
            item.setId("");
            arrayList.add(item);
        }

        arrayList.add(new ItemData("City",""));
        arrayList.add(new ItemData("District",""));
        arrayList.add(new ItemData("Street",""));

        return arrayList;
    }
/*
    private final BroadcastReceiver mHandleDataReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Refresh Data in Offer");

            adapter.setListData(GetDataForList(context));
            adapter.notifyDataSetChanged();

        }
    };
*/
}
