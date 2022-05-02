package com.example.fi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class Recycler_ML_Output : RecyclerView.Adapter<Recycler_ML_Output.ViewHolder>() {
    var datevalueml = arrayOf("1")
    var descriptionvalueml = arrayOf("des")
    var depositvalueml = arrayOf("depositvalue")
    var withdrawvalueml = arrayOf("100")
    var balancevalueml = arrayOf("10000")
    var labelvalueml = arrayOf("0")
    var monthvalueml = arrayOf("9")
    var typevalueml = arrayOf("12")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Recycler_ML_Output.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardviewmltable,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Recycler_ML_Output.ViewHolder, position: Int) {
        holder.dateml.text = datevalueml[position]
        holder.descriptionml.text =descriptionvalueml[position]
        holder.depositml.text = depositvalueml[position]
        holder.withdrawml.text = withdrawvalueml[position]
        holder.balanceml.text = balancevalueml[position]
        holder.labelsml.text= labelvalueml[position]
        holder.monthml.text = monthvalueml[position]
        holder.typeml.text=typevalueml[position]
    }

    override fun getItemCount(): Int {
        return datevalueml.size
    }
    inner class ViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView){
        var dateml : TextView
        var descriptionml : TextView
        var depositml : TextView
        var withdrawml : TextView
        var balanceml : TextView
        var labelsml : TextView
        var monthml : TextView
        var typeml : TextView

        init {
            dateml = itemView.findViewById(R.id.datecolumnml)
            descriptionml = itemView.findViewById(R.id.descriptioncolumnml)
            depositml = itemView.findViewById(R.id.depositcolumnml)
            withdrawml = itemView.findViewById(R.id.withdrawcolumnml)
            balanceml = itemView.findViewById(R.id.balancecolumnml)
            labelsml = itemView.findViewById(R.id.labelcolumnml)
            monthml = itemView.findViewById(R.id.monthcolumnml)
            typeml = itemView.findViewById(R.id.typecolumnml)
        }
    }
}