package com.example.pc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
    private JSONArray planData = new JSONArray();
    private static final String TAG = "PlanActivity";
    EditEkAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_list_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_title);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);

        plansPrepaid(null);

        // init listview
        adapter = new EditEkAdapter(this, GetDataForList(this));
        listView = (ListView) findViewById(R.id.list_view);

        listView.setDivider(getResources().getDrawable(
                android.R.color.holo_orange_dark));
        listView.setDividerHeight(getResources().getDimensionPixelSize(
                R.dimen.divider));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /** implements OnClickListener **/
            public void onItemClick(AdapterView<?> a, View view, int position,
                                    long id) {
                Log.i("listview", "position: " + position);

                ItemData kuv = (ItemData) listView.getItemAtPosition(position);
                adapter.setItemChecked(position);
                adapter.notifyDataSetChanged();
            }

        });

        Button nextButton = (Button) findViewById(R.id.action_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientId = getApplicationContext().getSharedPreferences("WAWOO", MODE_PRIVATE)
                        .getString("CLIENT_ID", "334");
                ItemData item = adapter.getItemChecked();
                if (item == null) {
                    return;
                }
                orders(null, clientId, item);

                Intent intent = new Intent(getApplicationContext(),
                        InstructionActivity.class);
                startActivity(intent);
            }
        });

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
                            adapter.setListData(GetDataForList(getApplicationContext()));
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
    public JSONObject createOrdersMapperObject(JSONObject object, ItemData item)
    {
        JSONObject request=new JSONObject();
        try {

            request.put("planCode",item.getId());
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

    public void orders(JSONObject object, String clientId,ItemData item) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                orders+ clientId, createOrdersMapperObject(null, item), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("PlanActivity", response.toString());
                try{
                    String resourceId = response.getString("resourceId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PlanActivity", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage() + "can't choose plan", Toast.LENGTH_SHORT).show();
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

    private ArrayList<ItemData> getPlanList(JSONArray plandata){
        ArrayList<ItemData> ret = new ArrayList();
        for(int i=0 ; i< plandata.length(); i++) {
            try{
                ret.add(new ItemData(plandata.getJSONObject(i).getString("planCode"),
                             plandata.getJSONObject(i).getString("id")));
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d(PlanDialog.class.getName(), ret.toString());
        return ret;
    }

    /**
     * Called to get data from database
     */
    private ArrayList<ItemData> GetDataForList(Context context) {
        Log.i(TAG, "Get kuv list in Offer");

        ArrayList<ItemData> kuvList = getPlanList(planData);

        ArrayList<ItemData> arrayList = new ArrayList<ItemData>();
        for (ItemData kuv : kuvList) {
            ItemData item = new ItemData(kuv.getTitle(), kuv.getId());
            if("5".equals(kuv.getId())) {
                item.setCheck(true);
            }
            arrayList.add(item);
        }

        if(arrayList.size() == 0){
            ItemData item =  new ItemData("Free Trial","5");
            item.setCheck(true);
            arrayList.add(item);
        }

        return arrayList;
    }
}
