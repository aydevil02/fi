package com.example.fi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class RecyclerAdapter_ML_Table(val list : ArrayList<JSONObject>) : RecyclerView.Adapter<RecyclerAdapter_ML_Table.ViewHolder>()  {





    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RecyclerAdapter_ML_Table.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_ml_input , parent , false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter_ML_Table.ViewHolder, position: Int) {
        holder.date.text = list[position]["date"].toString()
        holder.description.text =list[position]["description"].toString()
        holder.deposit.text = list[position]["deposit"].toString()
        holder.withdraw.text = list[position]["withdrawl"].toString()
        holder.balance.text = list[position]["balance"].toString()
        holder.labels.text= list[position]["clusterid"].toString()
//        holder.month.text = list[position]["month"].toString()
//        holder.type.text=list[position]["type"].toString()
        var userinput_value : String? = null
//        var userinput = holder.userinput.selectedItem.toString()
        holder.userinput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                 userinput_value = adapterView?.getItemAtPosition(pos).toString()
                Log.d("Nova lOdu", holder.adapterPosition.toString())

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }




    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var date : TextView
        var description : TextView
        var deposit : TextView
        var withdraw : TextView
        var balance : TextView
        var labels : TextView
//        var month : TextView
//        var type : TextView
        var userinput : Spinner

        init {
            date = itemView.findViewById(R.id.datecolumn)
            description = itemView.findViewById(R.id.descriptioncolumn)
            deposit = itemView.findViewById(R.id.depositcolumn)
            withdraw = itemView.findViewById(R.id.withdrawcolumn)
            balance = itemView.findViewById(R.id.balancecolumn)
            labels = itemView.findViewById(R.id.labelcolumn)
//            month = itemView.findViewById(R.id.monthcolumn)
//            type = itemView.findViewById(R.id.typecolumn)
            userinput = itemView.findViewById(R.id.userinput)
        }




    }
}