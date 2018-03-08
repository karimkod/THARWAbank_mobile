package com.tharwa.solid.tharwa.View


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.R.string.*
import com.tharwa.solid.tharwa.Remote.CheckConnection

class LandingPage : AppCompatActivity() {


    val timeToWait = 4000L

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page_activity)

        //Try to detect if the device is online
        //if the user the user is not online he must connect first
        if (!CheckConnection.isOnline(this))

            Toast.makeText(this@LandingPage,resources.getString(Verif_int),Toast.LENGTH_LONG).show()

        //else we could show the login activity
        else
        {
            Handler().postDelayed(Runnable {
                intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()

            },timeToWait)
        }
    }
}
