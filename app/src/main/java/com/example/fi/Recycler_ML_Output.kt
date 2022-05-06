package com.example.fi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class Recycler_ML_Output(val list : ArrayList<JSONObject>, val context : Context) : RecyclerView.Adapter<Recycler_ML_Output.ViewHolder>() {
//    var datevalueml = arrayOf("1")
//    var descriptionvalueml = arrayOf("des")
//    var depositvalueml = arrayOf("depositvalue")
//    var withdrawvalueml = arrayOf("100")
//    var balancevalueml = arrayOf("10000")
//    var labelvalueml = arrayOf("0")
//    var monthvalueml = arrayOf("9")
//    var typevalueml = arrayOf("12")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Recycler_ML_Output.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardviewmltable,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Recycler_ML_Output.ViewHolder, position: Int) {
        holder.dateml.text = list[position]["year"].toString() + "/" + list[position]["month"].toString()
        holder.inflow.text =list[position]["inflow"].toString()
        holder.outflow.text = list[position]["outflow"].toString()
        holder.fixed.text = list[position]["fixed"].toString()
        holder.variable.text = list[position]["variable"].toString()
//        holder.labelsml.text= labelvalueml[position]
        holder.netCash.text = list[position]["netcashflow"].toString()
        holder.review.text=list[position]["review"].toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView){
        var dateml : TextView
        var inflow : TextView
        var outflow : TextView
        var fixed : TextView
        var variable : TextView
//        var labelsml : TextView
        var netCash : TextView
        var review : TextView

        init {
            dateml = itemView.findViewById(R.id.datecolumnml)
            inflow = itemView.findViewById(R.id.cashInflow)
            outflow = itemView.findViewById(R.id.cashOutflow)
            fixed = itemView.findViewById(R.id.fixed)
            variable = itemView.findViewById(R.id.variable)
//            labelsml = itemView.findViewById(R.id.)
            netCash = itemView.findViewById(R.id.netcashflow)
            review = itemView.findViewById(R.id.review)
        }
    }
}