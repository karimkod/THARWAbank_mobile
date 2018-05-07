package com.tharwa.solid.tharwa.View

import Adapters.TransferListAdapter
import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.tharwa.solid.tharwa.Model.UserData
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.activity_client_acount.*
import kotlinx.android.synthetic.main.nav_header.view.*
import android.graphics.BitmapFactory
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.tharwa.solid.tharwa.Remote.UserApiService
import java.net.URL


class ClientAcountActivity : AppCompatActivity(),AdapterView.OnItemClickListener {


    private var pageAdapter: CostomPagerAdapter? = null
    private var pager: ViewPager? = null
    protected var navigatorView:NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_acount)
        setSupportActionBar(findViewById(R.id.acount_toolbar))

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_hamburger)
            setTitle("THARWA")
        }

        // les fragments de la tab Barre
        pageAdapter = CostomPagerAdapter(supportFragmentManager)
        //pager = findViewById<ViewPager>(R.id.homeViewPager)

        pageAdapter?.addFragment(VirementsFragment(), "Virements")
        pageAdapter?.addFragment(CommissionFragment(), "Commissions")

        pager?.adapter = pageAdapter
        //homeTabBarre.setupWithViewPager(pager)

       // buttomNavigation = findViewById(R.id.bottom_navigation)
        BottomSheetBehavior.from(floatingButtons)

        //buttomNavigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        Log.d("ClientAccountActivity",UserData.user.toString())

        transfer_money.setOnClickListener{openTransferDialog()}



    }


    override fun onResume()
    {   super.onResume()
        val user = if(UserData.user != null) UserData.user!! else return
        navigatorView = nav_view
        nav_view.getHeaderView(0).user_name_view?.text = user.name

        loadImageTask(navigatorView?.getHeaderView(0)!!.user_photo!!).execute(user.photoPath)
        id_count_view.text = user.currentAccount.accountCode
        balance_count_view.text = "${user.currentAccount.balance} DZD"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.acount_action_bar_menu, menu)
        return true
    }

    // Gestion des evenements de Buttom Navigation Bar
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.buttom_navigation_comptes -> {
                //"Mes comptes" clicked
                Toast.makeText(this@ClientAcountActivity,"MES COMTES",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.buttom_navigation_taux_changes -> {
                // "Taux de changes"  clicked
                Toast.makeText(this@ClientAcountActivity,"TAUX DE CHANGES",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.buttom_navigation_virements -> {
                // "virements clicked"  clicked
                Toast.makeText(this@ClientAcountActivity,"VIREMENTS",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId)
        {
            android.R.id.home ->
            {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }


    class loadImageTask(val imageView:ImageView):AsyncTask<String,Unit,Bitmap>()
    {
        override fun doInBackground(vararg params: String?): Bitmap
        {

            val inputStream = URL("${UserApiService.URL}images/customer/${params[0]}").openStream()
            return BitmapFactory.decodeStream(inputStream)

        }

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }

    }

    fun openTransferDialog()
    {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.dialog_listview,null,false)
        val lv = row.findViewById<ListView>(R.id.transfer_type_list)
        Log.d("ClientAccountActivity",UserData.user!!.accountTypes.toString())
        lv.adapter = TransferListAdapter(UserData.user!!.accountTypes,this)
        lv.onItemClickListener = this
        dialogBuilder.setView(row)
        dialogBuilder.setTitle("Quel type de virement?")
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    {
        if(position == 0)
        {
            //Toast.makeText(this,"Virrerrr",Toast.LENGTH_SHORT).show()
        }else if(position == 1)
        {
            Toast.makeText(this ,"Pas implémenté",Toast.LENGTH_SHORT).show()
        }
    }


}
