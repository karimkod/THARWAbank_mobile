package com.tharwa.solid.tharwa.Remote
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by LE on 06/03/2018.
 */

//To test if the dvice is online or not
class CheckConnection
{
     companion object {
        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            return  activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }
}