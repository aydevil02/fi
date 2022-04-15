package com.example.fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
//import android.os.Handler
import android.view.View
import android.widget.Button

class otpverified : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverified)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this , Message_Activity ::class.java))
            finish()
        }, 5000)




    }
}