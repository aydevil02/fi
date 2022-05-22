package com.example.fi;


import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fi.helper.Sharedpereference;

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

    public static final String TAG = "nova send-request.java";
    Context context;
     public SendRequest(Context context)
     {
         this.context = context;
     }

     public boolean RunallModels() throws InterruptedException {
         Run_Extraction();
         return true;
     }

    public String Run_ml() throws InterruptedException {
//        final JSONObject[] Jobj = new JSONObject[1];
        final Response[] response = new Response[1];
        final String[] status = {null};
        Thread n1 = new Thread(new Runnable() {
            @Override
            public void run(){
            OkHttpClient client = new OkHttpClient();
        String Token = "Authorization"+"Token " + Sharedpereference.getAuthCode(context);

         RequestBody body = RequestBody.create("JSON", MediaType.parse("json")); // old
        Request request = new Request.Builder()
                .url(Constant.INSTANCE.getRun_ML())
                .addHeader("Authorization","Token " + Sharedpereference.getAuthCode(context))
                .post(RequestBody.create(null, new byte[]{}))
                .build();

        try {
            Response response = client.newCall(request).execute();
            String resp = response.body().string();
            Log.d("nova-uploaded-then",resp);
            JSONObject Jobject = new JSONObject(resp);
            status[0] = String.valueOf(Jobject.getString("status"));
            Log.d("nova", String.valueOf(Jobject));
            Log.d("nova1", status[0]);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("nova1",e.toString());
        }
            }
        });
        n1.start();

        n1.join();
//    return Jobj[0] ;
    return status[0];
    }

    public String Run_Extraction() throws InterruptedException {
        final Response[] response = new Response[1];
        final String[] status = {null};
        Thread n1 = new Thread(new Runnable() {
            @Override
            public void run(){
                OkHttpClient client = new OkHttpClient();
                String Token = "Authorization"+"Token " + Sharedpereference.getAuthCode(context);

                RequestBody body = RequestBody.create("JSON", MediaType.parse("json")); // old
                Request request = new Request.Builder()
                        .url(Constant.INSTANCE.getRun_EXTRACTION())
                        .addHeader("Authorization","Token " + Sharedpereference.getAuthCode(context))
                        .post(RequestBody.create(null, new byte[]{}))
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String resp = response.body().string();
                    JSONObject Jobject = new JSONObject(resp);
                    status[0] = String.valueOf(Jobject.getString("status"));
                    if(status[0].equalsIgnoreCase("Success"))
                    {
                        Run_ml();
                    }
//                    Log.d("nova", String.valueOf(Jobject));
//                    Log.d("nova1 - etraction_func", status[0]);
                } catch (IOException | JSONException | InterruptedException e) {
                    e.printStackTrace();
                    Log.d("nova1",e.toString());
                }
            }
        });
        n1.start();

        n1.join();
//    return Jobj[0] ;
        return status[0];
    }


    public static void uploadPdf(String path , Context context){
//        UploadTask uploadTask = new UploadTask(context);
//        uploadTask.execute(path);
    }

    public JSONObject getCashFlowData() throws InterruptedException {
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
                        .url(Constant.INSTANCE.getGet_ML_cashflow())
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

//    public static JSONObject getAnalyticsData( Context context) throws InterruptedException {
//        Log.d("nova","Going to send request");
//        final JSONObject[] Jobj = new JSONObject[1];
//        final MediaType JSON
//                = MediaType.parse("application/json; charset=utf-8");
//        Thread n1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                OkHttpClient client = new OkHttpClient();
//                RequestBody requestBody = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart("fk", "you")
//                        .build();
////                RequestBody body = RequestBody.create(JSON, String.valueOf(new String[]{"json"}));
//                Request request = new Request.Builder()
//                        .url(Constant.INSTANCE.getGet_Ml_Data())
//                        .addHeader("Authorization", "Token " + Sharedpereference.getAuthCode(context))
//                        .post(requestBody)
//                        .build();
//                Response response = null;
//                try {
//                    response = client.newCall(request).execute();
//                    String resp = response.body().string();
//                    JSONObject Jobject = new JSONObject(resp);
//                    Jobj[0] = Jobject.getJSONObject("response");
//                    Log.d("nova", String.valueOf(Jobject));
//                } catch (IOException | JSONException e) {
//                    e.printStackTrace();
//                    Log.d("nova-SendRequest",e.toString());
//                }
//                Log.d("nova","Almost done");
//            }
//        });
//        n1.start();
//
//        n1.join();
////        Log.d("nova-SendRequest",Jobj[0].toString());
//        return Jobj[0];
//
//    }

//    public static JSONObject getAnalyticsData( Context context) {
//
//
//    }

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

class UploadTask extends AsyncTask<String, String, String>{
    public static final String TAG = "nova send-request.java";
    Context context ;

    public After_Request after_request = null ;

    public UploadTask(Context context , After_Request after_request){
        this.context = context;
        this.after_request = after_request;
        if(this.after_request !=null )
            Log.d("nova","Going right");
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
            after_request.onFileUploadComplete(s);
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
        String Jobj;

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

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(15,TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            Log.d(TAG,"meeeeeh"+response.message());
            if (response.isSuccessful()) {
                Log.d(TAG,response.body().toString());
                JSONObject Jobject = new JSONObject(response.body().string());
                Jobj = Jobject.getString("status");
                return Jobj;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
