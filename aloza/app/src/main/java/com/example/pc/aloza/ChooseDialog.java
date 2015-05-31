package com.example.pc.aloza;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockDialogFragment;

import java.util.ArrayList;


public class ChooseDialog extends SherlockDialogFragment {

    public static final int PLACE_DIALOG  = 0;
    public static final int STREET_DIALOG = 1;
    public static final int STORE_DIALOG  = 2;

    String selectedItem;
    int id;


    public ChooseDialog(int id){
        this.id = id;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final ArrayList<String> list = getDummyData(this.id);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Vui Lòng Chọn")
                .setSingleChoiceItems(list.toArray(new String[list.size()]), 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                selectedItem = list.get(arg1);
                                PlanActivity activity = (PlanActivity) getActivity();
                                activity.onFinishDialog(id, selectedItem);
                                getDialog().dismiss();
                            }
                        })
                /*.setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }) */;

        return builder.create();
    }

    private ArrayList<String> getDummyData(int id) {

        ArrayList<String> list = new ArrayList<>();

        switch (id){
            case PLACE_DIALOG:
                list.add("TP Ho Chi Minh");
                list.add("Ha Noi");
                list.add("Vung Tau");
                list.add("Nha Trang");
                list.add("Nha Trang");
                list.add("Nha Trang");
                list.add("Nha Trang");
                list.add("Nha Trang");
                list.add("Nha Trang");
                list.add("Nha Trang");
                list.add("Nha Trang");
                break;

            case STREET_DIALOG:
                list.add("Le Loi");
                list.add("Quang Trung");
                list.add("Quang Trung");
                list.add("Ut Tich");
                list.add("Quang Trung");
                list.add("Quang Trung");
                list.add("Cao Thang");
                break;

            case STORE_DIALOG:
                list.add("Hoa Lan Anh");
                list.add("Adidas Store");
                list.add("Puma Store");
                list.add("Nike Store");
                list.add("C&K");
                break;

            default:
                Log.e("ChooseDialog", "wrong id: " + id);
                break;
        }
        return list;
    }
}
