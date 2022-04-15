package com.example.fi

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import java.util.*

class RegisterActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val url = Constant.Registerurl
        val fullname = findViewById<EditText>(R.id.name)
        val gender =findViewById<RadioGroup>(R.id.gender)
        val male = findViewById<RadioButton>(R.id.radiobutton1)
        val female = findViewById<RadioButton>(R.id.radiobutton2)
        val other = findViewById<RadioButton>(R.id.radiobutton3)
        val mobileno = intent.getStringExtra("usermobile")
        val phone = findViewById<TextView>(R.id.mobileno)
        val datetxt = findViewById<TextView>(R.id.date_text)
        val date = findViewById<Button>(R.id.date_picker)
        val qualification =findViewById<Spinner>(R.id.qualificationspinner)
        val Bank = findViewById<Spinner>(R.id.bankspinner)
        val pincode =findViewById<EditText>(R.id.pincode)
        val pan = findViewById<EditText>(R.id.pan)
        val email =findViewById<EditText>(R.id.email)
        val registerbutton = findViewById<Button>(R.id.register_button)
        val t1 =findViewById<TextView>(R.id.t1)
        val jsonobj = JSONObject()
        var qualtext = ""
        var gendertxt = "male"
        var banktxt=""
        var dob = ""
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)


        phone.setText(mobileno)

       date.setOnClickListener {
           val dpd = DatePickerDialog(
               this , DatePickerDialog.OnDateSetListener {
                   View:DatePicker , mYear:Int ,mMonth:Int , mDay:Int  ->
                   datetxt.setText(""+mDay +"/"+(mMonth +1)  +"/"+mYear)
               }, year ,month ,day
           )
           dpd.show()
       }
        dob == datetxt.text.toString()
        fullname.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if( fullname.text.toString().length <= 5){
                    registerbutton.isEnabled = false
                    fullname.setError("Enter Your Name ")
                }
                else{
                    registerbutton.isEnabled = true
                }
            }

        })
        pan.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (pan.text.toString().length <10 && pan.text.toString().isEmpty() ){
                    registerbutton.isEnabled = false
                    pan.setError("Invalid Pan Number")
                }
                else{
                    registerbutton.isEnabled = true
                }
            }

        })

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if  (android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() && email.text.toString().isNotEmpty()){
                    registerbutton.isEnabled = true
                }
                else {
                    registerbutton.isEnabled = false
                    email.setError("Invalid Email")

                }

            }

        })
        pincode.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val pincode_txt= pincode.text.toString()
                if (pincode_txt.length< 6){
                    registerbutton.isEnabled = false
                    pincode.setError("Enter Correct Pincode")
                }
                else{
                    registerbutton.isEnabled = true
                }

            }

        })


        gender.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.male) {
                t1.text = male.text.toString()
                gendertxt = "male"
            }
            if (checkedId == R.id.female){
                t1.text = female.text.toString()
                gendertxt = "female" }
            if (checkedId == R.id.other){
                t1.text = other.text.toString()
                gendertxt= "other"}
        }



        qualification.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                qualtext = adapterView?.getItemAtPosition(position).toString()
                Toast.makeText(this@RegisterActivity,"You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        Bank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
               banktxt = adapterView?.getItemAtPosition(position).toString()
                Toast.makeText(this@RegisterActivity,"You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        registerbutton.setOnClickListener{
            val fullname = fullname.text.toString()
            val gender =gendertxt
            val mobileno =mobileno.toString()
            val dob = dob
            val qualification = qualtext
            val bank = banktxt
            val pincode = pincode.text.toString()
            val pan =pan.text.toString()
            val email =email.text.toString()
            jsonobj.put("fullname" ,fullname)
            jsonobj.put("gender",gender)
            jsonobj.put("phone",mobileno)
            jsonobj.put("dob",dob)
            jsonobj.put("qualification",qualification)
            jsonobj.put("bank",bank)
            jsonobj.put("pincode",pincode)
            jsonobj.put("pan",pan)
            jsonobj.put("email",email)
            val que = Volley.newRequestQueue(this@RegisterActivity)
            val req1 = JsonObjectRequest(
                Request.Method.POST,url,jsonobj,
                {
                        response->

                    Log.d("nova", response.toString())
                    val status= response.get(Constant.STATUS).toString()
                    if (status.lowercase() =="success"){
                        Log.d("Abhi", gender)
                        Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()
                        val intent = Intent(this ,otpverification::class.java)
                        intent.putExtra("phone", mobileno)
                        intent.putExtra("fullname" , fullname)
                        intent.putExtra("bank",banktxt)
                        startActivity(intent)
                        finish()
                    }
                    else{Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()}

                }, {
                    Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                    Log.d("nova",it.message.toString())

                }

            )
            que.add(req1)
        }

    }




}


