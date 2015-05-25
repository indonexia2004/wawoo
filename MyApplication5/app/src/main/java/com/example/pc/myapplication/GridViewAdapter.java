package com.example.pc.myapplication;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public GridViewAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return FOLDER_NUMBER;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(210, 190));
            textView.setGravity(Gravity.CENTER);
            textView.setSingleLine();
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    mContext.getResources().getDimension(R.dimen.result_font));

            //imageView.setPadding(4, 4, 4, 4);
            //imageView.setBackgroundColor(Color.RED);
        } else {
            textView = (TextView) convertView;
        }

        textView.setText(mThumbName[position]);
        textView.setBackgroundResource(mThumbIds[position]);
        //imageView.setBackgroundColor(Color.rgb(100, 100, 50));
        return textView;
    }

    public static int FOLDER_NUMBER = 9;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.color_1, R.drawable.color_2,
            R.drawable.color_3, R.drawable.color_4,
            R.drawable.color_5, R.drawable.color_6,
            R.drawable.color_7, R.drawable.color_8,
            R.drawable.color_3
    };

    // Keep all Images in array
    public String[] mThumbName = {
            "General",
            "Pop",
            "Radio",
            "Talk",
            "Music",
            "Sport",
            "Entertain",
            "Movies",
            "News",
            "Others",
    };
}