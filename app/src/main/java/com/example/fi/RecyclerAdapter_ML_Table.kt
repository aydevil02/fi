package com.example.fi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class RecyclerAdapter_ML_Table(val list : ArrayList<JSONObject>,val context : Context) : RecyclerView.Adapter<RecyclerAdapter_ML_Table.ViewHolder>()  {

    var cluster_group = "-1"
    var i = 0
    var userInputs = JSONObject()
    var diffPos : ArrayList<Int> = ArrayList<Int>();
    fun submitUserInput()
    {
        Log.d("nova", "LOgg Success\n$userInputs")
        SendRequest.sendUserInput(userInputs,context)
    }

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
        Log.d("Cluster"," : ${cluster_group} : ${list[position]["clusterid"].toString()}")
//         if( cluster_group !=  list[position]["clusterid"].toString()) {
//             cluster_group = list[position]["clusterid"].toString()
//         }
        if(diffPos.contains(position)){
            holder.userinput.visibility = View.VISIBLE
        }
        else{
             holder.userinput.visibility = View.GONE
         }
        Log.d("TAGGED","Position: $position ; Index: $i ; Adapter: ${holder.adapterPosition} ; Layout: ${holder.layoutPosition} ; Size: ${list.size}")
        i++;
        holder.userinput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                userinput_value = adapterView?.getItemAtPosition(pos).toString()
                Log.d("Nova", holder.adapterPosition.toString())
                userInputUpdate(holder.labels.text.toString(), userinput_value!!)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }




    }

    fun userInputUpdate(clusterID: String, input : String)
    {
//        var  = list
        val obj = userInputs
        for(item in list)
        {
            if(item["clusterid"].toString() == clusterID)
            {


                obj.put(item["id"].toString(),input)
            }
        }
        userInputs = obj
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun diffSpinnerPos(){
        var id = "-1"
        for (i in 0 until list.size-1)
        {
            if(list[i]["clusterid"].toString() != id)
            {
                diffPos.add(i)
                id = list[i]["clusterid"].toString()
            }
        }
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
            diffSpinnerPos()
        }




    }


}