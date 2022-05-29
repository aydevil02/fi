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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

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


    public static JSONObject getClusterData(Context context)
    {
        Log.d("nova", "Going to send request");
//        final JSONObject[] Jobj = new JSONObject[1];
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        String resp = null;

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fk", "you")
                .build();
//                RequestBody body = RequestBody.create(JSON, String.valueOf(new String[]{"json"}));
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(Constant.INSTANCE.getGet_Ml_Data())
                .addHeader("Authorization", "Token " + Sharedpereference.getAuthCode(context))
                .post(requestBody)
                .build();
        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
            resp = response.body().string();

//            Log.d("nova", String.valueOf(Jobject));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("nova-SendRequest", e.toString());
        }
        Log.d("nova", "Almost done");

        JSONObject Jobject = null;
        try {
            Jobject = new JSONObject(resp);
            Jobject = Jobject.getJSONObject("response");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return Jobject;
    }

}
