package com.example.fi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView

class test : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val username =findViewById<TextView>(R.id.username)
        val maindashboard =findViewById<Button>(R.id.maindashboard)
        val invite = findViewById<Button>(R.id.invitefriend)
        val fullname = intent.getStringExtra("fullname")

        username.setText(fullname)

        maindashboard.setOnClickListener {
            startActivity(Intent(this , maindashboard::class.java))
        }
        invite.setOnClickListener {
            startActivity(Intent(this, User_invite::class.java))
        }

    }
}