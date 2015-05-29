package com.example.pc.myapplication;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LiveChannelGridViewActivity extends SherlockActivity {

    private JSONArray channelData;
    LiveChannelGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_channel_grid_view);
        JSONArray channelData = new JSONArray();
        channel(null);

        adapter = new LiveChannelGridViewAdapter(this, getImageUrlList(channelData));
        GridView gridview = (GridView)findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Toast.makeText(getApplicationContext(),
                        "Click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String plans_prepaid = "http://192.168.56.101:3000/db";
    //private String urlBase = "https://41.204.245.244:80/tbcplatform/api/v1/";
    //private String plans_prepaid = urlBase + "planservices/334?serviceType=IPTV";

    private void channel(JSONObject object) {

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(plans_prepaid,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(PlanDialog.class.getName(), response.toString());
                        try
                        {
                            channelData = response;
                            getImageUrlList(channelData);
                            //PlanDialog m = new PlanDialog(getPlanList(response));
                            //m.show(getSupportFragmentManager(), "heyhey");                            adapter.setimageUrlList(getImageUrlList(channelData));
                            adapter.setimageUrlList(getImageUrlList(channelData));
                            adapter.notifyDataSetChanged();
                            JSONObject resourceId = response.getJSONObject(1);

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(PlanDialog.class.getName(), "Error: " + error.getMessage());

            }
        }){


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Tbc-Platform-TenantId", "Default");
                headers.put("Content-Type", "application/json;charset=utf-8");
                String creds = String.format("%s:%s","billing","password");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private ArrayList getImageUrlList(JSONArray channelList){
        ArrayList ret = new ArrayList();
        for(int i=0 ; i< channelList.length(); i++) {
            try{
                String url = channelList.getJSONObject(i).getString("image");
                ret.add(url);
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(PlanDialog.class.getName(), ret.toString());
        return ret;
    }
}
