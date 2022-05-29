package com.example.fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView

class Otpverified : AppCompatActivity() {
    private lateinit var Tag : String
    private lateinit var Username : TextView


//    val TAG = "Otpverified_TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverified)
        init()
//        val username = findViewById<TextView>(R.id.userfullname)
        val user = intent.getStringExtra("username")
        Username.setText(user)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this , Message_Activity ::class.java))
            finish()
        }, 5000)
    }
    private fun init(){
        Tag= "Otpverified_TAG"
        Username= findViewById(R.id.userfullname)
    }
}