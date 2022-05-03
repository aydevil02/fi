package com.example.fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class Loadind_Screen_Get_Cashflow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loadind_screen_get_cashflow)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this , Ml_output ::class.java))
            finish()
        }, 5000)
    }
}