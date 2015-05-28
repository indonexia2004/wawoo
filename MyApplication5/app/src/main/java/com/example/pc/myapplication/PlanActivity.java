package com.example.pc.myapplication;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
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


public class PlanActivity extends SherlockFragmentActivity {
    private JSONArray planData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.actionbar_title);

        //Intent i = new Intent(this,PlanActivity.class);
        //startActivity(i);
        plansPrepaid(null);


        /*
        if(networkAvailable()){
            Intent i = new Intent(this,GridViewActivity.class);
            startActivity(i);
        }else {
            //alertTurnOnNetwork();
            Intent i = new Intent(this,GridViewActivity.class);
            startActivity(i);
            Toast.makeText(this, "Please turn on network", Toast.LENGTH_LONG).show();
        }*/
    }

    private String plans_prepaid = "http://192.168.56.101:3000/db";
    //private String plans_prepaid = urlBase + "plans?planType=prepaid";

    private void plansPrepaid(JSONObject object) {

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(plans_prepaid,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(PlanDialog.class.getName(), response.toString());
                        try
                        {
                            planData = response;
                            PlanDialog m = new PlanDialog(getPlanList(response));
                            m.show(getSupportFragmentManager(), "heyhey");

                            JSONObject resourceId = response.getJSONObject(1);

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(PlanDialog.class.getName(), "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),
                //error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        }){

            /**
             * Passing some request headers
             * */
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

    private ArrayList getPlanList(JSONArray plandata){
        ArrayList ret = new ArrayList();
        for(int i=0 ; i< plandata.length(); i++) {
            try{
                ret.add(plandata.getJSONObject(i).getString("planCode"));
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d(PlanDialog.class.getName(), ret.toString());
        return ret;
    }
}
