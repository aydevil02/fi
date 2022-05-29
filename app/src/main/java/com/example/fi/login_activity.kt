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
    lateinit var Tag : String
    lateinit var Jsonobj : JSONObject
    lateinit var Url : String
    private lateinit var Usermobile : EditText
    private lateinit var Login : Button



//    val TAG = "Login_TAG"
//    val jsonobj = JSONObject()
//    val usermobile = findViewById<EditText>(R.id.login_number)
//    val url = Constant.Loginurl
//    val login = findViewById<Button>(R.id.loginButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()



        val VideoView = findViewById<VideoView>(R.id.video)
        val mediaController = MediaController(this)
        val vedio = Uri.parse("android.resource://$packageName/raw/${R.raw.video}")
        mediaController.setAnchorView(VideoView)



        val sharedPreferences: SharedPreferences = this.getSharedPreferences(Constant.AUTH_S_P,Context.MODE_PRIVATE)
        val AUTH_TOKEN = sharedPreferences.getString("auth","0")
        if (AUTH_TOKEN != "0"){
            startActivity(Intent(this,maindashboard::class.java))

        }



        Usermobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if  (Usermobile.text.toString().length !=10){
                    Login.isEnabled = false
                    Usermobile.setError("Invalid Number")
                }
                else {
                        Login.isEnabled = true

                }

            }

        })

        Login.setOnClickListener {
            UserLogin()

        }

        VideoView.setMediaController(mediaController)
        VideoView.setVideoURI(vedio)
        VideoView.requestFocus()
        VideoView.start()
    }

    private fun init(){
        Tag = "Login_TAG"
        Jsonobj= JSONObject()
        Usermobile= findViewById(R.id.login_number)
        Url= Constant.Loginurl
        Login= findViewById(R.id.loginButton)

    }
    fun UserLogin(){

        val phone =Usermobile.text.toString()
        Jsonobj.put("phone",phone)
        val que = Volley.newRequestQueue(this@login_activity)
        val req = JsonObjectRequest(
            Request.Method.POST,Url,Jsonobj,
            {
                    response->

                Log.d(Tag, response.get("response").toString())
                val status= response.get("status").toString()
                if (status.lowercase() =="success"){
                    val intent = Intent(this ,otpverification::class.java)
                    intent.putExtra("phone", Usermobile.text.toString())
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this ,Notamember::class.java))
                }

            }, {
                Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                Log.d(Tag,it.message.toString())


            }
        )
        que.add(req)

    }
}