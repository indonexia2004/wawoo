package com.example.pc.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockDialogFragment;

import java.util.ArrayList;


public class PlanDialog extends SherlockDialogFragment {

    private ArrayList<String> planList;

    public PlanDialog(ArrayList<String> planData){
        super();
        planList = planData;
    }
/*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);

        final ArrayList mSelectedItems = new ArrayList();  // Where we track the selected items


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("hahaha")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(planList.toArray(new String[planList.size()]), 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // If the user checked the item, add it to the selected items
                                mSelectedItems.add(arg1);
                            }
                        })
                        // Set the action buttons
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        ((PlanActivity)getActivity()).orders(null, "334",null);
                        Intent i = new Intent(getActivity(), LiveChannelGridViewActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog dialog = builder.create();
        Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if(b != null)
            b.setBackgroundColor(Color.parseColor("#ffff4444"));
        return dialog;
    }
*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Please turn on network")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        ((PlanActivity)getActivity()).orders(null, "334",null);
                        Intent i = new Intent(getActivity(), LiveChannelGridViewActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog dialog = builder.create();
        Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if(b != null)
            b.setBackgroundColor(Color.parseColor("#ffff4444"));
        return dialog;
    }
}
