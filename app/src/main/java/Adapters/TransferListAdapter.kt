package Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tharwa.solid.tharwa.R

/**
 * Created by thinkpad on 16/04/2018.
 */
class TransferListAdapter(val accounts:Array<Int>,val context: Context):BaseAdapter()
{

    val transferTypesArrays: Array<String> by lazy { createTransferType()}


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val infalter = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = infalter.inflate(R.layout.virement_type_list_item,parent,false)
        row.findViewById<TextView>(R.id.type_transfer).text = transferTypesArrays[position]
        return row
    }

    override fun getItem(position: Int): Any = transferTypesArrays[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = transferTypesArrays.size

    fun createTransferType():Array<String> = when(accounts.size)
    {
        1-> arrayOf("Virement vers Tharwa","Virement vers externe")
        2,3,4-> arrayOf("Virement vers Tharwa","Virement vers externe","Virement vers mon compte")
        else -> arrayOf("Error")
    }
}