package com.example.pc.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.actionbarsherlock.app.SherlockFragmentActivity;


public class MainActivity extends SherlockFragmentActivity {

    private Handler mHandler = new Handler();
    private Runnable registerTask = new Runnable() {
        public void run() {
            Intent intent = new Intent(getApplicationContext(),
                    RegisterActivity.class);
            startActivity(intent);
        }
    };
    private Runnable liveChannelTask = new Runnable() {
        public void run() {
            Intent intent = new Intent(getApplicationContext(),
                    LiveChannelGridViewActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(getApplicationContext(),
//                LoginActivity.class);
//        startActivity(intent);

        handleNetworkState();
    }

    private void handleNetworkState(){
        if(isNetworkAvailable()){
            nextActivity();
        }else {
            alertTurnOnNetwork();
        }
    }

    private void nextActivity(){

        boolean register =  getSharedPreferences("WAWOO", Context.MODE_PRIVATE)
                .getBoolean("REGISTER", false);
        boolean login =  getSharedPreferences("WAWOO", Context.MODE_PRIVATE)
                .getBoolean("LOGIN", false);

        if(register||login){
            mHandler.postDelayed(liveChannelTask, 5000);
        }else {
            mHandler.postDelayed(registerTask, 5000);
        }
    }

    private void alertTurnOnNetwork() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        alertDialogBuilder.setTitle("Network Conection");
        alertDialogBuilder.setInverseBackgroundForced(true);
        alertDialogBuilder
                .setMessage("Please turn on network")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        handleNetworkState();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

       //Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        //dialog.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
