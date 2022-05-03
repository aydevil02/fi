package com.example.fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView

class Otpverified : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverified)

        val username = findViewById<TextView>(R.id.userfullname)
        val user = intent.getStringExtra("username")

        username.setText(user)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this , Message_Activity ::class.java))

            finish()
        }, 5000)




    }
}