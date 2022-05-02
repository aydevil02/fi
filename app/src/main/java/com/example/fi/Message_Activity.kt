package com.example.fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_message.*


class Message_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)


        proceed.setOnClickListener {
            startActivity(Intent(this , Upload_PDF ::class.java ))

        }
        skip.setOnClickListener {
            startActivity(Intent(this , maindashboard::class.java))
        }
    }
}