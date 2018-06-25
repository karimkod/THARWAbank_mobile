package com.tharwa.solid.tharwa.Services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.NotificationCompat
import android.util.Log

import com.google.firebase.messaging.RemoteMessage
import com.tharwa.solid.tharwa.Controller.LoginActivity
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.View.LandingPage

class FirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.i("message____","received")
        showNotification(remoteMessage!!.data["message"]!!)
    }


    private fun showNotification(message: String) {

        val i = Intent(this, LandingPage::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("FCM Test")
                .setContentText(message)
                .setSmallIcon(R.drawable.logo_withoutcircle)
                .setContentIntent(pendingIntent)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(0, builder.build())
    }


}