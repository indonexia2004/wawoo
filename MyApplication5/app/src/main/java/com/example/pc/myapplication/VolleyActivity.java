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
    private String username = "indo7@email.com";

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
                template();
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

    private String authen = urlBase + "mediadevices/";
    private String orders = urlBase + "orders/";

    private String template = urlBase + "clients/template";
    private String clients = urlBase + "clients";
    private String ownedhardware = urlBase + "ownedhardware/";
    private String selfcare_pass = urlBase + "selfcare/password";

    private String selfcare_login = urlBase + "selfcare/login";

    private String plans_prepaid = urlBase + "plans?planType=prepaid";

    private String planservices = urlBase + "planservices";

    private String epgprogramguide = urlBase + "epgprogramguide";

    private String payments = urlBase + "/payments/paypalEnquirey/";
    private String order_changeplan = urlBase + "orders/changePlan/";
    private String mediadevices = urlBase + "mediadevices";
    private String selfcare_status = urlBase + "selfcare/status/";
    private String mediadevices_client = urlBase + "mediadevices/client/";
    private String selfcare_forgotpass = urlBase + "selfcare/forgotpassword";
    private String selfcare_resetpass= urlBase + "selfcare/resetpassword";


    /**
     * Method to make json object request where json response starts wtih {
     * */

    private void template() {

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

                    Log.d(TAG, location.toString());
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
    public JSONObject createClientmapperObject(JSONObject location)
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
            request.put("email",username);
            request.put("fullname","Bui Xuan Dai");
            Log.d(TAG, "createUserMapperObejct: " + request.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    private void client(JSONObject location) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                clients, createClientmapperObject(location), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    String clientId = response.getString("clientId");
                    Log.d(TAG, "clientId: " + clientId);

                    ownedhardware(new JSONObject().put("clientId",clientId));
                } catch (JSONException e) {
                    e.printStackTrace();
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
    public JSONObject createDeviceMapperObject(JSONObject object)
    {
        JSONObject request=new JSONObject();
        try {

            request.put("serialNumber","5555822eb528e705");
            request.put("dateFormat","dd MMMM yyyy");
            request.put("clientCategory","20");
            request.put("allocationDate","03 May 2014");
            request.put("status","");
            request.put("provisioningSerialNumber","5555822eb528e705");
            request.put("locale","en");

            Log.d(TAG, "createDeviceMapperObject: " + request.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    private void ownedhardware(JSONObject clientId) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ownedhardware, createDeviceMapperObject(null), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                    JSONObject resourceId = response;
                    Log.d(TAG, "resourceId: " + resourceId.toString());
                    selfcarePass(null);

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
    public JSONObject createSelfcarePassMapperObject(JSONObject object)
    {
        JSONObject request=new JSONObject();
        try {

            request.put("userName",username);
            request.put("uniqueReference",username);
            request.put("password","password123");

            Log.d(TAG, "createDeviceMapperObject: " + request.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }



    private void selfcarePass(JSONObject object) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                selfcare_pass, createSelfcarePassMapperObject(null), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try{
                    JSONObject resourceIdentifier = response.getJSONObject("resourceIdentifier");

                } catch (JSONException e) {
                    e.printStackTrace();
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


    private void login(JSONObject object) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                selfcare_login, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try{
                    String clientId = response.getString("clientId");

                    JSONObject clientData = response.getJSONObject("clientData");
                    String balanceAmount = clientData.getString("balanceAmount");
                    String currency = clientData.getString("currency");
                    String balanceCheck = clientData.getString("balanceCheck");

                    JSONObject paypalConfigData = response.getJSONObject("paypalConfigData");
                    String name = clientData.getString("name");
                    Boolean enabled = clientData.getBoolean("enabled");
                    String value = clientData.getString("value");

                    JSONObject object = new JSONObject();
                    object.put("clientId",clientId);
                    object.put("balanceAmount",balanceAmount);
                    object.put("currency",currency);
                    object.put("balanceCheck",balanceCheck);
                    object.put("name",name);
                    object.put("enabled",enabled);
                    object.put("value",value);
                    Log.d(TAG, object.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
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

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", "password123");
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
     * Method to make json object request where json response starts wtih {
     * */
    public JSONObject createOwnedHardwareMapperObject(JSONObject object)
    {
        JSONObject request=new JSONObject();
        try {

            request.put("serialNumber","5555822eb528e705");
            request.put("dateFormat","dd MMMM yyyy");
            request.put("allocationDate","03 May 2014");
            request.put("status","");
            request.put("provisioningSerialNumber","5555822eb528e705");
            request.put("itemType","1");
            request.put("locale","en");

            Log.d(TAG, "createDeviceMapperObject: " + request.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    private void ownedHardware(JSONObject object) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                selfcare_pass, createOwnedHardwareMapperObject(null), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try{
                    JSONObject resourceId = response.getJSONObject("resourceId");

                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void plansPrepaid(JSONObject object) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                plans_prepaid, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try
                {
                     JSONObject resourceId = response.getJSONObject("resourceId");

                } catch(JSONException e) {
                    e.printStackTrace();
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
            request.put("billAlign","false");

            Log.d(TAG, "createDeviceMapperObject: " + request.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    private void orders(JSONObject object, String clientId) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                orders+ clientId, createOrdersMapperObject(null), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try{
                    JSONObject resourceId = response.getJSONObject("resourceId");

                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void planservices(JSONObject object) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                planservices, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try{
                     JSONObject resourceId = response.getJSONObject("resourceId");

                } catch (JSONException e) {
                    e.printStackTrace();
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





























    private void  makeJsonObjectRequest() {

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
