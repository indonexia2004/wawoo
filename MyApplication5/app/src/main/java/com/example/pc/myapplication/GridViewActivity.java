package com.example.pc.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;


public class GridViewActivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gridview = (GridView)findViewById(R.id.gridview);
        gridview.setAdapter(new GridViewAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Send intent to SingleViewActivity
                //Intent i = new Intent(getApplicationContext(), SingleViewActivity.class);
                // Pass image index
                //i.putExtra("id", position);
                //startActivity(i);
                Toast.makeText(getApplicationContext(),
                        "Click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
