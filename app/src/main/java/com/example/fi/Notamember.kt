package com.example.fi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Notamember : AppCompatActivity() {
    val TAG = "Notamember_TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notamember)

        val regis_btn = findViewById<Button>(R.id.registeration_button)
        val name = findViewById<EditText>(R.id.nameForRegisteration)
        val gender = findViewById<RadioGroup>(R.id.genderForRegisteration)
        val  phone = findViewById<EditText>(R.id.mobileno_ForRegisteration)
        val qualification = findViewById<Spinner>(R.id.qualificationspinnerForRegisteration)
        val bank = findViewById<Spinner>(R.id.bankspinnerForRegisteration)
        val jsonobj = JSONObject()
        val url = Constant.Registerurl
        var qualtext = ""
        var gendertxt = "male"
        var banktxt=""


        gender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.male){
                gendertxt= "male"}
            if (checkedId == R.id.female){
                gendertxt = "female"
            }
            if (checkedId == R.id.other){
            gendertxt = "other"
            }
        }

        qualification.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                qualtext =adapterView?.getItemAtPosition(position).toString()
                Toast.makeText(this@Notamember,"You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        bank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                banktxt = adapterView?.getItemAtPosition(position).toString()
                Toast.makeText(this@Notamember,"You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        regis_btn.setOnClickListener {
            val name = name.text.toString()
            val gender =gendertxt
            val mobileno =phone.text.toString()
            val qualification = qualtext
            val bank= banktxt

            jsonobj.put("fullname" ,name)
            jsonobj.put("gender",gender)
            jsonobj.put("phone",mobileno)
            jsonobj.put("qaulification",qualification)
            jsonobj.put("bank",bank)

            val que = Volley.newRequestQueue(this@Notamember)
            val req1 = JsonObjectRequest(
                Request.Method.POST,url,jsonobj,
                {
                        response->

                    Log.d(TAG, response.get("response").toString())
                    val status= response.get("status").toString()
                    if (status.lowercase() =="success"){
                        Log.d(TAG, gender)
                        Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this ,Member_registeration_sucessful::class.java))
                    }
                    else{Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()}

                }, {
                    Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                    Log.d(TAG,it.message.toString())

                }

            )
            que.add(req1)
        }



    }
}