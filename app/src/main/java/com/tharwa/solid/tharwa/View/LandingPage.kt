package com.tharwa.solid.tharwa.View


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tharwa.solid.tharwa.R

class LandingPage : AppCompatActivity() {


    val timeToWait = 4000L

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page_activity)
        Handler().postDelayed(Runnable {
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()

        },timeToWait)
    }
}
