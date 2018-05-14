package com.tharwa.solid.tharwa.View


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.tharwa.solid.tharwa.Contract.ExchageRateContract
import com.tharwa.solid.tharwa.FormInterface

import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Model.ExchangeRateItem
import com.tharwa.solid.tharwa.Model.User
import com.tharwa.solid.tharwa.Model.UserData
import com.tharwa.solid.tharwa.Presenter.ExchangeRatePresenter
import com.tharwa.solid.tharwa.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_exchange_rate.*


/**
 * A simple [Fragment] subclass.
 */
class ExchangeRateActivity : AppCompatActivity(), ExchageRateContract.View {

    private var list_ex_adapter: ExchangeRateAdapter? = null
    private var recyclerView: RecyclerView? = null
    var disposable: Disposable? = null

    var exchange_rate_items:ArrayList<ExchangeRateItem> = ArrayList()

    private val Service = Config.newService()



    val presenter: ExchangeRatePresenter by lazy { ExchangeRatePresenter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_rate)


        val toolbar: Toolbar = findViewById(R.id.echangeRateToolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        toolbar!!.setNavigationIcon(R.mipmap.ic_keyboard_backspace_white_36dp)
        toolbar!!.setNavigationOnClickListener {
            this.finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Taux de changes"
        recyclerView = findViewById(R.id.recycle_view_exchageRate)




        getExchangeRate(UserData.user!!.token)


    }


    fun getExchangeRate(token:String){

        showProgressDialog()
        disposable = Service.getExchangeRate(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { exchangeRateBody ->

                            if (exchangeRateBody.isSuccessful) {

                                val Euro:String = exchangeRateBody.body()?.Euro!!
                                val item1 = ExchangeRateItem(R.drawable.euro,"Euro", "Europe", Euro)

                                val USDollars:String = exchangeRateBody.body()?.USDollars!!
                                val item2 = ExchangeRateItem(R.drawable.usa,"USDollars", "USA", USDollars)

                                val TurkeyLira:String = exchangeRateBody.body()?.TurkeyLira!!
                                val item3 = ExchangeRateItem(R.drawable.turque,"TurkeyLira", "Turkey", TurkeyLira)

                                val CanadaDollars:String = exchangeRateBody.body()?.CanadaDollars!!
                                val item4 = ExchangeRateItem(R.drawable.canada,"CanadaDollars", "Canada", CanadaDollars)

                                val SaouditRyal:String = exchangeRateBody.body()?.SaouditRyal!!
                                val item5 = ExchangeRateItem(R.drawable.saudit,"SaouditRyal", "Saoudit", SaouditRyal)

                                val ChinaYuan:String = exchangeRateBody.body()?.ChinaYuan!!
                                val item6 = ExchangeRateItem(R.drawable.chine,"ChinaYuan", "China", ChinaYuan)

                                val MarocDinars:String = exchangeRateBody.body()?.MarocDinars!!
                                val item7 = ExchangeRateItem(R.drawable.maroc,"MarocDinars", "Maroc", MarocDinars)

                                val TunisiaDinars:String = exchangeRateBody.body()?.TunisiaDinars!!
                                val item8 = ExchangeRateItem(R.drawable.tunisie,"TunisiaDinars", "Tunisia", TunisiaDinars)

                                val PoundSterling = exchangeRateBody.body()?.PoundSterling!!
                                val item9 = ExchangeRateItem(R.drawable.esterling,"PoundSterling", "Europe", PoundSterling)

                                val EmaratDirham:String = exchangeRateBody.body()?.EmaratDirham!!
                                val item10 = ExchangeRateItem(R.drawable.emirat,"EmaratDirham", "Emarat", EmaratDirham)

                                exchange_rate_items.add(item1)
                                exchange_rate_items.add(item2)
                                exchange_rate_items.add(item3)
                                exchange_rate_items.add(item4)
                                exchange_rate_items.add(item5)
                                exchange_rate_items.add(item6)
                                exchange_rate_items.add(item7)
                                exchange_rate_items.add(item8)
                                exchange_rate_items.add(item9)
                                exchange_rate_items.add(item10)



                                list_ex_adapter= ExchangeRateAdapter(this,  exchange_rate_items)
                                recyclerView!!.adapter = list_ex_adapter
                                recyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

                                hideProgressDialog()

                            } else //error 400-500
                            {
                                if(exchangeRateBody.code() == 400){
                                    Toast.makeText(this,"400", Toast.LENGTH_LONG).show()
                                    Log.e("error", token +"not success") }
                                else{
                                    Toast.makeText(this,"500", Toast.LENGTH_LONG).show()
                                    Log.e("error", token +"not success") }
                            }
                            Log.e("rate", exchangeRateBody.body().toString())

                        },
                        { error ->
                            //other error
                            Toast.makeText(this,error.message.toString(), Toast.LENGTH_LONG).show()
                            Log.e("error", error.message.toString())
                            hideProgressDialog()
                        }
                )


    }



    fun showProgressDialog()
    {
        progressbar_exchange_rate.visibility = ProgressBar.VISIBLE

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    }

    fun hideProgressDialog()
    {
        progressbar_exchange_rate.visibility = ProgressBar.INVISIBLE

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    fun showDialogMessage(title:String,message:String)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setNeutralButton("Réessayer", DialogInterface.OnClickListener { _, _ ->

        })
        builder.create().show()
    }




}// Required empty public constructor
