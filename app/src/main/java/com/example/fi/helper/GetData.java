package com.example.fi.helper;

import android.content.Context;
import android.util.Log;
import android.view.textclassifier.TextClassification;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fi.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetData  {
    Context context ;
    public GetData (Context context){
        this.context = context;

    }

    public void getjsondata  (){

        String TOKEN = Sharedpereference.getAuthCode(context);
        String EXTRACTION_URL = Constant.INSTANCE.getExtraction_data();
        Log.d("nova",TOKEN);
        RequestQueue que = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, EXTRACTION_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("status");
                            JSONArray extract_data = response.getJSONArray("response");
                            Log.d("Nova", extract_data.toString() );


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("nova",error.toString());
            }

        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Token "+ TOKEN);

                return params;
            }
        };

        que.add(request);
        Log.d("nova","Request execu");
    }
}
