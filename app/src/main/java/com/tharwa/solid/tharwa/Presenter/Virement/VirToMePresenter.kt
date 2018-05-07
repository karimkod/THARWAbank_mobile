package com.tharwa.solid.tharwa.Presenter.Virement

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import com.tharwa.solid.tharwa.Contract.VirToMeContract
import com.tharwa.solid.tharwa.Model.Response
import com.tharwa.solid.tharwa.Model.UserData
import com.tharwa.solid.tharwa.Model.VirToMe
import com.tharwa.solid.tharwa.View.ClientAcountActivity
import com.tharwa.solid.tharwa.View.Virment.VirToMeFragment
import com.tharwa.solid.tharwa.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.util.HalfSerializer.onComplete
import io.reactivex.internal.util.HalfSerializer.onNext
import io.reactivex.plugins.RxJavaPlugins.onSubscribe
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import java.util.Observer


/**
 * Created by LE on 23/04/2018.
 */

class VirToMePresenter(val view:VirToMeContract.View):VirToMeFragment.InterfaceDataVirMe
{

    /**
    Virer vers un de ses comptes:

    [POST] http://tharwa.localhost/virements_internes

    Header: {"Accept":"application/json"
    "Authorization":"Bearer The_access_token_from_the_login"}}
    Body:   {
    "type_acc_sender" : 0,
    "type_acc_receiver":1,
    "montant_virement":20000,
    "type":0
    }
     * * */


    var disposable: Disposable? = null
    private val Service = Config.newService()



   private lateinit var virement: VirToMe
    private lateinit var token:String

    fun transfer(token:String,virement: VirToMe)
    {
        view.showTag("errtag","in transfert"+virement.toString())
        view.closeDialog()
        disposable = Service.virToMe(token ,virement)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            if (result.isSuccessful) {
                                view.showTag("ok succ","in transfert"+result.body()!!.message)
                                view.showMessage(result.body()!!.message)
                                //close the dialoge
                                 view.closeDialog()
                                //opent the activity


                            } else //error 400-500
                            {
                               view.showMessage(result.body()!!.message)
                                view.showTag("ok err","in transfert"+result.body()!!.message)
                            }
                        },
                        {error ->
                            view.showMessage(error.message.toString()) }
                )
    }


    fun on_confirmClick(type_acc_sender:Int,type_acc_receiver:Int,montant_virement:Int)
    {
        //view.showTag("errtag",montant_virement.toString())
        initData(type_acc_sender,type_acc_receiver,montant_virement)
      // view.showTag("errtag","before"+token)
       // view.showTag("errtag","before"+virement)
        transfer(token,virement)
      //  view.showTag("errtag","after"+virement)
    }


    fun initData(type_acc_sender:Int,type_acc_receiver:Int,montant_virement:Int)
    {
        virement=VirToMe(type_acc_sender,type_acc_receiver,montant_virement,0)
        token=UserData.user!!.token
    }




    override fun CountVirMe(item: String): ArrayList<String> {
        val listDisp=listDisp()
        var list =ArrayList<String>()
        when(item)
        {
            "COURANT"-> {
               if (listDisp.contains("EPARGNE")) list.add("EPARGNE")
                if (listDisp.contains("EURO")) list.add("EURO")
                if (listDisp.contains("USD")) list.add("USD")
            }
            "EPARGNE","USD","EURO"-> list.add("COURANT")
            else -> {
                if (listDisp.contains("COURANT")) list.add("COURANT")
                if (listDisp.contains("EPARGNE")) list.add("EPARGNE")
                if (listDisp.contains("EURO")) list.add("EURO")
                if (listDisp.contains("USD")) list.add("USD")
            }
        }
        return list
    }

    fun listDisp():ArrayList<String>
    {
        var  list=ArrayList<String>()
        var listUser=UserData.user!!.accountTypes
        for (i in listUser)
        {
            when(i)
            {
                1-> list.add("COURANT")
                2-> list.add("EPARGNE")
                3-> list.add("EURO")
                4-> list.add("USD")
            }
        }
        return list
    }


    override fun dataVirMe(item:String):ArrayList<String>
    {
        var  list=ArrayList<String>()
        when(item)
        {
            "COURANT"-> {
                list.add("EPARGNE")
                list.add("EURO")
                list.add("USD")
            }
            "EPARGNE","USD","EURO"-> list.add("COURANT")
            else -> {
                list.add("COURANT")
                list.add("EPARGNE")
                list.add("EURO")
                list.add("USD")

            }
        }

        return list
    }

}



    
    
    
    
    
