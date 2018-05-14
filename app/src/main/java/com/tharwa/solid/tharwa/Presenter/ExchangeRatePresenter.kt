package com.tharwa.solid.tharwa.Presenter

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.tharwa.solid.tharwa.Contract.SignUpContrat
import com.tharwa.solid.tharwa.Model.ExchangeRateItem
import com.tharwa.solid.tharwa.Model.UserCreate
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.View.ExchangeRateActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.tharwa.solid.tharwa.View.SignUpActivity
import com.tharwa.solid.tharwa.util.Config
import kotlinx.android.synthetic.main.code_introduction_activity.*
import java.io.File
/**
 * Created by mac on 15/04/2018.
 */
class ExchangeRatePresenter ( val view: ExchangeRateActivity){

    var disposable: Disposable? = null

    var exchange_rate_items:ArrayList<ExchangeRateItem> = ArrayList()

    private val Service = Config.newService()


    fun getExchangeRate(token:String){

        view.showProgressDialog()
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



                            } else //error 400-500
                            {
                                if(exchangeRateBody.code() == 400){
                                    Toast.makeText(this.view,"400", Toast.LENGTH_LONG).show()
                                    Log.e("error", token +"not success") }
                                else{
                                    Toast.makeText(this.view,"500", Toast.LENGTH_LONG).show()
                                    Log.e("error", token +"not success") }
                            }
                            Log.e("rate", exchangeRateBody.body().toString())
                            view.hideProgressDialog()
                        },
                        { error ->
                            //other error
                            Toast.makeText(this.view,error.message.toString(), Toast.LENGTH_LONG).show()
                            Log.e("error", error.message.toString())
                            view.hideProgressDialog()
                        }
                )




    }



}