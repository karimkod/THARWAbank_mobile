package com.tharwa.solid.tharwa.Presenter.Virement

import com.tharwa.solid.tharwa.Contract.VirementTharwaContract
import com.tharwa.solid.tharwa.Model.DestinationAccoutInfo
import com.tharwa.solid.tharwa.Model.VirementTharwa
import com.tharwa.solid.tharwa.Presenter.MissingPicture
import com.tharwa.solid.tharwa.Presenter.TakePicturePresenter
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.Repositories.UserRepository
import com.tharwa.solid.tharwa.enumration.CodeStatus

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class VirementTharwaPresenter(val mView:VirementTharwaContract.View, val userRepository: UserRepository)
{
    var takePicturePresenter: TakePicturePresenter? = null
    val MAXIMUM_SANS_MOTIF =  200000.0f


    var needMotif = false



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
                mView.hidePicturePlace()
                false
            }else
                needMotif
        }catch (e:NumberFormatException)
        {
            mView.hidePicturePlace()
            needMotif = false
        }
    }

    fun onFinishedClicked()
    {

        try {
            if (needMotif) takePicturePresenter!!.isImageValid()

            if (mView.isValidInputs())
            {
                mView.showProgressDialog()
                getDestinationInfos()

            }
        }catch(e: MissingPicture){}

    }


    fun onRequestFailed(error:Throwable)
    {

        mView.hideProgressDialog()
        mView.showDialogMessage("Oops..","Erreur, veuillez réessayer plus tard")
        //Log.e("SignUpPrensenter", error.message.toString())
    }


    fun onDestinationInfosResult(response: Response<DestinationAccoutInfo>)
    {
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

    }

    fun getDestinationInfos()
    {

         UserApiService.apply {
             sendRequest(
             create()
                     .getDestinationAccountInfo(
                             userRepository.accessInfos.token,
                             mView.destinationAccount.toInt()),
                     ::onDestinationInfosResult,
                     ::onRequestFailed)
         }

    }




    fun onVirementSuccess(response:Response<ResponseBody>)
    {
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

    }

    fun makeVirement() {
        if (!needMotif) {
            mView.showProgressDialog()


            UserApiService.apply{
                sendRequest(create().virementToTharwa(userRepository.accessInfos.token, VirementTharwa(mView.destinationAccount.toInt(), mView.montant.toFloat()))
            ,::onVirementSuccess,::onRequestFailed)}

        }else
        {

            val  callback = object : retrofit2.Callback<okhttp3.ResponseBody>{

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
            }

            makeVirementWithMotif(callback)
        }
    }


    fun makeVirementWithMotif(callback:retrofit2.Callback<okhttp3.ResponseBody>)
    {
        mView.showProgressDialog()
        val image = takePicturePresenter!!.getImage
        val reqFile = RequestBody.create(MediaType.parse("image/*"), image)
        val body = MultipartBody.Part.createFormData("justif", image.name, reqFile)

        val map = HashMap<String,RequestBody>()

        val dest = RequestBody.create(okhttp3.MultipartBody.FORM,mView.destinationAccount)
        val mont= RequestBody.create(okhttp3.MultipartBody.FORM,mView.montant)
        val type= RequestBody.create(okhttp3.MultipartBody.FORM,"0")

        val req = UserApiService.createServiceForImage().VirementToTharwa(userRepository.accessInfos.token,body,dest,mont,type)
        req.enqueue(callback)

    }



}

