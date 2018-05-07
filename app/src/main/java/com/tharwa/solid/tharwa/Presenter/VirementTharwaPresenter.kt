package com.tharwa.solid.tharwa.Presenter

import android.util.Log
import com.tharwa.solid.tharwa.Contract.VirementTharwaContract
import com.tharwa.solid.tharwa.Model.UserData
import com.tharwa.solid.tharwa.Model.VirementTharwa
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.enumration.CodeStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

class VirementTharwaPresenter(val mView:VirementTharwaContract.View)
{
    var takePicturePresenter:TakePicturePresenter? = null
    val MAXIMUM_SANS_MOTIF =  200000.0f

    private var needMotif = false

    fun montantVerification(montant:String)
    {
        try {
            val montantFloat = montant.toFloat()
            needMotif = if (montantFloat > MAXIMUM_SANS_MOTIF && !needMotif)
            {
                mView.showPicturePlace()
                true
            }else if (montantFloat <= MAXIMUM_SANS_MOTIF && needMotif)
            {
                mView.hidePicturePlase()
                false
            }else
                needMotif
        }catch (e:NumberFormatException)
        {
            mView.hidePicturePlase()
            needMotif = false
        }
    }

    fun onFinishedClicked()
    {

        try {
            val image = if (needMotif) takePicturePresenter!!.getImage else null

            if (mView.isValidInputs())
            {
                mView.showProgressDialog()

                val disposable = UserApiService.create().getDestinationAccountInfo(UserData.user!!.token, mView.destinationAccount.toInt())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { response ->

                                    mView.hideProgressDialog()
                                    if (response.isSuccessful)
                                    {
                                        mView.showConfirmationMethod(response.body()!!.name, response.body()!!.wilaya, response.body()!!.commune)

                                    } else //error 400-500
                                    {
                                        val message:String
                                        val title:String
                                        when(response.code())
                                        {
                                            CodeStatus.err_404.status->{title = "Compte Inexistant";message="Le numéro de compte que vous avez saisi n\'appartient à personne"}
                                            else->{title = "Oops ${response.code()}";message="Erreur inattendue, veuillez réessayer plus tard."}

                                        }
                                        mView.showDialogMessage(title,message)
                                    }

                                },
                                { error ->
                                    mView.hideProgressDialog()
                                    mView.showDialogMessage("Oops..","Erreur, veuillez réessayer plus tard")
                                    Log.e("SignUpPrensenter", error.message.toString())
                                }
                        )


            }
        }catch(e: MissingPicture){}

    }

    fun makeVirement() {
        if (!needMotif) {
            mView.showProgressDialog()

            val disposable = UserApiService.create().virementToTharwa(UserData.user!!.token, VirementTharwa(mView.destinationAccount.toInt(), mView.montant.toFloat()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { response ->

                                mView.hideProgressDialog()
                                if (response.isSuccessful) {

                                    mView.showSuccessDialog("Virement effectué","Le virement a été effectué avec success")


                                } else //error 400-500
                                {
                                    val message: String
                                    val title: String
                                    when (response.code()) {
                                        CodeStatus.err_400.status -> {
                                            title = "Champs invalide";message = "Un des champs que vous avez saisi est invalide."
                                        }
                                        CodeStatus.err_422.status -> {
                                            title = "Montant insuffisant";message = "Votre montant est insuffisant."
                                        }
                                        CodeStatus.err_403.status -> {
                                            title = "Motif du virement manquant";message = "Vous avez dépassé permet sans justification, veuillez attaché un justificatif"
                                        }
                                        else -> {
                                            title = "Oops ${response.code()}";message = "Erreur inattendue, veuillez réessayer plus tard."
                                        }

                                    }
                                    mView.showDialogMessage(title, message)
                                }

                            },
                            { error ->
                                mView.hideProgressDialog()
                                mView.showDialogMessage("Oops..", "Erreur, veuillez réessayer plus tard")
                                Log.e("SignUpPrensenter", error.message.toString())
                            }
                    )
        }else
        {
            mView.showProgressDialog()
            val image = takePicturePresenter!!.getImage
            val reqFile = RequestBody.create(MediaType.parse("image/*"), image)
            val body = MultipartBody.Part.createFormData("justif", image.name, reqFile)

            val map = HashMap<String,RequestBody>()

            val dest = RequestBody.create(okhttp3.MultipartBody.FORM,mView.destinationAccount)
            val mont= RequestBody.create(okhttp3.MultipartBody.FORM,mView.montant)
            val type= RequestBody.create(okhttp3.MultipartBody.FORM,"0")

            val req = UserApiService.createServiceForImage().VirementToTharwa(UserData.user!!.token,body,dest,mont,type)

            req.enqueue(object : retrofit2.Callback<okhttp3.ResponseBody>
            {
                override fun onResponse(call: retrofit2.Call<okhttp3.ResponseBody>, response: retrofit2.Response<ResponseBody>)
                {
                    mView.hideProgressDialog()
                    if(response.isSuccessful)
                    {
                        mView.showSuccessDialog("Virement remis à validation","Le virement est remis à validation cela prendra moins de 24 heures")
                    }else
                    {
                        val message: String
                        val title: String
                        when (response.code()) {
                            CodeStatus.err_400.status -> {
                                title = "Champs invalide";message = "Un des champs que vous avez saisi est invalide."
                            }
                            CodeStatus.err_422.status -> {
                                title = "Montant insuffisant";message = "Votre montant est insuffisant."
                            }
                            CodeStatus.err_403.status -> {
                                title = "Motif du virement manquant";message = "Vous avez dépassé permet sans justification, veuillez attaché un justificatif"
                            }
                            else -> {
                                title = "Oops ${response.code()}";message = "Erreur inattendue, veuillez réessayer plus tard."
                            }

                        }
                        mView.showDialogMessage(title, message)
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                    mView.hideProgressDialog()
                    mView.showDialogMessage("Oops..", "Erreur, veuillez réessayer plus tard")
                }
            })
        }
    }



}

