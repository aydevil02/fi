package com.example.fi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class otpverification : AppCompatActivity() {

    private lateinit var Tag : String
    private lateinit var Loginurl : String
    private lateinit var Otpurl : String
    private lateinit var Otpverificationbutton : Button
    private lateinit var Resendotp : Button
    private lateinit var Otp : EditText
    private lateinit var Jsonobj : JSONObject
    private lateinit var phone : String

//    val TAG = "Otpverification_TAG"

//    val loginurl ="https://yomoh.herokuapp.com/api/account/login/"
//    val otpurl ="https://yomoh.herokuapp.com/api/account/otp/"
//    val otpverificationbutton =findViewById<Button>(R.id.otpverification)
//    val otp = findViewById<EditText>(R.id.otp)
//    val resendotp =findViewById<Button>(R.id.resendotp)
//    val jsonobj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)
        init()
        phone = intent.getStringExtra("phone").toString()
            Otpverificationbutton.setOnClickListener {
                SenOtp()
            }
            Resendotp.setOnClickListener {
                ResendOtp()
            }
    }
    private fun init(){
        Tag = "Otpverification_TAG"
        Loginurl="https://yomoh.herokuapp.com/api/account/login/"
        Otpurl="https://yomoh.herokuapp.com/api/account/otp/"
        Otpverificationbutton=findViewById(R.id.otpverification)
        Otp= findViewById(R.id.otp)
        Resendotp=findViewById(R.id.resendotp)
        Jsonobj= JSONObject()

    }

    private fun ResendOtp() {
        val otp =Otp.text.toString()
        val phone =phone.toString()
        Jsonobj.put("otp",otp)
        Jsonobj.put("phone",phone)
        val que = Volley.newRequestQueue(this@otpverification)
        val req = JsonObjectRequest(
            Request.Method.POST,Otpurl,Jsonobj,
            {
                    response->

                Log.d(Tag, response.toString())
                val status= response.get("status").toString()
                if (status.lowercase() =="success"){
                    Toast.makeText(applicationContext, "OTP Send ", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()}

            }, {
                Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                Log.d(Tag,it.message.toString())

            }
        )
        que.add(req)
    }

    private fun SenOtp() {
        Toast.makeText(applicationContext, phone , Toast.LENGTH_LONG).show()
        val otp =Otp.text.toString()
        Jsonobj.put("otp",otp)
        Jsonobj.put("phone", phone)
        val que = Volley.newRequestQueue(this@otpverification)
        val req = JsonObjectRequest(
            Request.Method.POST,Loginurl,Jsonobj,
            {
                    response->

                Log.d(Tag, response.toString())
                Log.d(Tag, otp + " " + phone)

                val status= response.get("status").toString()
                if (status.lowercase() =="success"){
                    val sharedPreferences: SharedPreferences = this.getSharedPreferences(Constant.AUTH_S_P,
                        Context.MODE_PRIVATE)
                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                    editor.putString("auth",response.get("Token").toString())
                    editor.apply()
                    editor.commit()
                    Toast.makeText(applicationContext, "Token: " + response["Token"], Toast.LENGTH_LONG).show()
                    startActivity(Intent(this , Otpverified ::class.java))
                    finish()
                }
                else{
                    Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()}

            }, {
                Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                Log.d(Tag,it.message.toString())

            }
        )
        que.add(req)
        Log.d(Tag, req.body.toString())

    }
}