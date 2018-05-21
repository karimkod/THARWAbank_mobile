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
    val accountsType: ArrayList<String> by lazy { createCounts()}


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val infalter = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = infalter.inflate(R.layout.account_list_item,parent,false)
        row.findViewById<TextView>(R.id.type_account).text = accountsType[position]
        return row
    }

    override fun getItem(position: Int): Any = accountsType[position]

    override fun getItemId(position: Int): Long = accounts[position].toLong()

    override fun getCount(): Int = accountsType.size

    fun createCounts():ArrayList<String>
    {
        var list = ArrayList<String>()
        for (i in accounts)
        {
            when (i)
            {
                1-> list.add("Compte courant")
                2-> list.add("Compte epargne")
                3-> list.add("Compte euro")
                4-> list.add("Compte dollars")
                else -> list.add("Erreur")

            }
        }

        return list;
    }
}