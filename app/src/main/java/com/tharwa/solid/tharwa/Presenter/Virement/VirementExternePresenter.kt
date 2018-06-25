package com.tharwa.solid.tharwa.Presenter.Virement

import android.util.Log
import com.tharwa.solid.tharwa.Contract.VirementExterneContract
import com.tharwa.solid.tharwa.Model.BanksID
import com.tharwa.solid.tharwa.Model.VirementExtern
import com.tharwa.solid.tharwa.Presenter.MissingPicture
import com.tharwa.solid.tharwa.Presenter.TakePicturePresenter
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.Repositories.UserRepository
import com.tharwa.solid.tharwa.enumration.CodeStatus
import com.tharwa.solid.tharwa.util.VirementConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.math.log

class VirementExternePresenter(val mView:VirementExterneContract.View, val userRepository: UserRepository)
{
    var takePicturePresenter: TakePicturePresenter? = null

    var needMotif = false


    fun start()
    {
        getBanks()
    }

    fun getBanks()
    {
        UserApiService.apply {
            sendRequest(create().getBanks(userRepository.accessInfos.token),::onGetBanksSuccess,::onRequestFailed)
        }
    }

    fun onGetBanksSuccess(response:Response<ArrayList<BanksID>>)
    {
        if(response.isSuccessful)
        {
            var listOfBanksID = ArrayList<String>()
            for (i in response.body()!!)
            {
                listOfBanksID.add(i.bankId)
            }

            mView.loadBankSpinner(listOfBanksID)
        }else
        {
            mView.showDialogMessage("Erreur","Erreur survenu pendant le chargement des banques")
        }
    }

    fun onRequestFailed(error:Throwable)
    {

        mView.hideProgressDialog()
        mView.showDialogMessage("Oops..","Erreur, veuillez réessayer plus tard")
    }

    fun montantVerification(montant: String)
    {
        try {
            val montantFloat = montant.toFloat()
            needMotif = if (montantFloat > VirementConfig.MAXIMUM_SANS_MOTIF && !needMotif)
            {
                mView.showPicturePlace()
                true
            }else if (montantFloat <= VirementConfig.MAXIMUM_SANS_MOTIF && needMotif)
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

            if (mView.isValidInputs())
            {
                if (needMotif) takePicturePresenter!!.getImage
                mView.showConfirmationMethod()

            }
        }catch(e: MissingPicture){}

    }

    fun onVirementSuccess(response:Response<ResponseBody>)
    {
        mView.hideProgressDialog()
        if (response.isSuccessful) {

            mView.showSuccessDialog("Virement effectué","Le virement est envoyé, il sera traité dans moins de 24 heures")


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
                    title = "Motif du virement manquant";message = "Vous avez dépassé le montant permis sans justification, veuillez attaché un justificatif"
                }
                else -> {
                    title = "Oops ${response.code()}";message = "Erreur inattendue, veuillez réessayer plus tard."
                    Log.d("Erreur Serveur", response.message())
                    Log.d("Erreur Serveur",response.errorBody().toString())
                    Log.d("Erreur Serveur",response.body().toString())
                    Log.d("Erreur Serveur",response.toString())
                    Log.d("Erreur Serveur",response.raw().toString())
                    Log.d("Erreur Serveur",response.raw().body().toString())


                }

            }
            mView.showDialogMessage(title, message)
        }

    }


    fun makeVirement() {
        if (!needMotif) {
            mView.showProgressDialog()


            UserApiService.apply{
                sendRequest(create().virementExtern(userRepository.accessInfos.token, VirementExtern(mView.numAccountReceiver.toInt()
                        , mView.bankReceiver,mView.codeCurrencyReceiver,mView.nameReceiver,
                        mView.montant.toFloat()))
                        ,::onVirementSuccess,::onRequestFailed)}

        }else
        {

            val  callback = object : retrofit2.Callback<okhttp3.ResponseBody>{

                override fun onResponse(call: retrofit2.Call<okhttp3.ResponseBody>, response: retrofit2.Response<ResponseBody>)
                {

                    onVirementSuccess(response)
                }

                override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                    onRequestFailed(t)
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



        val numAccount = RequestBody.create(okhttp3.MultipartBody.FORM,mView.numAccountReceiver)
        val codeBank= RequestBody.create(okhttp3.MultipartBody.FORM,mView.bankReceiver)
        val codeCurrency= RequestBody.create(okhttp3.MultipartBody.FORM,mView.codeCurrencyReceiver)
        val name= RequestBody.create(okhttp3.MultipartBody.FORM,mView.nameReceiver)
        val amount= RequestBody.create(okhttp3.MultipartBody.FORM,mView.montant)

        val req = UserApiService.createServiceForImage().virementExternWithMotif(
                userRepository.accessInfos.token,
                body,numAccount,codeBank,codeCurrency,name,amount)
        req.enqueue(callback)

    }


}