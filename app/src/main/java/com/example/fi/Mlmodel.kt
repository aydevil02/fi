package com.example.fi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fi.helper.GetData
import okhttp3.*
import org.json.JSONObject
import java.util.concurrent.Executors


class Mlmodel: AppCompatActivity() {

    private val TAG = "ML_TAG"
    private lateinit var progressBar: ProgressBar
    private var LayoutManager: RecyclerView.LayoutManager? = null

    //    private var adapter : RecyclerView.Adapter<RecyclerAdapter_ML_Table.ViewHolder>?=null
    lateinit var adapter: RecyclerAdapter_ML_Table
    lateinit var recycler_ml_table: RecyclerView

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

            var status: String = SendRequest(applicationContext).Run_ml()
            if (status != null) {
                if (status.lowercase() == "success") {
                    Toast.makeText(this, "Successfully updated analytical data", Toast.LENGTH_LONG)
                        .show()
                    startActivity(Intent(this, Ml_output::class.java))
                } else {
                    Toast.makeText(this, "Something went wrong ", Toast.LENGTH_LONG).show()
                }
            }

        }

//        recycler_ml_table.adapter


    }


    private fun getClusterData()
    {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            /*
            * Your task will be executed here
            * you can execute anything here that
            * you cannot execute in UI thread
            * for example a network operation
            * This is a background thread and you cannot
            * access view elements here
            *
            * its like doInBackground()
            * */
            val sendRequest = SendRequest(applicationContext) // Separation Required fro Here
            val status_extraction = sendRequest.RunallModels()

            val resp = GetData.getClusterData(applicationContext)
            val analytics_data = resp
            val list = ArrayList<JSONObject>()
            val iter: Iterator<String> = analytics_data.keys()
            while (iter.hasNext()) {
                val key = iter.next()
                try {
                    val value: JSONObject = analytics_data.get(key) as JSONObject
                    Log.d("nova", key)
                    Log.d("nova", value["date"] as String)

                    list.add(value)

                } catch (e : Exception) {
                    // Something went wrong!
                    Log.d("nova-exception", e.toString())
                }
            }

            Log.d(TAG,"Prince Response $resp")

            handler.post {
                /*
                * You can perform any operation that
                * requires UI Thread here.
                *
                * its like onPostExecute()
                * */
                initializeRecycleView(list)
            }
        }

    }

    private fun startTheScripts() {
        Toast.makeText(this, "Scanning your statement", Toast.LENGTH_SHORT).show()
//        val sendRequest = SendRequest(applicationContext)
//        val status_extraction = sendRequest.RunallModels()
        getClusterData()
//        Toast.makeText(this, "Running analytics on it  ${status_extraction}", Toast.LENGTH_SHORT).show()

//        val getAnalytics  = GetAnalyticsData(
//            applicationContext,
//            object : After_Request {
//                override fun onAnalyticsDataFetch(jsonObject: JSONObject) {
//                    progressBar.visibility = View.GONE
//                      Toast.makeText(this@Mlmodel, "Process Complete  ${jsonObject.length()}", Toast.LENGTH_SHORT).show();
//                       if(status_extraction)
//                        {
//        val analytics_data = jsonObject
//        val list = ArrayList<JSONObject>()
//        val iter: Iterator<String> = analytics_data.keys()
//        while (iter.hasNext()) {
//            val key = iter.next()
//            try {
//                val value: JSONObject = analytics_data.get(key) as JSONObject
////                Log.d("nova", key)
////                Log.d("nova", value["date"] as String)
//
//                list.add(value)
//
//            } catch (e : Exception) {
//                // Something went wrong!
//                Log.d("nova-exception", e.toString())
//            }
//        }
////        Log.d("nova", list[0].toString())
//        initializeRecycleView(list)
//    }
//                }
//            }
//        )
//        getAnalytics.execute()

//                SendRequest.uploadPdf(file_path , getApplicationContext());

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
//
//    fun executeExtraction()
//    {
//        try {
//            SendRequest.Run_Extraction(applicationContext)
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//    }

}


