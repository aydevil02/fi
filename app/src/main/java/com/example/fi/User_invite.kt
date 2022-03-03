package com.example.fi

import android.content.Intent
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_invite)




        val url = ""
        val invitedName = findViewById<EditText>(R.id.inviteName)
        val invitedPhone = findViewById<EditText>(R.id.invitePhone)
        val invitedEmail = findViewById<EditText>(R.id.inviteEmail)
        val invitedBtn = findViewById<Button>(R.id.inviteBtn)
        val jsonobj = JSONObject()



        invitedName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (invitedName.text.toString().length <= 5){
                        invitedBtn.isEnabled = false
                            invitedName.setError("Enter Your Name ")
                    }
                    else{
                        invitedBtn.isEnabled = true
                    }
            }

        })


        invitedPhone.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if  (invitedPhone.text.toString().length !=10){
                    invitedBtn.isEnabled = false
                    invitedPhone.setError("Invalid Number")
                }
                else {
                    invitedBtn.isEnabled = true
                }
            }

        })
        invitedEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if  (android.util.Patterns.EMAIL_ADDRESS.matcher(invitedEmail.text.toString()).matches() && invitedEmail.text.toString().isNotEmpty()){
                    invitedBtn.isEnabled = true
                }
                else {
                    invitedBtn.isEnabled = false
                    invitedEmail.setError("Invalid Email")

                }

            }

        })


        invitedBtn.setOnClickListener {
            val name = invitedName.text.toString()
            val phone = invitedPhone.text.toString()
            val email = invitedEmail.text.toString()

            jsonobj.put("name", name)
            jsonobj.put("phone", phone)
            jsonobj.put("email", email)
            val que = Volley.newRequestQueue(this@User_invite)
            val req2 = JsonObjectRequest(
                Request.Method.POST,url,jsonobj,
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
}