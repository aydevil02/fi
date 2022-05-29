package com.example.fi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class maindashboard : AppCompatActivity() {
    lateinit var Tag : String
    lateinit var Url: String
    lateinit var bank : String
    private lateinit var Menubtn : Button
    private lateinit var Bank_name : TextView




//    val TAG = "Dashboard_TAG"
//    val url = "https://fi-backend.herokuapp.com/api/email/gmail/"
//    val menubtn =findViewById<Button>(R.id.menubtn)
//    val bank_name =findViewById<TextView>(R.id.bankname)
//    val bank = intent.getStringExtra("bank")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maindashboard)
        init()

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(Constant.AUTH_S_P,Context.MODE_PRIVATE)
        val AUTH_TOKEN = sharedPreferences.getString("auth","0")


        Bank_name.setText(bank)

        Toast.makeText(this,AUTH_TOKEN,Toast.LENGTH_LONG).show()

        Menubtn.setOnClickListener {
            startActivity(Intent(this,Menu::class.java))

        }

        val que = Volley.newRequestQueue(this@maindashboard)
        val req = object:JsonObjectRequest(
            Request.Method.GET,Url,null,
            {
                    response->

                Log.d(Tag, response.toString())
//                Log.d("nova 2", otp + " " + phone)

//                val status= response.get("status").toString()
//                if (status.lowercase() =="success"){
//                    val sharedPreferences: SharedPreferences = this.getSharedPreferences(Constant.AUTH_S_P,
//                        MODE_PRIVATE
//                    )
//                    val editor:SharedPreferences.Editor =  sharedPreferences.edit()
//                    editor.putString("auth",response.get("Token").toString())
//                    editor.apply()
//                    editor.commit()
//                    Toast.makeText(applicationContext, "Token: " + response["Token"], Toast.LENGTH_LONG).show()
//                    startActivity(Intent(this , otpverified ::class.java))
//                }
//                else{
//                    Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()}

            }, {
//                Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                Log.d(Tag,it.message.toString())

            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = AUTH_TOKEN.toString()
                return headers
            }
        }
        que.add(req)


    }
    private fun init(){
        Tag= "Dashboard_TAG"
        Url= "https://fi-backend.herokuapp.com/api/email/gmail/"
        Menubtn=findViewById(R.id.menubtn)
        Bank_name=findViewById(R.id.bankname)
        bank= intent.getStringExtra("bank").toString()
    }
}