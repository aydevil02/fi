package com.example.fi;

import org.json.JSONObject;

public interface After_Request {
    default void onFileUploadComplete(String status) {} ;
    default  void onAnalyticsDataFetch(JSONObject jsonObject) {};
}
