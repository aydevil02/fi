package com.example.fi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject

class Ml_output : AppCompatActivity() {
    private lateinit var Tag : String
    private lateinit var Recycler_ml_output : RecyclerView


//    val TAG = "Ml_output_TAG"
    private var layoutManager : RecyclerView.LayoutManager?= null
    private var adapter : RecyclerView.Adapter<Recycler_ML_Output.ViewHolder> ?= null
    var list = ArrayList<JSONObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ml_output)
        init()

//        val recycler_ml_output = findViewById<RecyclerView>(R.id.recycler_ml_output)

        Get_CashFlow();

        layoutManager = LinearLayoutManager(this)
        Recycler_ml_output.layoutManager = layoutManager
        adapter = Recycler_ML_Output(list,applicationContext)
        Recycler_ml_output.adapter = adapter


    }
    private fun init(){
        Tag= "Ml_output_TAG"
        Recycler_ml_output = findViewById<RecyclerView>(R.id.recycler_ml_output)

    }

    private fun Get_CashFlow() {
        val cashflow_data = SendRequest(applicationContext).getCashFlowData()
        val iter: Iterator<String> = cashflow_data.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            try {
                val value: JSONObject = cashflow_data.get(key) as JSONObject
                Log.d(Tag, key)
                Log.d(Tag, value.toString())

                list.add(value)

            } catch (e: JSONException) {
                // Something went wrong!
                Log.d(Tag, e.toString())
            }
        }
        Log.d(Tag, list[0].toString())
    }
}