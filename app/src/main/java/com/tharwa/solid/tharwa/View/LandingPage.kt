package com.tharwa.solid.tharwa.View


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import com.tharwa.solid.tharwa.Controller.LoginActivity
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.R.string.*
import com.tharwa.solid.tharwa.Remote.CheckConnection
import kotlinx.android.synthetic.main.landing_page_activity.*

class LandingPage : AppCompatActivity() {


    val timeToWait = 2000L

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page_activity)

        connectionChecking()
        //Try to detect if the device is online
        //if the user the user is not online he must connect first

    }

    fun connectionChecking()
    {
        if (!CheckConnection.isOnline(this)) {

            Snackbar.make(parentView, resources.getString(Verif_int), Snackbar.LENGTH_INDEFINITE).setAction("Réessayer", {
                connectionChecking()
            }).show()
        }
        //else we could show the login activity
        else
        {
            Handler().postDelayed( {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            },timeToWait)
        }
    }






}
