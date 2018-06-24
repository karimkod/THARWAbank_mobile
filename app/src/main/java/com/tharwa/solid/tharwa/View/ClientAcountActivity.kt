package com.tharwa.solid.tharwa.View

import Adapters.ListTransactionAdapter

import Adapters.CountChangeAdapter
import Adapters.TransferListAdapter
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.activity_client_acount.*
import kotlinx.android.synthetic.main.nav_header.view.*
import android.graphics.BitmapFactory
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.tharwa.solid.tharwa.Model.Transaction
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.View.Virment.VirToMeFragment
import com.tharwa.solid.tharwa.Repositories.Injection
import com.tharwa.solid.tharwa.View.Virment.VirementTharwaActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.URL


class ClientAcountActivity : AppCompatActivity(),AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {


    protected var navigatorView:NavigationView? = null
    private  var listTransactions=  ArrayList<Transaction>()
    private  var recyclerView: RecyclerView? = null
    private  var mAdapter:ListTransactionAdapter? = null
    private  var swipeRefreshLayout:SwipeRefreshLayout? = null
    private lateinit var bottomSheet:BottomSheetBehavior<*>


    private var currentPage = 1
    private var maxPages = 0

    private var isLoading = false



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
            title = getString(R.string.app_name).toUpperCase()
        }
