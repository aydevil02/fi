package com.example.fi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class login_activity : AppCompatActivity() {
    val TAG = "Login_TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        val url = Constant.Loginurl
        val VideoView = findViewById<VideoView>(R.id.video)
        val login = findViewById<Button>(R.id.loginButton)
        val mediaController = MediaController(this)
        val usermobile = findViewById<EditText>(R.id.login_number)
        val vedio = Uri.parse("android.resource://$packageName/raw/${R.raw.video}")
        val jsonobj = JSONObject()
        mediaController.setAnchorView(VideoView)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(Constant.AUTH_S_P,
            Context.MODE_PRIVATE)
        val AUTH_TOKEN = sharedPreferences.getString("auth","0")

        if (AUTH_TOKEN != "0"){
            startActivity(Intent(this,maindashboard::class.java))

        }



        usermobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if  (usermobile.text.toString().length !=10){
                    login.isEnabled = false
                    usermobile.setError("Invalid Number")
                }
                else {
                        login.isEnabled = true

                }

            }

        })

        login.setOnClickListener {

            val phone =usermobile.text.toString()
            jsonobj.put("phone",phone)
            val que = Volley.newRequestQueue(this@login_activity)
            val req = JsonObjectRequest(
                Request.Method.POST,url,jsonobj,
                {
                        response->

                    Log.d(TAG, response.get("response").toString())
                    val status= response.get("status").toString()
                    if (status.lowercase() =="success"){
                        val intent = Intent(this ,otpverification::class.java)
                        intent.putExtra("phone", usermobile.text.toString())
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this ,Notamember::class.java))
                    }

                }, {
                    Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                    Log.d(TAG,it.message.toString())


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