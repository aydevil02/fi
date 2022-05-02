package com.example.fi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Ml_output : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager?= null
    private var adapter : RecyclerView.Adapter<Recycler_ML_Output.ViewHolder> ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ml_output)

        val recycler_ml_output = findViewById<RecyclerView>(R.id.recycler_ml_output)

        layoutManager = LinearLayoutManager(this)
        recycler_ml_output.layoutManager = layoutManager
        adapter = Recycler_ML_Output()
        recycler_ml_output.adapter = adapter


    }
}