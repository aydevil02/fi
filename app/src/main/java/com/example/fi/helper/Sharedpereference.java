package com.example.fi.helper;

import static android.os.ParcelFileDescriptor.MODE_APPEND;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fi.Constant;

public  class Sharedpereference {
    public static  String getAuthCode(Context context){
        SharedPreferences sh = context.getSharedPreferences(Constant.INSTANCE.getAUTH_S_P(),
                Context.MODE_PRIVATE);
        String token = sh.getString("auth", "0");
        return token ;
    }


}
