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
    private lateinit var Tag : String
    private lateinit var Regis_btn : Button
    private lateinit var Name : EditText
    private lateinit var Phone : EditText
    private lateinit var Gender : RadioGroup
    private lateinit var Qualification : Spinner
    private lateinit var Bank  : Spinner
    private lateinit var Jsonobj : JSONObject
    private lateinit var Url :String


//
//    val TAG = "Notamember_TAG"
//    val regis_btn = findViewById<Button>(R.id.registeration_button)
//    val name = findViewById<EditText>(R.id.nameForRegisteration)
//    val gender = findViewById<RadioGroup>(R.id.genderForRegisteration)
//    val  phone = findViewById<EditText>(R.id.mobileno_ForRegisteration)
//    val qualification = findViewById<Spinner>(R.id.qualificationspinnerForRegisteration)
//    val bank = findViewById<Spinner>(R.id.bankspinnerForRegisteration)
//    val jsonobj = JSONObject()
//    val url = Constant.Registerurl
    var qualtext = ""
    var gendertxt = "male"
    var banktxt=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notamember)
        init()


        Gender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.male){
                gendertxt= "male"}
            if (checkedId == R.id.female){
                gendertxt = "female"
            }
            if (checkedId == R.id.other){
            gendertxt = "other"
            }
        }

        Qualification.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                qualtext =adapterView?.getItemAtPosition(position).toString()
                Toast.makeText(this@Notamember,"You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_LONG).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        Bank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                banktxt = adapterView?.getItemAtPosition(position).toString()
                Toast.makeText(this@Notamember,"You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_LONG).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        Regis_btn.setOnClickListener {
            InitiaiteResgistration()
        }
    }
    private fun init (){
        Tag= "Notamember_TAG"
        Regis_btn = findViewById(R.id.registeration_button)
        Name = findViewById(R.id.nameForRegisteration)
        Gender = findViewById(R.id.genderForRegisteration)
        Phone= findViewById(R.id.mobileno_ForRegisteration)
        Qualification= findViewById(R.id.qualificationspinnerForRegisteration)
        Bank= findViewById(R.id.bankspinnerForRegisteration)
        Jsonobj = JSONObject()
        Url= Constant.Registerurl

    }

    private fun InitiaiteResgistration() {
        val name = Name.text.toString()
        val gender =gendertxt
        val mobileno =Phone.text.toString()
        val qualification = qualtext
        val bank= banktxt

        Jsonobj.put("fullname" ,name)
        Jsonobj.put("gender",gender)
        Jsonobj.put("phone",mobileno)
        Jsonobj.put("qaulification",qualification)
        Jsonobj.put("bank",bank)

        val que = Volley.newRequestQueue(this@Notamember)
        val req1 = JsonObjectRequest(
            Request.Method.POST,Url,Jsonobj,
            {
                    response->

                Log.d(Tag, response.get("response").toString())
                val status= response.get("status").toString()
                if (status.lowercase() =="success"){
                    Log.d(Tag, gender)
                    Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this ,Member_registeration_sucessful::class.java))
                }
                else{Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()}

            }, {
                Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                Log.d(Tag,it.message.toString())

            }

        )
        que.add(req1)
    }
}