/*
        val accountTypes:Array<Int> = arrayOf(0,3)
        UserData.user = TokenResponse("Naziha",
                "avatar_1526485281_84874485341653f1915de1128373c36df1304dc60e772b81b44563437470ef7419b139aa9acfb7ac78e59a6d33fd_a8898282b01a580375d057dcd57ccc58ac2183cc.png"
                  ,0,accountTypes, currentAccountDetail("THW000001DZD",20000.0), "5"
                ,"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjkyNDhhNTU2NDlhMjA4ZDI4MGZmZTQyNTk2MmZhNzIzODJlMGIwYTM4MTYyNDZlNTU2Y2FmM2VjNWViMGRkMzNlOWJkMDNmMGM2M2NiOWEzIn0.eyJhdWQiOiIyIiwianRpIjoiOTI0OGE1NTY0OWEyMDhkMjgwZmZlNDI1OTYyZmE3MjM4MmUwYjBhMzgxNjI0NmU1NTZjYWYzZWM1ZWIwZGQzM2U5YmQwM2YwYzYzY2I5YTMiLCJpYXQiOjE1MjY3NTExMjgsIm5iZiI6MTUyNjc1MTEyOCwiZXhwIjoxNTI2NzU4MzI3LCJzdWIiOiI1Iiwic2NvcGVzIjpbImN1c3RvbWVyIl19.yndwK-GWpmC64-Wpk41IXdCaoA5cMcByBaogh2jH_1ry8nHi_9rd-W6XORx4iT3FirGRaYl6B6u1uZ8UoHl5PEt1VeP1UaD6Ml9i6bKLrk5Z7irKBAYV_KVgSKdM_YpwkNJeETh_6__C7Vgx5YOwxIusHFATQDq1SUORbLisjcWk3YwqvZ8s66TPxM2iFtUVV8GIZmLPpqk03Jc2L1br5EMWbNy-SrBCyxO6Uw8P2UuuxuT0g1zn1C8pTT9bM7V6uE9Mjb_KIzpnUBc4Bo95r3ASIqcl9e71X-bHYF1BswbNxrGBsp-4eXwPJEWTd3NlBJSOQXFQw5kotyNRwOFEtCQc4Z4VnCDB-d91eAAgVbwtD2XcCPnK_5IxWOpJ8KirS18_kztzB701rJGusqGwiHPQAHhcgBfzqC9NQTWLDb6F1Lc7kSvpZn6IFaQojJE4lwHAuBHioVvvvtqLiaZHOU75oByCk_hLQojeUQuW1PFm5IrA3Z3a9qprpvwAXYlOGTrgGwBYDof12goG6ZuUAdYDNloSStbRcFhdHF9JGQuaCDMZBdGdQSec4XEB8yORNVPZviH-QdfUZjaJiLsD2gloG-rb7Fx0-WG2cEeZ0t7OPokByMBcJ3VX6yzwbt2bO9qvbSlVd5PvqWAHL0oSVBjRazU7eSdIhUdLuf6S2IM"
                ,"7199")
*/

        bottomSheet =  BottomSheetBehavior.from(floatingButtons)

        transfer_money.setOnClickListener{openTransferDialog()}

        exchange_rate.setOnClickListener{  openExchangeRate()}

        change_account.setOnClickListener{openChangeCompte()}

        recyclerView = findViewById(R.id.recycle_view_historique)

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    hideFloatingButtons()
                } else if (dy < 0) {
                    showFloatingButtons()
                }


            }
        })

        swipeRefreshLayout =  findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout!!.setOnRefreshListener(this)
        swipeRefreshLayout!!.post { getNewTransaction() }
    }

    override fun onRefresh() {
        getNewTransaction()
    }


    fun hideFloatingButtons()
    {
        val listOfHiddeable = listOf<View>(transfer_money,transfer_money_txt,
                exchange_rate,exchange_rate_txt, change_account,change_account_txt)
        bottomSheet.isHideable = true
        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN


        for (i in listOfHiddeable)
            i.visibility = View.INVISIBLE

    }

    fun showFloatingButtons()
    {
        val listOfHiddeable = listOf<View>(transfer_money,transfer_money_txt,
                exchange_rate,exchange_rate_txt, change_account,change_account_txt)
        bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheet.isHideable = false

        for (i in listOfHiddeable)
            i.visibility = View.VISIBLE



    }


    fun getNewTransaction(){
        swipeRefreshLayout!!.isRefreshing = true

        swipeRefreshLayout!!.isRefreshing = false
    }

    fun  openExchangeRate(){

        val intent =Intent(this, ExchangeRateActivity::class.java)
        startActivity(intent)

    }


    override fun onResume()
    {   super.onResume()
        val user = userRepository.userInfo
        navigatorView = nav_view
        nav_view.getHeaderView(0).user_name_view?.text = user.name

        //loadImageTask(navigatorView?.getHeaderView(0)!!.user_photo!!).execute(user.photoPath)

        updateBalance()
        //updateHistory()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.acount_action_bar_menu, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            R.id.actualiser ->
            {
                updateBalance()
                true
            }
            else -> super.onOptionsItemSelected(item)
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

            0 -> {
                val intent = Intent(this, VirementTharwaActivity::class.java)
                startActivity(intent)
                transferDialog?.dismiss()
            }
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
                                val moneyString = String.format("%,.2f", money)
                                accountRepository.cachedAccounts[response.body()?.type!!] = response.body()!!
                                balance_count_view.text =  moneyString+" "+ response.body()?.currency
                                id_count_view.text = response.body()?.id

                                count_type_view.text = when (response.body()?.type)
                                                        {
                                                            1-> getString(R.string.account_current)
                                                            2 -> getString(R.string.account_epargne)
                                                            3 -> getString(R.string.account_euro)
                                                            4-> getString(R.string.account_dollars)
                                                            else -> getString(R.string.Erreur)
                                                        }

                                updateHistory()

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


    fun updateHistory(){

        if(isLoading)
            return
        isLoading = true
        val id:Int = (Injection.provideAccountRepository().getSelectedAccount().id.subSequence(3,9)).toString().toInt()

        val disposable: Disposable = UserApiService.create().getHistory(Injection.provideUserRepository().accessInfos.token,id,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->

                            isLoading = false
                            if (response.isSuccessful) {

                                listTransactions = ArrayList()

                                val array = response.body()!!.data

                                maxPages = response.body()?.to!!


                                for (i in array.indices) {

                                    var num_acc_receiver:String = array[i].num_acc_receiver.toString()
                                    var num_acc_sender:String = array[i].num_acc_sender .toString()
                                    while (num_acc_receiver.length < 6 ){
                                       num_acc_receiver = "0"+num_acc_receiver
                                    }
                                    while (num_acc_sender.length < 6 ){
                                        num_acc_sender = "0"+num_acc_sender
                                    }

                                    val receiver:String =  array[i].code_bnk_receiver + num_acc_receiver + array[i]!!.code_curr_receiver
                                    val sender:String =  array[i].code_bnk_sender + num_acc_sender + array[i]!!.code_curr_sender
                                    var externAccount = " "
                                    var E_S = 0

                                    if(Injection.provideAccountRepository().getSelectedAccount().id == receiver){
                                        externAccount=sender
                                        E_S = 1 //entrant

                                    }else{
                                        externAccount = receiver
                                        E_S = 2 //sortant
                                    }

                                   listTransactions.add(Transaction(externAccount,array[i].montant_virement,array[i].created_at,array[i].type, array[i].status, array[i].montant_commission,E_S ))
                                }

                                mAdapter =  ListTransactionAdapter(this, listTransactions)
                                recyclerView!!.adapter = mAdapter
                                val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                                recyclerView!!.layoutManager =  linearLayoutManager

                                chargerScoll(recyclerView!!,linearLayoutManager)

                                Toast.makeText(this,"OK", Toast.LENGTH_LONG).show()
                                Log.e("history", response.body().toString())

                            }

                        },
                        { error ->

                            isLoading = false
                            Toast.makeText(this,error.message.toString(), Toast.LENGTH_LONG).show()
                            Log.e("out",error.message.toString() )
                        }
                )

    }



    fun loadNextPage()
    {

        if(currentPage == maxPages || isLoading)
            return
        currentPage++
        isLoading = true
        val id:Int = (Injection.provideAccountRepository().getSelectedAccount().id.subSequence(3,9)).toString().toInt()

        val disposable: Disposable = UserApiService.create().getHistory(Injection.provideUserRepository().accessInfos.token,id,currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->

                            isLoading = false
                            if (response.isSuccessful) {

                                val array = response.body()!!.data

                                listTransactions = ArrayList()

                                for (i in array.indices) {

                                    var num_acc_receiver:String = array[i].num_acc_receiver.toString()
                                    var num_acc_sender:String = array[i].num_acc_sender .toString()
                                    while (num_acc_receiver.length < 6 ){
                                        num_acc_receiver = "0"+num_acc_receiver
                                    }
                                    while (num_acc_sender.length < 6 ){
                                        num_acc_sender = "0"+num_acc_sender
                                    }

                                    val receiver:String =  array[i].code_bnk_receiver + num_acc_receiver + array[i]!!.code_curr_receiver
                                    val sender:String =  array[i].code_bnk_sender + num_acc_sender + array[i]!!.code_curr_sender
                                    var externAccount = " "
                                    var E_S = 0

                                    if(Injection.provideAccountRepository().getSelectedAccount().id == receiver){
                                        externAccount=sender
                                        E_S = 1 //entrant

                                    }else{
                                        externAccount = receiver
                                        E_S = 2 //sortant
                                    }

                                    listTransactions.add(Transaction(externAccount,array[i].montant_virement,array[i].created_at,array[i].type, array[i].status, array[i].montant_commission,E_S ))
                                }

                                //mAdapter =  ListTransactionAdapter(this, listTransactions)
                                //recyclerView!!.adapter = mAdapter
                                //recyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

                                mAdapter?.addListTransactions(listTransactions)

                                Toast.makeText(this,"Added", Toast.LENGTH_LONG).show()
                                Log.e("history", response.body().toString())

                            }

                        },
                        { error ->
                            isLoading = false

                            Toast.makeText(this,error.message.toString(), Toast.LENGTH_LONG).show()
                            Log.e("ClientActivityFailed",error.message.toString() )
                        }
                )

    }




    fun chargerScoll(recyclerView: RecyclerView, layoutManager: LinearLayoutManager):Boolean{
        var charger=false
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var pastVisiblesItems: Int = 0
            var visibleItemCount: Int = 0
            var totalItemCount: Int = 0

            override fun onScrolled(recyclerView: RecyclerView?,
                                    dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastVisiblesItems >= totalItemCount) charger=true

                if(charger)
                    loadNextPage()
            }
        })
        return  charger

    }


}
