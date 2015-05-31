package com.example.pc.aloza;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import java.util.ArrayList;


public class PlanActivity extends SherlockFragmentActivity {

    private static final String TAG = "PlanActivity";
    EditEkAdapter adapter;
    ListView listView;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_title);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);


        // init listview
        adapter = new EditEkAdapter(this, GetDataForList(this));
        listView = (ListView) findViewById(R.id.list_view);

        listView.setDivider(getResources().getDrawable(
                R.color.green_dark));
        listView.setDividerHeight(getResources().getDimensionPixelSize(
                R.dimen.divider_padding));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /** implements OnClickListener **/
            public void onItemClick(AdapterView<?> a, View view, int position,
                                    long id) {
                Log.i("listview", "position: " + position);
                new ChooseDialog(position).show(fm,"ChooseDialog"+position);
            }

        });

        Button nextButton = (Button) findViewById(R.id.action_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MapActivity.class));
            }
        });
    }

    public void onFinishDialog(int position, String content) {
        Toast.makeText(this, "Hi, " + content, Toast.LENGTH_SHORT).show();
        adapter.setItem(position, content);
        adapter.notifyDataSetChanged();
    }

    /**
     * Called to get data from database
     */
    private ArrayList<ItemData> GetDataForList(Context context) {

        ArrayList<ItemData> list = new ArrayList<ItemData>();

        list.add(new ItemData("Địa Điểm",""));
        list.add(new ItemData("Đường",""));
        list.add(new ItemData("Cửa Hàng", ""));

        //Log.i(TAG, "Get data" + listView.toString());
        return list;
    }
}