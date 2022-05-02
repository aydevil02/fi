package com.example.fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        val username =findViewById<TextView>(R.id.username)
        val maindashboard =findViewById<Button>(R.id.maindashboard)
        val invite = findViewById<Button>(R.id.invitefriend)
        val statement = findViewById<Button>(R.id.statement)
        val uploadocument =findViewById<Button>(R.id.uploadDocument)
        val mloutput = findViewById<Button>(R.id.Mloutput)
        val fullname = intent.getStringExtra("fullname")

        username.setText(fullname)

        maindashboard.setOnClickListener {
            startActivity(Intent(this , maindashboard::class.java))
        }
        invite.setOnClickListener {
            startActivity(Intent(this, User_invite::class.java))
        }
        statement.setOnClickListener {
            startActivity(Intent(this , statementscreen::class.java))
        }
        uploadocument.setOnClickListener {
            startActivity(Intent(this, Upload_PDF::class.java))
        }
        mloutput.setOnClickListener {
            startActivity(Intent(this, Ml_output::class.java))
        }
    }
}