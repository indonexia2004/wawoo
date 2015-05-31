package com.example.pc.aloza;

import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;


public class MapActivity extends SherlockFragmentActivity {

    AloraMapFragment mapFragment;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_title);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);

        mapFragment = (AloraMapFragment)getFragmentManager().findFragmentById(R.id.map);

        if(latLng == null){
            google_api_get_latlng_from_address("");
        }else {
            mapFragment.addMarker(latLng);
        }

    }


    private void google_api_get_latlng_from_address(String address) {

        String api = "http://maps.googleapis.com/maps/api/geocode/json?address=";
       //address = "1 Tran Hung Dao, Nguyen Cu Trinh, Quan 1, Ho Chi Minh, Viet Nam";
        address ="";
        String sensor="&sensor=false";
        String url = api + address.replaceAll(" ", "%20") + sensor;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("MapActivity", response.toString());
                try{

                JSONObject latlng = response.getJSONArray("results")
                                            .getJSONObject(0)
                                            .getJSONObject("geometry")
                                            .getJSONObject("location");

                latLng = new LatLng(latlng.getDouble("lat"),latlng.getDouble("lng"));
                mapFragment.addMarker(latLng);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MapActivity", "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
