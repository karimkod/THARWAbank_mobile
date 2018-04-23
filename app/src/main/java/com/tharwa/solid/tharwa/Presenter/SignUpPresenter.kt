package com.tharwa.solid.tharwa.Presenter

import android.util.Log
import com.tharwa.solid.tharwa.Contract.SignUpContrat
import com.tharwa.solid.tharwa.Model.UserCreate
import com.tharwa.solid.tharwa.Remote.UserApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.tharwa.solid.tharwa.View.SignUpActivity
import com.tharwa.solid.tharwa.util.Config
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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




    fun createCustomer(usercr: UserCreate) {
        disposable = Service.createCustomer(usercr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usercr ->
                            mView.hideProgressDialog()
                            //val message=Error.codeMessage(usercr.code())
                            mView.showSuccessDialog()

                            if (usercr.isSuccessful) {
                            } else //error 400-500
                            {
                                //mView?.showMessage(mView as Context,message )
                            }




                        },
                        { error ->
                            mView.hideProgressDialog()
                            Log.e("SignUpPrensenter", error.message.toString())
                        }
                )
    }

    fun onSignUpClicked() {

        val activity = mView

        if (mView.isValidInputs()) {
            try {
                val image = picturePresenter!!.getImage
                val userCrt = UserCreate(activity.mail, activity.password, activity.phone_number,
                        "${activity.firstName} ${activity.lastName}", activity.adress,
                        activity.function, activity.wilaya, activity.commune, activity.type)
                createCustomer(userCrt)

                mView.showProgressDialog()
            } catch (e: MissingPicture) {

            }

        }


    }

    fun onSuccessDialogEnded() {
        mView.finish()
    }
//
//    fun sendImage()
//    {
//        Log.d("TakePictureBefo  re",image?.path)
//
//        val reqFile = RequestBody.create(MediaType.parse("image/*"), image!!)
//        val body = MultipartBody.Part.createFormData("photo", image!!.name, reqFile)
//        val userID = RequestBody.create(okhttp3.MultipartBody.FORM,"6")
//        val req = UserApiService.createServiceForImage().postImage(body,userID)
//        req.enqueue(object : retrofit2.Callback<okhttp3.ResponseBody>
//        {
//            override fun onResponse(call: retrofit2.Call<okhttp3.ResponseBody>, response: retrofit2.Response<ResponseBody>) {
//
//                Log.d("TakePicturePresenter", response.message())
//            }
//
//            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
//    }
}
