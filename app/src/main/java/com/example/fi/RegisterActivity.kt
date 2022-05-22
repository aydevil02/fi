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
import okhttp3.CertificatePinner
import org.json.JSONObject
import java.util.*

class RegisterActivity : AppCompatActivity()  {

    private lateinit var Tag : String
    private lateinit var Url : String
    private lateinit var Fullname :EditText
    private lateinit var Gender : RadioGroup
    private lateinit var  Male :RadioButton
    private lateinit var  Female :RadioButton
    private lateinit var  Other :RadioButton
    private lateinit var Phone :TextView
    private lateinit var Datetxt :TextView
    private lateinit var Date :Button
    private lateinit var Qualification : Spinner
    private lateinit var Bank : Spinner
    private lateinit var Pincode : EditText
    private lateinit var Pan : EditText
    private lateinit var Email : EditText
    private lateinit var Registerbutton :Button
    private lateinit var T1 : TextView
    private lateinit var Jsonobj : JSONObject
    var qualtext = ""
    var gendertxt = "male"
    var banktxt=""
    var dob = ""


    val mobileno = intent.getStringExtra("usermobile")



//    val TAG = "Register_TAG"
//    val url = Constant.Registerurl
//    val fullname = findViewById<EditText>(R.id.name)
//    val gender =findViewById<RadioGroup>(R.id.gender)
//    val male = findViewById<RadioButton>(R.id.radiobutton1)
//    val female = findViewById<RadioButton>(R.id.radiobutton2)
//    val other = findViewById<RadioButton>(R.id.radiobutton3)
//    val phone = findViewById<TextView>(R.id.mobileno)
//    val datetxt = findViewById<TextView>(R.id.date_text)
//    val date = findViewById<Button>(R.id.date_picker)
//    val qualification =findViewById<Spinner>(R.id.qualificationspinner)
//    val Bank = findViewById<Spinner>(R.id.bankspinner)
//    val pincode =findViewById<EditText>(R.id.pincode)
//    val pan = findViewById<EditText>(R.id.pan)
//    val email =findViewById<EditText>(R.id.email)
//    val registerbutton = findViewById<Button>(R.id.register_button)
//    val t1 =findViewById<TextView>(R.id.t1)
//    val jsonobj = JSONObject()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()


        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

       Phone.setText(mobileno)
       Date.setOnClickListener {
           val dpd = DatePickerDialog(
               this , DatePickerDialog.OnDateSetListener {
                   View:DatePicker , mYear:Int ,mMonth:Int , mDay:Int  ->
                   Datetxt.setText(""+mDay +"/"+(mMonth +1)  +"/"+mYear)
               }, year ,month ,day
           )
           dpd.show()
       }
        dob == Datetxt.text.toString()
        Fullname.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if( Fullname.text.toString().length <= 5){
                    Registerbutton.isEnabled = false
                    Fullname.setError("Enter Your Name ")
                }
                else{
                    Registerbutton.isEnabled = true
                }
            }
        })
        Pan.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if (pan.text.toString().length <10 && pan.text.toString().isEmpty() ){
                    Registerbutton.isEnabled = false
                    pan.setError("Invalid Pan Number")
                }
                else{
                    Registerbutton.isEnabled = true
                }
            }
        })
        Email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if  (android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() && email.text.toString().isNotEmpty()){
                    Registerbutton.isEnabled = true
                }
                else {
                    Registerbutton.isEnabled = false
                    email.setError("Invalid Email")

                }
            }
        })
        Pincode.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                val pincode_txt= pincode.text.toString()
                if (pincode_txt.length< 6){
                    Registerbutton.isEnabled = false
                    pincode.setError("Enter Correct Pincode")
                }
                else{
                    Registerbutton.isEnabled = true
                }
            }
        })
        Gender.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.male) {
                t1.text = Male.text.toString()
                gendertxt = "male"
            }
            if (checkedId == R.id.female){
                t1.text = Female.text.toString()
                gendertxt = "female" }
            if (checkedId == R.id.other){
                t1.text = Other.text.toString()
                gendertxt= "other"}
        }
        Qualification.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
        Registerbutton.setOnClickListener{
            InviitedUserRegistration()
        }
    }
    private fun init(){
        Tag = "Register_TAG"
        Url= Constant.Registerurl
        Fullname= findViewById(R.id.name)
        Gender=findViewById(R.id.gender)
        Male= findViewById(R.id.radiobutton1)
        Female = findViewById(R.id.radiobutton2)
        Other = findViewById(R.id.radiobutton3)
        Phone= findViewById(R.id.mobileno)
        Datetxt= findViewById(R.id.date_text)
        Date = findViewById(R.id.date_picker)
        Qualification=findViewById(R.id.qualificationspinner)
        Bank= findViewById(R.id.bankspinner)
        Pincode=findViewById(R.id.pincode)
        Pan =findViewById(R.id.pan)
        Email =findViewById(R.id.email)
        Registerbutton= findViewById(R.id.register_button)
        T1=findViewById(R.id.t1)
        Jsonobj= JSONObject()

    }

    private fun InviitedUserRegistration() {

        val fullname = Fullname.text.toString()
        val gender =gendertxt
        val mobileno =mobileno.toString()
        val dob = dob
        val qualification = qualtext
        val bank = banktxt
        val pincode = pincode.text.toString()
        val pan =pan.text.toString()
        val email =email.text.toString()
        Jsonobj.put("fullname" ,fullname)
        Jsonobj.put("gender",gender)
        Jsonobj.put("phone",mobileno)
        Jsonobj.put("dob",dob)
        Jsonobj.put("qualification",qualification)
        Jsonobj.put("bank",bank)
        Jsonobj.put("pincode",pincode)
        Jsonobj.put("pan",pan)
        Jsonobj.put("email",email)
        val que = Volley.newRequestQueue(this@RegisterActivity)
        val req1 = JsonObjectRequest(
            Request.Method.POST,Url,Jsonobj,
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


