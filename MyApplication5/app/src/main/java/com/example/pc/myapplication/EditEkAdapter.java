package com.example.pc.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

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

    public void setItemChecked(int position){
        clearCheckedAll();
        listData.get(position).setCheck(true);
    }

    public ItemData getItemChecked(){

        for (int i = 0; i < listData.size(); i++){
            if(listData.get(i).isCheck()) {
                Log.d("PlanActivity","the item is check multiple times i" + listData.get(i) );
                return listData.get(i);
            }
        }
        return null;
    }


    private void clearCheckedAll(){
        for (int i =0 ; i < listData.size(); i++){
            listData.get(i).setCheck(false);
        }
    }

    public int getCount() {
        return getListData().size();
    }

    public Object getItem(int position) {
        return getListData().get(position);
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
            rowHolder.tvTitle = (CheckedTextView) convertView
                    .findViewById(R.id.checkList);

            convertView.setTag(rowHolder);
        } else {
            rowHolder = (RowHolder) convertView.getTag();
        }

        ItemData itemData = getListData().get(position);
        rowHolder.tvTitle.setText(itemData.getTitle());
        rowHolder.tvTitle.setChecked(itemData.isCheck());

        return convertView;
    }

    public ArrayList<ItemData> getListData() {
        return listData;
    }

    public void setListData(ArrayList<ItemData> listData) {
        this.listData = listData;
    }

    static class RowHolder {
        CheckedTextView tvTitle;
    }
}
