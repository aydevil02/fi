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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)
        val phone = intent.getStringExtra("phone")

        val loginurl ="https://yomoh.herokuapp.com/api/account/login/"
        val otpurl ="https://yomoh.herokuapp.com/api/account/otp/"
        val otpverificationbutton =findViewById<Button>(R.id.otpverification)
        val otp = findViewById<EditText>(R.id.otp)
        val resendotp =findViewById<Button>(R.id.resendotp)
        val jsonobj = JSONObject()


            otpverificationbutton.setOnClickListener {
                Toast.makeText(applicationContext, phone , Toast.LENGTH_LONG).show()
                val otp =otp.text.toString()
                jsonobj.put("otp",otp)
                jsonobj.put("phone", phone)
                val que = Volley.newRequestQueue(this@otpverification)
                val req = JsonObjectRequest(
                    Request.Method.POST,loginurl,jsonobj,
                    {
                            response->

                        Log.d("nova", response.toString())
                        Log.d("nova 2", otp + " " + phone)

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
                        Log.d("nova",it.message.toString())

                    }
                )
                que.add(req)
                Log.d("nova 3 ", req.body.toString())
            }
            resendotp.setOnClickListener {
                val otp =otp.text.toString()
                val phone =phone.toString()
                jsonobj.put("otp",otp)
                jsonobj.put("phone",phone)
                val que = Volley.newRequestQueue(this@otpverification)
                val req = JsonObjectRequest(
                    Request.Method.POST,otpurl,jsonobj,
                    {
                            response->

                        Log.d("nova", response.toString())
                        val status= response.get("status").toString()
                        if (status.lowercase() =="success"){

                            startActivity(Intent(this ,Otpverified::class.java))
                        }
                        else{
                            Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()}

                    }, {
                        Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                        Log.d("nova",it.message.toString())

                    }
                )
                que.add(req)

            }


    }
}