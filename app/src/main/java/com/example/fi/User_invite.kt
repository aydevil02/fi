package com.example.fi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class User_invite : AppCompatActivity() {

    private lateinit var InvitedName : EditText
    private lateinit var InvitedPhone : EditText
    private lateinit var InvitedEmail : EditText
    private lateinit var InvitedBtn : Button
    private lateinit var Jsonobj : JSONObject

//    val invitedName = findViewById<EditText>(R.id.inviteName)
//    val invitedPhone = findViewById<EditText>(R.id.invitePhone)
//    val invitedEmail = findViewById<EditText>(R.id.inviteEmail)
//    val invitedBtn = findViewById<Button>(R.id.inviteBtn)
//    val jsonobj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_invite)
        init()


        InvitedName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (InvitedName.text.toString().length <= 5){
                        InvitedBtn.isEnabled = false
                            InvitedName.setError("Enter Your Name ")
                    }
                    else{
                        InvitedBtn.isEnabled = true
                    }
            }

        })


        InvitedPhone.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if  (InvitedPhone.text.toString().length !=10){
                    InvitedBtn.isEnabled = false
                    InvitedPhone.setError("Invalid Number")
                }
                else {
                    InvitedBtn.isEnabled = true
                }
            }

        })
        InvitedEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if  (android.util.Patterns.EMAIL_ADDRESS.matcher(InvitedEmail.text.toString()).matches() && InvitedEmail.text.toString().isNotEmpty()){
                    InvitedBtn.isEnabled = true
                }
                else {
                    InvitedBtn.isEnabled = false
                    InvitedEmail.setError("Invalid Email")

                }

            }

        })


        InvitedBtn.setOnClickListener {
            InviteFriend()


        }
    }
    private fun init(){
        InvitedBtn= findViewById<Button>(R.id.inviteBtn)
        InvitedEmail= findViewById<EditText>(R.id.inviteEmail)
        InvitedName= findViewById<EditText>(R.id.inviteName)
        InvitedPhone= findViewById<EditText>(R.id.invitePhone)
        Jsonobj =JSONObject()
    }

    private fun InviteFriend() {
        val name = InvitedName.text.toString()
        val phone = InvitedPhone.text.toString()
        val email = InvitedEmail.text.toString()

        Jsonobj.put("name", name)
        Jsonobj.put("phone", phone)
        Jsonobj.put("email", email)
        val que = Volley.newRequestQueue(this@User_invite)
        val req2 = JsonObjectRequest(
            Request.Method.POST,Constant.User_invite_Url,Jsonobj,
            {
                    response->

                Log.d("nova", response.toString())
                val status= response.get(Constant.STATUS).toString()
                if (status.lowercase() =="success"){
                    Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(applicationContext, "sucessfull $response", Toast.LENGTH_LONG).show()}
            }, {
                Toast.makeText(applicationContext, "error ${it.message}", Toast.LENGTH_LONG).show()
                Log.d("nova",it.message.toString())

            }

        )
        que.add(req2)
    }
}