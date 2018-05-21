package com.tharwa.solid.tharwa.View

import Adapters.CountChangeAdapter
import Adapters.TransferListAdapter
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.activity_client_acount.*
import kotlinx.android.synthetic.main.nav_header.view.*
import android.graphics.BitmapFactory
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.View.Virment.VirToMeFragment
import com.tharwa.solid.tharwa.Repositories.Injection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URL
import java.text.NumberFormat


class ClientAcountActivity : AppCompatActivity(),AdapterView.OnItemClickListener{
    private var pageAdapter: CostomPagerAdapter? = null
    private var pager: ViewPager? = null
    protected var navigatorView: NavigationView? = null


    var transferDialog:AlertDialog? = null

    var changeAccountDialog:AlertDialog? = null

    val userRepository by lazy {
        Injection.provideUserRepository()
    }

    val accountRepository by lazy{
        Injection.provideAccountRepository()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_acount)
        setSupportActionBar(findViewById(R.id.acount_toolbar))

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_hamburger)
            setTitle("THARWA")
        }

        // buttomNavigation = findViewById(R.id.bottom_navigation)
        BottomSheetBehavior.from(floatingButtons)

        //buttomNavigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //Log.d("ClientAccountActivity",.user.toString())

        transfer_money.setOnClickListener{openTransferDialog()}
        change_account.setOnClickListener{openChangeCompte()}

    }



    override fun onResume()
    {   super.onResume()
        val user = userRepository.userInfo
        navigatorView = nav_view
        nav_view.getHeaderView(0).user_name_view?.text = user.name

        //loadImageTask(navigatorView?.getHeaderView(0)!!.user_photo!!).execute(user.photoPath)
       /* id_count_view.text = accountRepository.getSelectedAccount().id
        balance_count_view.text = "${accountRepository.getSelectedAccount().balance} ${accountRepository.getSelectedAccount().currency}"
*/
        updateBalance()

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
                Toast.makeText(this@ClientAcountActivity, "MES COMTES", Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.buttom_navigation_taux_changes -> {
                // "Taux de changes"  clicked
                Toast.makeText(this@ClientAcountActivity, "TAUX DE CHANGES", Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.buttom_navigation_virements -> {
                // "virements clicked"  clicked
                Toast.makeText(this@ClientAcountActivity, "VIREMENTS", Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.actualiser ->
            {
                updateBalance()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }



    class loadImageTask( val imageView:ImageView):AsyncTask<String,Unit,Bitmap>()
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

    fun openTransferDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.dialog_listview, null, false)
        val lv = row.findViewById<ListView>(R.id.transfer_type_list)

        lv.adapter = TransferListAdapter(accountRepository.availableAccountsType,this)
        lv.onItemClickListener = this
        dialogBuilder.setView(row)
        dialogBuilder.setTitle("Quel type de virement?")
        transferDialog = dialogBuilder.create().apply { show() }

    }


    fun openChangeCompte()
    {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.dialog_listview, null, false)
        val lv = row.findViewById<ListView>(R.id.transfer_type_list)
        Log.d("ClientAccountActivity", Injection.provideAccountRepository().availableAccountsType.toString())
        lv.adapter = CountChangeAdapter(Injection.provideAccountRepository().availableAccountsType, this)
        lv.setOnItemClickListener{parent: AdapterView<*>?, view: View?, position: Int, id: Long ->

            Injection.provideAccountRepository().selectedAccount = id.toInt()
            updateBalance()
            changeAccountDialog!!.dismiss()

        }
        dialogBuilder.setView(row)
        dialogBuilder.setTitle("Quel compte voulez vous choisir?")
        changeAccountDialog = dialogBuilder.create()
        changeAccountDialog!!.show()

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {

            0 -> Toast.makeText(this, "Virrerrr", Toast.LENGTH_SHORT).show()
            1 -> {
                Toast.makeText(this, "Pas implémenté", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, Injection.provideAccountRepository().availableAccountsType.toString(), Toast.LENGTH_LONG).show()
            }
            2 -> {
                if (Injection.provideAccountRepository().availableAccountsType.size == 1)
                    Toast.makeText(this, resources.getString(R.string.curr_only), Toast.LENGTH_SHORT).show()
                else {
                    transferDialog?.dismiss()
                    val dialog = VirToMeFragment()
                    val ft = supportFragmentManager.beginTransaction()
                    dialog.show(ft, ContentValues.TAG)
                }
            }
            else -> {
            }

        }
    }

    fun updateBalance()
    {

        val disposable = UserApiService.create().getAccountInfo(userRepository.accessInfos.token,Injection.provideAccountRepository().selectedAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->

                            if (response.isSuccessful) {

                                val money = response.body()?.balance
                                val formatter = NumberFormat.getCurrencyInstance()
                                val moneyString = formatter.format(money).removePrefix("$")

                                balance_count_view.text =  moneyString+" "+ response.body()?.currency
                                id_count_view.text = response.body()?.id
                                count_type_view.text = when (response.body()?.type)
                                                        {
                                                            1-> "Compte courant"
                                                            2 -> "Compte epargne"
                                                            3 -> "Compte Euro"
                                                            4-> "Compte Dollars"
                                                            else -> "Erreur"
                                                        }
                            }else
                            {
                                Log.e("ClientAccoutActivity",response.toString())
                            }

                        },
                        { error ->
                            Log.e("ClientAccoutActivity","Error ")
                        }
                )
    }

}
