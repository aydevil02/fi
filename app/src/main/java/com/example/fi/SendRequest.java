package com.example.fi;


import android.content.Context;
import android.media.session.MediaSession;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fi.helper.Sharedpereference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SendRequest{


    public static void Run_ml(Context context){
        OkHttpClient client = new OkHttpClient();
        String Token = "Authorization"+"Token " + Sharedpereference.getAuthCode(context);

        // RequestBody body = RequestBody.create(JSON, json); // old
        Request request = new Request.Builder()
                .url(Constant.INSTANCE.getRun_ML())
                .addHeader("Authorization","Token " + Sharedpereference.getAuthCode(context))
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            Log.d("nova1", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("nova1",e.toString());
        }

    }

    public static void uploadPdf(String path , Context context){
        UploadTask uploadTask = new UploadTask(context);
        uploadTask.execute(path);
    }

    public static JSONObject getExtractionData( Context context) throws InterruptedException {
        Log.d("nova","Going to send request");
        final JSONObject[] Jobj = new JSONObject[1];
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        Thread n1 = new Thread(new Runnable() {
            @Override
            public void run() {


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
                    JSONObject Jobject = new JSONObject(resp);
                    Jobj[0] = Jobject.getJSONObject("response");
                    Log.d("nova", String.valueOf(Jobject));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    Log.d("nova-SendRequest",e.toString());
                }
                Log.d("nova","Almost done");


            }
        });
        n1.start();

        n1.join();
//        Log.d("nova-SendRequest",Jobj[0].toString());
        return Jobj[0];

    }

    public static void sendUserInput(JSONObject userInputs, Context context) throws InterruptedException {
        Log.d("nova","Going to send request");
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        Thread n1 = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                    RequestBody body = RequestBody.create(String.valueOf(userInputs), JSON); // new
                    // RequestBody body = RequestBody.create(JSON, json); // old
                    Request request = new Request.Builder()
                            .url(Constant.INSTANCE.getPUSH_USER_INPUT())
                            .addHeader("Authorization","Token " + Sharedpereference.getAuthCode(context))
                            .post(body)
                            .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    Log.d("nova", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("nova",e.toString());
                }


            }
        });
        n1.start();

        n1.join();
    }

}


class UploadTask extends AsyncTask<String, String, String> {


    public static final String TAG = "nova send-request.java";
    Context context ;

    public UploadTask(Context context){
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null){
            Log.d(TAG,"File Uploaded");
            Log.d(TAG, s);
//            after_request.upload_pdf_result(s);
//            Toast.makeText(AdvanceFileUpload.this, , Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d(TAG,"File Upload Failed");
//            Toast.makeText(AdvanceFileUpload.this, "File Upload Failed", Toast.LENGTH_SHORT).show();
        }
//        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected String doInBackground(String... string) {

        File file1 = new File(string[0]);


        try {
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("statement", file1.getName(), RequestBody.create(MediaType.parse("*/*"), file1))
//                        .addFormDataPart("files2", file2.getName(), RequestBody.create(MediaType.parse("*/*"), file2))
//                        .addFormDataPart("files3", file3.getName(), RequestBody.create(MediaType.parse("*/*"), file3))
                    .addFormDataPart("filename",file1.getName())
//                        .addFormDataPart("submit", "submit")
                    .build();
            Request request = new Request.Builder()
                    .url(Constant.INSTANCE.getDocuploadurl())

                    .addHeader("Authorization","Token "+ Sharedpereference.getAuthCode(context) )
                    .post(requestBody)
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient();
            //now progressbar not showing properly let's fixed it
            Response response = okHttpClient.newCall(request).execute();
            Log.d(TAG,"meeeeeh"+response.message());
            if (response.isSuccessful()) {
                Log.d(TAG,response.body().toString());
                return Objects.requireNonNull(response.body()).string();
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
