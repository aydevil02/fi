package com.example.fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Menu : AppCompatActivity() {

    private lateinit var Username : TextView
    private lateinit var Maindashboard : Button
    private lateinit var Invite : Button
    private lateinit var Statement : Button
    private lateinit var Uploadpdf : Button
    private lateinit var Mloutput : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        init()


//        val username =findViewById<TextView>(R.id.username)
//        val maindashboard =findViewById<Button>(R.id.maindashboard)
//        val invite = findViewById<Button>(R.id.invitefriend)
//        val statement = findViewById<Button>(R.id.statement)
//        val uploadocument =findViewById<Button>(R.id.uploadDocument)
//        val mloutput = findViewById<Button>(R.id.Mloutput)
        val fullname = intent.getStringExtra("fullname")

        Username.setText(fullname)

        Maindashboard.setOnClickListener {
            startActivity(Intent(this , maindashboard::class.java))
        }
        Invite.setOnClickListener {
            startActivity(Intent(this, User_invite::class.java))
        }
        Statement.setOnClickListener {
            startActivity(Intent(this , statementscreen::class.java))
        }
        Uploadpdf.setOnClickListener {
            startActivity(Intent(this, Upload_PDF::class.java))
        }
        Mloutput.setOnClickListener {
            startActivity(Intent(this, Ml_output::class.java))
        }
    }
    private fun init(){
        Username=findViewById<TextView>(R.id.username)
        Maindashboard=findViewById<Button>(R.id.maindashboard)
        Invite= findViewById<Button>(R.id.invitefriend)
        Statement= findViewById<Button>(R.id.statement)
        Uploadpdf =findViewById<Button>(R.id.uploadDocument)
        Mloutput = findViewById<Button>(R.id.Mloutput)

    }
}