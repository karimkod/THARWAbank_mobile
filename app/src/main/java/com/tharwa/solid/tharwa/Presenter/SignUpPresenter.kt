package com.tharwa.solid.tharwa.Presenter

import android.util.Log
import com.tharwa.solid.tharwa.Contract.SignUpContrat
import com.tharwa.solid.tharwa.Model.CreateResponse
import com.tharwa.solid.tharwa.Model.UserCreate
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.View.CodeReceptionMethodDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.tharwa.solid.tharwa.View.SignUpActivity
import com.tharwa.solid.tharwa.enumration.CodeStatus
import com.tharwa.solid.tharwa.util.Config
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

/**
 * Created by LE on 12/03/2018.
 */
class SignUpPresenter (val mView:SignUpContrat.View){

    private val Service = Config.newService()

    var picturePresenter: TakePicturePresenter? = null


    fun onCreateCustomerSuccess(response: Response<CreateResponse>) {


        if (response.isSuccessful)
        {
            //mView.showSuccessDialog()
            sendImage(picturePresenter!!.getImage,response.body()?.user_id)

        } else //error 400-500
        {
            mView.hideProgressDialog()
            val message:String
            val title:String
            when(response.code())
            {
                CodeStatus.err_400.status->{title = "Champs invalide";message="Un des champs que vous avez saisi est invalide."}
                CodeStatus.err_422.status->{title = "Compte existe";message="Ces données sont déja associées à un compte."}
                else->{title = "Oops";message="Erreur inattendue, veuillez réessayer plus tard."}

            }
            mView.showDialogMessage(title,message)
        }

    }


    fun onCreateCustomerFail(error:Throwable) {
        mView.hideProgressDialog()
        Log.e("SignUpPrensenter", error.message.toString())
    }

    fun createCustomer(usercr: UserCreate)
    {
        UserApiService.apply { sendRequest(create().createCustomer(usercr),::onCreateCustomerSuccess,::onCreateCustomerFail) }
    }

    fun onSignUpClicked() {

        val activity = mView

        try {
            picturePresenter?.isImageValid()
            if (mView.isValidInputs())
            {
                val userCrt = UserCreate(activity.mail, activity.password, activity.phone_number,
                        "${activity.firstName} ${activity.lastName}", activity.adress,
                        activity.function, activity.wilaya, activity.commune, activity.type)
                createCustomer(userCrt)
                mView.showProgressDialog()


            }
        }catch(e: MissingPicture){}


    }

    fun onSuccessDialogEnded() {
        mView.finish()
    }

    fun sendImage(image:File,userID:Int?)
    {
        //Log.d("TakePictureBefre",image?.path)

        val reqFile = RequestBody.create(MediaType.parse("image/*"), image)
        val body = MultipartBody.Part.createFormData("photo", image.name, reqFile)
        val userID = RequestBody.create(okhttp3.MultipartBody.FORM,userID.toString())
        val req = UserApiService.createServiceForImage().postImage(body,userID)
        req.enqueue(object : retrofit2.Callback<okhttp3.ResponseBody>
        {
            override fun onResponse(call: retrofit2.Call<okhttp3.ResponseBody>, response: retrofit2.Response<ResponseBody>)
            {
                mView.hideProgressDialog()
                if(response.isSuccessful)
                {
                    mView.showSuccessDialog()
                }else
                    Log.d("SignUpPresenter","Image invalide")
            }

            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
