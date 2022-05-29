package com.example.fi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.util.ArrayList

class Recycler_Selected_Files( val Selected_Files_Name: ArrayList<String>, val context: Context) : RecyclerView.Adapter<Recycler_Selected_Files.ViewHolder>(){



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Recycler_Selected_Files.ViewHolder {
        val v  = LayoutInflater.from(parent.context).inflate(R.layout.recyclerforselectedfiles,parent ,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Recycler_Selected_Files.ViewHolder, position: Int) {
//        holder.fileName.text = filename[position]
        Log.d("RE","${Selected_Files_Name.size}")
        holder.fileName.text=Selected_Files_Name.get(position)
        holder.delete.setOnClickListener {
            Selected_Files_Name.removeAt(position)
            notifyDataSetChanged()

        }

    }

    override fun getItemCount(): Int {
        return Selected_Files_Name.size
    }
    inner class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        var fileName : TextView
//        var filePath : TextView
        var delete : ImageView

        init {
            fileName = itemView.findViewById(R.id.FileName)
            delete =itemView.findViewById(R.id.delete)
        }
    }
}