package com.tharwa.solid.tharwa.Presenter

import android.widget.Toast
import com.tharwa.solid.tharwa.Contract.VirementInterneContract
import com.tharwa.solid.tharwa.Model.UserClass
import com.tharwa.solid.tharwa.Model.VirmentInterne
import com.tharwa.solid.tharwa.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by LE on 12/04/2018.
 */

class VirementInternePresenter(val view: VirementInterneContract.View)
{
    var disposable: Disposable? = null
    private val Service = Config.newService()
    private lateinit var virement: VirmentInterne



 fun InitData()
 {
     UserClass.token=""
     UserClass.num_acc_sender=1
     UserClass.type_acc_sender=0
     UserClass.code_curr_sender="DZD"

     var  num_acc_receiver:Int =3
     val type_acc_receiver:Int=1
     val code_curr_receiver:String ="DZD"
     val montant_virement:Int=20000
     val type:Int=0


     virement=VirmentInterne(UserClass.num_acc_sender,UserClass.type_acc_sender,UserClass.code_curr_sender,
             num_acc_receiver,type_acc_receiver,code_curr_receiver,montant_virement,type)
 }
fun transfer(virement: VirmentInterne)
{
  disposable = Service.virementInterne(UserClass.token,virement)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    {  mytransfer ->
                        if (mytransfer.isSuccessful) {

                           view.showMessage(mytransfer.body()?.message.toString())
                        } else //error 400-500
                        {
                            //mView?.showMessage(mView as Context,message )
                        }
                    },
                    {
                        error ->
                        view.showMessage(error.message.toString())
                    }
            )
}
    fun on_trasfClik()
    {
        transfer(virement)
    }

}
