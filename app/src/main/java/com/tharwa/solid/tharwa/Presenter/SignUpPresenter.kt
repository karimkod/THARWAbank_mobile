package com.tharwa.solid.tharwa.Presenter

import android.util.Log
import com.tharwa.solid.tharwa.Contract.SignUpContrat
import com.tharwa.solid.tharwa.Model.UserCreate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.tharwa.solid.tharwa.View.SignUpActivity
import com.tharwa.solid.tharwa.util.Config
import java.io.File

/**
 * Created by LE on 12/03/2018.
 */
class SignUpPresenter (val mView:SignUpContrat.View){
    var disposable: Disposable? = null

    private val Service = Config.newService()
    private var user_id: Int? = null
    private val _method: String = "PUT"

    var picturePresenter: TakePicturePresenter? = null




    fun createCustomer(usercr: UserCreate, photo: File) {
        disposable = Service.createCustomer(usercr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usercr ->
                            mView?.hideProgressDialog()
                            //val message=Error.codeMessage(usercr.code())
                            mView?.showSuccessDialog()

                            if (usercr.isSuccessful) {
                            } else //error 400-500
                            {
                                //mView?.showMessage(mView as Context,message )
                            }
                        },
                        { error ->
                            //other error
                            mView?.hideProgressDialog()
                            //mView?.showError(mView as Context,error.message.toString())
                            Log.e("SignUpPrensenter", error.message.toString())
                        }
                )
    }

    fun onConnectClicked() {

        val activity = mView!!
        if (mView!!.isValidInputs()) {
            try {
                val userCrt = UserCreate(activity.mail, activity.password, activity.phone_number,
                        "   ${activity.firstName} ${activity.lastName}", activity.adress,
                        activity.function, activity.wilaya, activity.commune, activity.type)
                createCustomer(userCrt, picturePresenter!!.getImage as File)

                mView?.showProgressDialog()
            } catch (e: InexistantImage) {

            }

        }


    }

    fun onSuccessDialogEnded() {
        mView?.finish()
    }
}