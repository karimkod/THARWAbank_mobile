package Adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tharwa.solid.tharwa.R
import android.widget.BaseAdapter

/**
 * Created by LE on 07/05/2018.
 */
class CountChangeAdapter (val accounts:Array<Int>,val context: Context): BaseAdapter()
{
    val countsChange: Array<String> by lazy { createCounts()}


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val infalter = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = infalter.inflate(R.layout.virement_type_list_item,parent,false)
        row.findViewById<TextView>(R.id.type_transfer).text = countsChange[position]
        return row
    }

    override fun getItem(position: Int): Any = countsChange[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = countsChange.size

    fun createCounts():Array<String> = when(accounts.size)
    {
        1-> arrayOf("EPARGNE","USD","EURO")
        2,3,4-> arrayOf("Change vers compte Epargne"," Changer vers compte USD","Cahnger vers compte EURO")

        else -> arrayOf("Error")
    }
}