package com.example.pc.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;


public class VideoStreamActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_stream);
        String vidAddress = "http://www.pocketjourney.com/downloads/pj/video/famous.3gp";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(vidAddress), "video/*");
        startActivity(intent);

    }

    private void alertTurnOnNetwork() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                VideoStreamActivity.this);

        alertDialogBuilder.setTitle("Network Conection");
        alertDialogBuilder.setInverseBackgroundForced(true);
        alertDialogBuilder
                .setMessage("Please turn on network")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (networkAvailable()) {
                            Intent i = new Intent(VideoStreamActivity.this, LoginActivity.class);
                            startActivity(i);
                        }else {
                            VideoStreamActivity.this.finish();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        VideoStreamActivity.this.finish();
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
