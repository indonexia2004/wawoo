package com.example.pc.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VolleyActivity extends SherlockActivity {

    // json object response url
    private String urlJsonObj = "http://api.androidhive.info/volley/person_object.json";

    // json array response url
    private String urlJsonArry = "http://api.androidhive.info/volley/person_array.json";

    private static String TAG = VolleyActivity.class.getSimpleName();
    private Button btnMakeObjectRequest, btnMakeArrayRequest;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        btnMakeObjectRequest = (Button) findViewById(R.id.btnObjRequest);
        btnMakeArrayRequest = (Button) findViewById(R.id.btnArrayRequest);
        txtResponse = (TextView) findViewById(R.id.txtResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json object request
                makeJsonObjectRequest();
            }
        });

        btnMakeArrayRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeJsonArrayRequest();
            }
        });

    }

    private String urlBase = "https://41.204.245.244:80/tbcplatform/api/v1/";
    private String template = urlBase + "clients/template";
    private String clients = urlBase + "clients";
    /**
     * Method to make json object request where json response starts wtih {
     * */

    private void makeJsonObjectRequest() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                template, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String officeId = response.getString("officeId");
                    JSONObject addressTemplateData = response.getJSONObject("addressTemplateData");
                    JSONArray countryData = addressTemplateData.getJSONArray("countryData");
                    JSONArray stateData = addressTemplateData.getJSONArray("stateData");
                    JSONArray cityData = addressTemplateData.getJSONArray("cityData");

                    String country = countryData.getString(0);
                    String state = stateData.getString(0);
                    String city = cityData.getString(0);

                    JSONObject location = new JSONObject();

                    location.put("officeId",officeId);
                    location.put("country",country);
                    location.put("state",country);
                    location.put("city",country);

                    String jsonResponse = "";
                    jsonResponse += "country: " + country + "\n\n";
                    jsonResponse += "state: " + state + "\n\n";
                    jsonResponse += "city: " + city + "\n\n";

                    Log.d(TAG, jsonResponse);
                    client(location);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        }){

            /*@Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "billing");
                params.put("password", "password");
                return params;
            }*/

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
    /*{
            "officeId": "1",
            "phone": "6345656765",
            "middlename": "",
            "clientCategory": "20",
            "locale": "en",
            "street": "#23",
            "state": "Andhra Pradesh",
            "lastname": "dfg",
            "firstname": "dfgfd",
            "activationDate": "",
            "externalId":"",
            "city": "Hyderabad",
            "country": "India",
            "flag": "false",
            "email": "dsf@wqq.com",
            "zipCode": "436346",
            "active": "false",
            "dateFormat": "dd MMMM yyyy",
            "addressNo": "ghcv",
            "fullname": ""
    }*/
    public static JSONObject createUserMapperObejct(JSONObject location)
    {
        JSONObject request=new JSONObject();
        try {
            request.put("officeId",location.getString("country"));
            request.put("phone","6345656765");
            request.put("middlename","");
            request.put("clientCategory","20");
            request.put("locale","en");
            request.put("lastname","");
            request.put("firstname","");
            request.put("externalId","");
            request.put("city","");
            request.put("flag","false");
            request.put("zipCode","436346");
            request.put("active","false");
            request.put("dateFormat","dd MMMM yyyy");
            request.put("addressNo","ghcv");
            request.put("street","#32");


            request.put("officeId",location.getString("officeId"));
            request.put("country",location.getString("country"));
            request.put("state",location.getString("state"));
            request.put("city",location.getString("city"));
            request.put("email","indo7@email.com");
            request.put("fullname","Bui Xuan Dai");
            Log.d(TAG, "createUserMapperObejct: " + request.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    private void client(JSONObject location) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                clients, createUserMapperObejct(location), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                /*
                try {
                    // Parsing json object response
                    // response will be a json object
                    JSONObject addressTemplateData = response.getJSONObject("addressTemplateData");
                    JSONArray countryData = addressTemplateData.getJSONArray("countryData");
                    JSONArray stateData = addressTemplateData.getJSONArray("stateData");
                    JSONArray cityData = addressTemplateData.getJSONArray("cityData");

                    String country = countryData.getString(0);
                    String state = stateData.getString(0);
                    String city = cityData.getString(0);

                    String jsonResponse = "";
                    jsonResponse += "country: " + country + "\n\n";
                    jsonResponse += "state: " + state + "\n\n";
                    jsonResponse += "city: " + city + "\n\n";

                    Log.d(TAG, jsonResponse);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }*/
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        }){

            /*@Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "billing");
                params.put("password", "password");
                return params;
            }*/

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

    private void  template() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
                template, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                txtResponse.setText(response.toString());
                /*
                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("name");
                    String email = response.getString("email");
                    JSONObject phone = response.getJSONObject("phone");
                    String home = phone.getString("home");
                    String mobile = phone.getString("mobile");

                    jsonResponse = "";
                    jsonResponse += "Name: " + name + "\n\n";
                    jsonResponse += "Email: " + email + "\n\n";
                    jsonResponse += "Home: " + home + "\n\n";
                    jsonResponse += "Mobile: " + mobile + "\n\n";

                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }*/
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", "billing");
                params.put("password", "password");
                return params;
            }

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
     * Method to make json array request where response starts with [
     * */
    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);

                                String name = person.getString("name");
                                String email = person.getString("email");
                                JSONObject phone = person
                                        .getJSONObject("phone");
                                String home = phone.getString("home");
                                String mobile = phone.getString("mobile");

                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Email: " + email + "\n\n";
                                jsonResponse += "Home: " + home + "\n\n";
                                jsonResponse += "Mobile: " + mobile + "\n\n\n";

                            }

                            txtResponse.setText(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
