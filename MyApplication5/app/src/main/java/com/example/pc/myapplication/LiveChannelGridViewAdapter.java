package com.example.pc.myapplication;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class LiveChannelGridViewAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public LiveChannelGridViewAdapter(Context c) {
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

        DisplayMetrics displayMetrics= mContext.getResources().getDisplayMetrics();
        int screen_width= displayMetrics.widthPixels;    //width of the device screen
        int screen_high = displayMetrics.heightPixels;    //width of the device screen

        int view_width = screen_width/5;   //width for imageview
        int view_high = screen_high/3;   //width for imageview
        Log.d("DAI_DEBUG","view_width = " + view_width);


        if (convertView == null) {
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(view_width, view_high));

            textView.setGravity(Gravity.CENTER);
           // textView.setSingleLine();
           // textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
              //      mContext.getResources().getDimension(R.dimen.result_font_small));

            //textView.setPadding(0, 20, 20, 0);
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
            R.drawable.tv1, R.drawable.tv2,
            R.drawable.tv3, R.drawable.tv4,
            R.drawable.tv5, R.drawable.tv6,
            R.drawable.tv7, R.drawable.tv8,
            R.drawable.tv4
    };

    // Keep all Images in array
    public String[] mThumbName = {
            "LiveTV",
            "CNN",
            "BBC",
            "VTC",
            "VTV3",
            "CNTV",
            "ABC",
            "HBO",
            "MAX",
            "SKY",
    };
}