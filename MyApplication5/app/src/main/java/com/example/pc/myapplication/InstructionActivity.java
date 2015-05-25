package com.example.pc.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;


public class InstructionActivity extends SherlockActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);

        /*
        if(networkAvailable()){
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
        }else {
            //alertTurnOnNetwork();
            Toast.makeText(this, "Please turn on network", Toast.LENGTH_LONG).show();
        }*/
    }

    private void alertTurnOnNetwork() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                InstructionActivity.this);

        alertDialogBuilder.setTitle("Network Conection");
        alertDialogBuilder.setInverseBackgroundForced(true);
        alertDialogBuilder
                .setMessage("Please turn on network")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (networkAvailable()) {
                            Intent i = new Intent(InstructionActivity.this, LoginActivity.class);
                            startActivity(i);
                        }else {
                            InstructionActivity.this.finish();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        InstructionActivity.this.finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

       //Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        //dialog.show();
    }

    private boolean networkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
