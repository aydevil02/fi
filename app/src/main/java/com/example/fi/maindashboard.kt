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
    val TAG = "Dashboard_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maindashboard)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(Constant.AUTH_S_P,
            Context.MODE_PRIVATE)
        val AUTH_TOKEN = sharedPreferences.getString("auth","0")
        val url = "https://fi-backend.herokuapp.com/api/email/gmail/"
        val menubtn =findViewById<Button>(R.id.menubtn)
        val bank_name =findViewById<TextView>(R.id.bankname)
        val bank = intent.getStringExtra("bank")

        bank_name.setText(bank)

        Toast.makeText(this,AUTH_TOKEN,Toast.LENGTH_LONG).show()

        val stk: TableLayout = findViewById<View>(R.id.tablelayout) as TableLayout

        val tbrow = TableRow(this)
        val t1v = TextView(this)
        t1v.text = "BOX1"
        t1v.getResources().getColor(R.color.orange)
        t1v.gravity = Gravity.CENTER
        t1v.setBackgroundResource(R.drawable.bgtable)
        tbrow.addView(t1v)
        stk.addView(tbrow)


        menubtn.setOnClickListener {
            startActivity(Intent(this,Menu::class.java))

        }

        val que = Volley.newRequestQueue(this@maindashboard)
        val req = object:JsonObjectRequest(
            Request.Method.GET,url,null,
            {
                    response->

                Log.d(TAG, response.toString())
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
                Log.d(TAG,it.message.toString())

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
}