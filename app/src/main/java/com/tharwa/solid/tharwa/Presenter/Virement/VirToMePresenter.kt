package com.tharwa.solid.tharwa.Presenter.Virement

import com.tharwa.solid.tharwa.Contract.VirToMeContract

import com.tharwa.solid.tharwa.Model.UserData
import com.tharwa.solid.tharwa.Model.VirToMe
import com.tharwa.solid.tharwa.View.Virment.VirToMeFragment
import com.tharwa.solid.tharwa.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

import io.reactivex.schedulers.Schedulers



/**
 * Created by LE on 23/04/2018.
 */

class VirToMePresenter(val view:VirToMeContract.View):VirToMeFragment.InterfaceDataVirMe
{


    var disposable: Disposable? = null
    private val Service = Config.newService()



   private lateinit var virement: VirToMe
    private lateinit var token:String

    fun transfer(token:String,virement: VirToMe)
    {
        disposable = Service.virToMe(token ,virement)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            if (result.isSuccessful) {
                                view.showTag("ok succ","in transfert"+result.body()!!.message)
                                view.showResultDialog(  "Votre transaction est effectuer avec succssès")
                            } else //error 400-500
                            {
                               view.showResultDialog(""+result.errorBody().toString())
                               // view.showTag("errtag",result.errorBody().toString())
                            }
                        },
                        {error ->
                            view.showTag("ok err",error.message.toString())
                        }

                )
    }


    fun on_confirmClick(type_acc_sender:Int,type_acc_receiver:Int,montant_virement:Double)
    {
        initData(type_acc_sender,type_acc_receiver,montant_virement)
        transfer(token,virement)
    }
    fun onResultDialogEnded() {
        view.closeDialog()
    }


    fun initData(type_acc_sender:Int,type_acc_receiver:Int,montant_virement:Double)
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



    
    
    
    
    
