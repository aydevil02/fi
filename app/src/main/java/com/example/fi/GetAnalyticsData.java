package com.example.fi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fi.helper.Sharedpereference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetAnalyticsData extends AsyncTask<String, String, String> {
    public After_Request after_request = null;
    private Context context;

    public GetAnalyticsData(Context context, After_Request after_request) {
        this.context = context;
        this.after_request = after_request;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d("nova", "Going to send request");
//        final JSONObject[] Jobj = new JSONObject[1];
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fk", "you")
                .build();
//                RequestBody body = RequestBody.create(JSON, String.valueOf(new String[]{"json"}));
        Request request = new Request.Builder()
                .url(Constant.INSTANCE.getGet_Ml_Data())
                .addHeader("Authorization", "Token " + Sharedpereference.getAuthCode(context))
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String resp = response.body().string();
            return resp;
//            Log.d("nova", String.valueOf(Jobject));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("nova-SendRequest", e.toString());
        }
        Log.d("nova", "Almost done");

//        Log.d("nova-SendRequest",Jobj[0].toString());
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            Log.d("TAG", "Data fetched");
            Log.d("TAG", s);
            try {
                JSONObject Jobject = new JSONObject(s);
                Jobject = Jobject.getJSONObject("response");
                after_request.onAnalyticsDataFetch(Jobject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            after_request.upload_pdf_result(s);
//            Toast.makeText(AdvanceFileUpload.this, , Toast.LENGTH_SHORT).show();
        } else {
            Log.d("TAG", "Data Fetching failed");
//            Toast.makeText(AdvanceFileUpload.this, "File Upload Failed", Toast.LENGTH_SHORT).show();
        }
//        progressBar.setVisibility(View.GONE);
    }
}
