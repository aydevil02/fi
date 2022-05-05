package com.example.fi

import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fi.Constant.PUSH_USER_INPUT
import com.example.fi.helper.Sharedpereference
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class Mlmodel: AppCompatActivity() {

    private val TAG = "ML_TAG"

    private var LayoutManager: RecyclerView.LayoutManager? = null
//    private var adapter : RecyclerView.Adapter<RecyclerAdapter_ML_Table.ViewHolder>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ml_model)

//        val get_data = GetData(applicationContext)
        val submitBttn = findViewById<Button>(R.id.submitUserInput)
//        get_data.getjsondata()
//        Log.d("Abhi", get_data.toString())
        val extraction_data = SendRequest.getExtractionData(applicationContext);
//        val list = List<JSONObject> ?= null
        val list = ArrayList<JSONObject>()
        val iter: Iterator<String> = extraction_data.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            try {
                val value: JSONObject = extraction_data.get(key) as JSONObject
                Log.d("nova", key)
                Log.d("nova", value["date"] as String)

                list.add(value)

            } catch (e: JSONException) {
                // Something went wrong!
                Log.d("nova-exception", e.toString())
            }
        }
        Log.d("nova", list[0]["date"] as String)
        val recycler_ml_table = findViewById<RecyclerView>(R.id.recycler_ml_table)

        LayoutManager = LinearLayoutManager(this)

        val adapter = RecyclerAdapter_ML_Table(list, applicationContext)

        recycler_ml_table.layoutManager = LayoutManager
//        adapter= RecyclerAdapter_ML_Table(list)
        recycler_ml_table.adapter = adapter
        submitBttn.setOnClickListener {
            adapter.submitUserInput()
            var status : String = SendRequest.Run_ml(applicationContext)
            if (status!=null){
            if (status.lowercase()=="success"){
                startActivity(Intent(this , Loadind_Screen_Get_Cashflow::class.java))
            }
            else{
                Toast.makeText(this, "Something went wrong " ,Toast.LENGTH_LONG).show() }
            }

        }

//        recycler_ml_table.adapter


    }


}


