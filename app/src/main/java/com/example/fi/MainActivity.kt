package com.example.fi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = Constant.Verifyurl
        val VideoView = findViewById<VideoView>(R.id.video)
        val verifybutton = findViewById<Button>(R.id.loginButton)
        val mediaController = MediaController(this)
        val usermobile = findViewById<EditText>(R.id.usermobile_no_)
        val vedio = Uri.parse("android.resource://$packageName/raw/${R.raw.video}")
        val signin = findViewById<Button>(R.id.Login)
        val jsonobj = JSONObject()
        mediaController.setAnchorView(VideoView)


        val sharedPreferences: SharedPreferences = this.getSharedPreferences(Constant.AUTH_S_P,
            Context.MODE_PRIVATE)
        val AUTH_TOKEN = sharedPreferences.getString("auth","0")

        if (AUTH_TOKEN != "0"){
            startActivity(Intent(this,maindashboard::class.java))

        }



        usermobile.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if  (usermobile.text.toString().length !=10){
                    verifybutton.isEnabled = false
                    usermobile.setError("Invalid Number")
                }
                else {
                    verifybutton.isEnabled = true

            }
        }


    })

        signin.setOnClickListener {
            startActivity(Intent(this , login_activity::class.java))
        }

        verifybutton.setOnClickListener {
            val phone =usermobile.text.toString()
            jsonobj.put("phone",phone)
            val que = Volley.newRequestQueue(this@MainActivity)
            val req = JsonObjectRequest(
                Request.Method.POST,url,jsonobj,
                {
                    response->

                    Log.d("nova", response.get("response").toString())
                    val status= response.get("status").toString()
                    Toast.makeText(applicationContext,status,Toast.LENGTH_LONG).show()
                    Log.d("nova",status)
                    if (status.lowercase() =="success"){
                        val intent = Intent(this ,RegisterActivity::class.java)
                        intent.putExtra("usermobile", usermobile.text.toString())
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this ,Notamember::class.java))
                        finish()
                    }

                }, {
                    Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                    Log.d("nova",it.message.toString())
                    startActivity(Intent(this ,statementscreen::class.java))
                    finish()
                }
             )
            que.add(req)
        }



        

        VideoView.setMediaController(mediaController)
        VideoView.setVideoURI(vedio)
        VideoView.requestFocus()
        VideoView.start()







    }
}