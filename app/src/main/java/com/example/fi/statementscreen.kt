package com.example.fi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout

class statementscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statementscreen)

        val yoursaving = findViewById<LinearLayout>(R.id.yoursaving)
        val table  = findViewById<HorizontalScrollView>(R.id.table)
        var n = 0




        yoursaving.setOnClickListener {

            if (n == 0) {
                    table.visibility = View.VISIBLE
                    n = 1
                }
            else {
                    table.visibility = View.GONE
                    n = 0
                }
        }





    }
}

