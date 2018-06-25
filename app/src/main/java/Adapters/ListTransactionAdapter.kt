package Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tharwa.solid.tharwa.Model.Transaction
import com.tharwa.solid.tharwa.R

/**
 * Created by mac on 18/05/2018.
 */
class ListTransactionAdapter(private val context: Context, arrayList: ArrayList<Transaction>) : RecyclerView.Adapter<ListTransactionAdapter.ViewHolder>()
{
    private var arrayList = ArrayList<Transaction>()
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    init {
        this.arrayList = arrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (arrayList[position].statuts){
          0 ->  holder.transaction_icon?.setImageResource(R.drawable.ic_virement_en_attente)
          1 -> if (arrayList[position].E_S == 2) holder.transaction_icon?.setImageResource(R.drawable.ic_virement_valide)
          else holder.transaction_icon?.setImageResource(R.drawable.ic_virement_entrant)
          2 ->   holder.transaction_icon?.setImageResource(R.drawable.ic_virement_bloque)

          //else ->  holder.transaction_icon?.setImageResource(R.drawable.ic_virement_entrant)
        }

        if(arrayList[position].E_S == 1) {
            holder.transaction_date?.text = arrayList[position].date.toString()
            holder.transaction_montant?.text = "+ "+ String.format("%,.2f", arrayList[position].montant.toFloat()) + "DZD"
            holder.transaction_acount?.text = "De: "+arrayList[position].acount_number
            holder.transaction_commission?.text = " "
        }else{
            holder.transaction_date?.text = arrayList[position].date.toString()
            holder.transaction_montant?.text = "- "+  String.format("%,.2f", arrayList[position].montant.toFloat()) + " DZD"
            holder.transaction_acount?.text = "Vers: "+arrayList[position].acount_number
            holder.transaction_commission?.text = "- " +  String.format("%,.2f", arrayList[position].montant_commission.toFloat()) + " DZD"
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var transaction_icon: ImageView?=null
        var transaction_date: TextView?=null
        var transaction_montant: TextView?=null
        var transaction_acount: TextView?=null
        var transaction_commission: TextView?=null

        init {
            transaction_icon = itemView.findViewById(R.id.icon_transaction)
            transaction_date = itemView.findViewById(R.id.date_transaction)
            transaction_montant = itemView.findViewById(R.id.transaction_motant)
            transaction_acount = itemView.findViewById(R.id.transaction_num_acount)
            transaction_commission = itemView.findViewById(R.id.transaction_commission)
        }
    }


    fun addListTransactions(newTransactions:ArrayList<Transaction>)
    {
        arrayList = ArrayList(arrayList.plus(newTransactions))
        notifyDataSetChanged()
    }
}