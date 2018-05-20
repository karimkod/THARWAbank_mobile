package com.tharwa.solid.tharwa.Presenter.Virement

import android.util.Log
import com.tharwa.solid.tharwa.Contract.VirToMeContract
import com.tharwa.solid.tharwa.Model.ResponseVirme
import com.tharwa.solid.tharwa.Model.VirToMe
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.Repositories.Injection
import com.tharwa.solid.tharwa.View.Virment.VirToMeFragment
import com.tharwa.solid.tharwa.util.Config
import io.reactivex.disposables.Disposable
import retrofit2.Response
class VirToMePresenter(val view:VirToMeContract.View):VirToMeFragment.InterfaceDataVirMe
{


    var disposable: Disposable? = null
    private val Service = Config.newService()

   private lateinit var virement: VirToMe
    private lateinit var token:String


      fun transfer( token:String,virement: VirToMe)
    {
        view.showProgressDialog()
        UserApiService.apply { sendRequest(create().virToMe(token ,virement),::onCreateVirementSuccess,::onCreateVirementFail) }
    }

    fun onCreateVirementSuccess(result: Response<ResponseVirme>)
    {
        view.hideProgressDialog()
        if (result.isSuccessful) {
            view.showResultDialog(result.code(),result.body()!!.balance)
        } else //error 400-500
        {
            view.showResultDialog(result.code(),result.message())
        }

    }
    fun onCreateVirementFail(error:Throwable) {
        view.hideProgressDialog()
        Log.e("VirToMePresenter", error.message.toString())
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
        token=Injection.provideUserRepository().accessInfos.token
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
       // var countTypr=UserData.user!!.accountTypes
        var countTypr=Injection.provideAccountRepository().availableAccountsType
        for (i in countTypr)
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
}



    
    
    
    
    
