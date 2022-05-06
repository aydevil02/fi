package com.example.fi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject


class Mlmodel: AppCompatActivity() {

    private val TAG = "ML_TAG"
    private lateinit var progressBar : ProgressBar
    private var LayoutManager: RecyclerView.LayoutManager? = null
//    private var adapter : RecyclerView.Adapter<RecyclerAdapter_ML_Table.ViewHolder>?=null
    lateinit var adapter : RecyclerAdapter_ML_Table
    lateinit var recycler_ml_table : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ml_model)
//        progressBar = findViewById(R.id.uploadpdfprogress)
//        val get_data = GetData(applicationContext)
        val submitBttn = findViewById<Button>(R.id.submitUserInput)
        progressBar = findViewById(R.id.modelViewProgressbar)
        recycler_ml_table = findViewById(R.id.recycler_ml_table)
//        get_data.getjsondata()
//        Log.d("Abhi", get_data.toString())
        startTheScripts()


//        val list = List<JSONObject> ?= null

        submitBttn.setOnClickListener {
            adapter.submitUserInput()
            var status : String = SendRequest.Run_ml(applicationContext)
            if (status!=null){
            if (status.lowercase()=="success"){
                Toast.makeText(this, "Successfully updated analytical data", Toast.LENGTH_LONG).show()
                startActivity(Intent(this , Ml_output::class.java))
            }
            else{
                Toast.makeText(this, "Something went wrong " ,Toast.LENGTH_LONG).show() }
            }

        }

//        recycler_ml_table.adapter


    }

    private fun startTheScripts() {
        Toast.makeText(this, "Scanning your statement", Toast.LENGTH_SHORT).show()
        val status_extraction = SendRequest.Run_Extraction(applicationContext)
        Toast.makeText(this, "Running analytics on it", Toast.LENGTH_SHORT).show()
        val status_analytics_run = SendRequest.Run_ml(applicationContext)
        if(status_extraction.equals("success", ignoreCase = true) && status_analytics_run.equals("success", ignoreCase = true))
        {
            Toast.makeText(this, "Fetching your Analytics data", Toast.LENGTH_SHORT).show()
            val analytics_data = SendRequest.getAnalyticsData(applicationContext)
            val list = ArrayList<JSONObject>()
            val iter: Iterator<String> = analytics_data.keys()
            while (iter.hasNext()) {
                val key = iter.next()
                try {
                    val value: JSONObject = analytics_data.get(key) as JSONObject
                    Log.d("nova", key)
                    Log.d("nova", value["date"] as String)

                    list.add(value)

                } catch (e: JSONException) {
                    // Something went wrong!
                    Log.d("nova-exception", e.toString())
                }
            }
            Log.d("nova", list[0]["date"] as String)
            initializeRecycleView(list)
        }
    }

    fun initializeRecycleView(list: ArrayList<JSONObject> )
    {
        progressBar.visibility = View.GONE
        LayoutManager = LinearLayoutManager(this)

        adapter = RecyclerAdapter_ML_Table(list, applicationContext)

        recycler_ml_table.layoutManager = LayoutManager
//        adapter= RecyclerAdapter_ML_Table(list)
        recycler_ml_table.adapter = adapter
    }

    fun executeExtraction()
    {
        try {
            SendRequest.Run_Extraction(applicationContext)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

}


