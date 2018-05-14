package com.tharwa.solid.tharwa.View

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tharwa.solid.tharwa.Model.ExchangeRateItem
import com.tharwa.solid.tharwa.R

/**
 * Created by mac on 23/04/2018.
 */
class ExchangeRateAdapter (private val context: Context, arrayList: ArrayList<ExchangeRateItem>) : RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder>()
{
    internal var arrayList = ArrayList<ExchangeRateItem>()
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
        this.arrayList = arrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.exchange_rate_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.monaie_icon?.setImageResource(arrayList[position]!!.monaie_icon)
        holder.monaie_name?.text=arrayList[position].monaie_name
        holder.monaie_pays?.text=arrayList[position].monaie_pays
        holder.monaaie_value?.text=arrayList[position].monaie_value

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var monaie_icon: ImageView?=null
        var monaie_name: TextView?=null
        var monaie_pays: TextView?=null
        var monaaie_value: TextView?=null

        init {
            monaie_icon = itemView.findViewById(R.id.monaie_icon)
            monaie_name = itemView.findViewById(R.id.monaie_name)
            monaie_pays = itemView.findViewById(R.id.monaie_pays)
            monaaie_value = itemView.findViewById(R.id.monaie_value)
        }
    }
}