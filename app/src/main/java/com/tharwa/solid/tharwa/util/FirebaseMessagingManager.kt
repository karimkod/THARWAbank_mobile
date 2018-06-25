package com.tharwa.solid.tharwa.util

import android.util.Log
import com.tharwa.solid.tharwa.Model.RegisterFCMData
import com.tharwa.solid.tharwa.Remote.UserApiService
import okhttp3.ResponseBody

object FirebaseMessagingManager
{

    fun registerFirebaseMessaging(authToken:String, token: String?)
    {

        UserApiService.apply {
            sendRequest(create().registerFCM(authToken, RegisterFCMData(token!!)),::onSuccess,::onFaillure)
        }


    }


    fun onSuccess(response:retrofit2.Response<ResponseBody>)
    {
        if(response.isSuccessful)
            Log.d("FirebaseMessaging","Success")
        else
            Log.d("FirebaseMessaging","Failed from server")    }
    fun onFaillure(t:Throwable) {
        Log.d("FirebaseMessaging","Failed")
    }

}