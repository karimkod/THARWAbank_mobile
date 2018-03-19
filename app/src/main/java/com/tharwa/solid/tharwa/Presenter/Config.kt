package com.tharwa.solid.tharwa.Presenter
import com.tharwa.solid.tharwa.Remote.UserApiService

/**
 * Created by LE on 15/03/2018.
 */
class Config
{
    companion object {
        fun newService():UserApiService
        {
            val Service :UserApiService by lazy {
                UserApiService.create()
            }
            return Service
        }
    }
}