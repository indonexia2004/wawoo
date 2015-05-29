package com.example.pc.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class TabHighRateFragment extends SherlockFragment {
    // your member variables here
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tabview_fragment, container, false);

        GridView gridview = (GridView)view.findViewById(R.id.gridview);
        gridview.setAdapter(new LiveChannelGridViewAdapter(getActivity().getApplicationContext(),null));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Toast.makeText(getActivity().getApplicationContext(),
                        "Click", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
