package com.example.pc.aloza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An adapter class to adapt the data for the offer list view
 */
public class EditEkAdapter extends BaseAdapter {

    Context context;
    private ArrayList<ItemData> listData;

    public EditEkAdapter(Context context, ArrayList<ItemData> listData) {
        this.context = context;
        this.setListData(listData);
    }

    public void setItem(int postion, String content) {
        listData.get(postion).setContent(content);
    }

    public void setListData(ArrayList<ItemData> listData) {
        this.listData = listData;
    }


    public int getCount() {
        return listData.size();
    }

    public Object getItem(int position) {
        return listData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        RowHolder rowHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowHolder = new RowHolder();

            convertView = inflater.inflate(R.layout.edit_list_item, null);
            rowHolder.tvTitle = (TextView) convertView.findViewById(R.id.tView_title);
            rowHolder.tvContent = (TextView) convertView.findViewById(R.id.tView_content);

            convertView.setTag(rowHolder);
        } else {
            rowHolder = (RowHolder) convertView.getTag();
        }

        ItemData itemData = listData.get(position);
        rowHolder.tvTitle.setText(itemData.getTitle());
        rowHolder.tvContent.setText(itemData.getContent());

        return convertView;
    }



    static class RowHolder {
        TextView tvTitle;
        TextView tvContent;
    }
}
