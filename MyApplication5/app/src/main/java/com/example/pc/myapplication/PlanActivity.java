package com.example.pc.myapplication;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ic_wawoologo);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_title);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //planData = new JSONArray();
        //PlanDialog m = new PlanDialog(getPlanList(planData));
       // m.show(getSupportFragmentManager(), "PlanDialog");
        plansPrepaid(null);

    }

    //private String plans_prepaid = "http://192.168.56.101:3000/db";
    private String urlBase = "https://41.204.245.244:80/tbcplatform/api/v1/";
    private String plans_prepaid = urlBase + "plans?planType=prepaid";
    private String orders = urlBase + "orders/";

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

    /**
     * Method to make json object request where json response starts wtih {
     * */
    public JSONObject createOrdersMapperObject(JSONObject object)
    {
        JSONObject request=new JSONObject();
        try {

            request.put("planCode","4");
            request.put("paytermCode","Monthly");
            request.put("dateFormat","dd MMMM yyyy");
            request.put("contractPeriod","1");
            request.put("isNewplan","true");
            request.put("start_date","02 June 2014");
            request.put("locale","en");
            request.put("billAlign", "false");

            Log.d("PlanActivity", "createDeviceMapperObject: " + request.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    public void orders(JSONObject object, String clientId) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                orders+ clientId, createOrdersMapperObject(null), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("PlanActivity", response.toString());
                try{
                    JSONObject resourceId = response.getJSONObject("resourceId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PlanActivity", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
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
