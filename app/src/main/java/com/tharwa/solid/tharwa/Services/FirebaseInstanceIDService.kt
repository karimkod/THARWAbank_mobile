package com.tharwa.solid.tharwa.Services

import android.content.Context
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.tharwa.solid.tharwa.Model.RegisterFCMData
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Remote.UserApiService
import okhttp3.*
import java.io.IOException


class FirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {

        val token = FirebaseInstanceId.getInstance().token
        Log.i("token", token)
        this.run(token)
    }

    fun run(token: String?)
    {

        val sharedPref = this.getSharedPreferences(getString(com.tharwa.solid.tharwa.R.string.shared_pref_name), Context.MODE_PRIVATE)
        val authToken = sharedPref.getString(getString(R.string.token_key), "NaT") ?: return
        UserApiService.apply {
            sendRequest(create().registerFCM(authToken, RegisterFCMData(token!!)),::onSuccess,::onFaillure)
        }


    }


    fun onSuccess(response:retrofit2.Response<ResponseBody>)
    {
        if(response.isSuccessful)
            Log.i("successFCM","Phone registred")
    }
    fun onFaillure(t:Throwable) {
        Log.i("FaillureFCM","Phone fail registred")
    }
}