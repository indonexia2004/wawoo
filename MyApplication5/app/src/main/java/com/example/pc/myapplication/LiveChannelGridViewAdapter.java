package com.example.pc.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

;

public class LiveChannelGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList imageUrlLIst;

    // Constructor
    public LiveChannelGridViewAdapter(Context c, ArrayList urlList) {
        mContext = c;
        imageUrlLIst = urlList;
    }

    public void setimageUrlList(ArrayList urlList) {
        imageUrlLIst = urlList;
    }

    public int getCount() {
        return imageUrlLIst.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    /*
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;

        DisplayMetrics displayMetrics= mContext.getResources().getDisplayMetrics();
        int screen_width= displayMetrics.widthPixels;    //width of the device screen
        int screen_high = displayMetrics.heightPixels;    //width of the device screen

        int view_width = screen_width/5;   //width for imageview
        int view_high = screen_high/3;   //width for imageview
        Log.d("DAI_DEBUG", "view_width = " + view_width);


        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(view_width, view_high));

            //imageView.setGravity(Gravity.CENTER);
           // textView.setSingleLine();
           // textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
              //      mContext.getResources().getDimension(R.dimen.result_font_small));

            //textView.setPadding(0, 20, 20, 0);
            //imageView.setBackgroundColor(Color.RED);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setText(mThumbName[position]);
        //imageView.setBackgroundResource(mThumbIds[position]);
        imageView.setBackgroundColor(Color.rgb(100, 100, 50));


        // If you are using normal ImageView
        if(imageUrlLIst.size() == 0){
            //imageView.setBackgroundResource(R.drawable.icon_error);
        } else {
            imageLoader.get((String)imageUrlLIst.get(position), new ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LiveChannelGridViewAdapter", "Image Load Error: " + error.getMessage());
                }

                @Override
                public void onResponse(ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        imageView.setImageBitmap(response.getBitmap());
                    }
                }
            });
        }


        return imageView;
    }*/
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;


        if (imageView == null) {
            imageView = new ImageView(mContext);
        }

        String url = (String)imageUrlLIst.get(position);

        Picasso.with(mContext)
                .load(url)
                .resize(250, 250)
                .transform(new CropSquareTransformation())
                //.placeholder(R.drawable.icon_loading)   // optional
                //.error(R.drawable.icon_error)
                .centerCrop()
                .into(imageView);

        return imageView;
    }

    public class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "square()"; }
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