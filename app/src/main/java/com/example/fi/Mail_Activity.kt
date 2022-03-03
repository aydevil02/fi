package com.example.fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class Mail_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)


        val gotomaindashbutton =findViewById<Button>(R.id.gotomaindash)

//        val g_api = Gmail_api()
//        Log.d("nnova",g_api.toString())
    }
}