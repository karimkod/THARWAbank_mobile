package com.tharwa.solid.tharwa.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.activity_account_waiting.*

class AccountWaitingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_waiting)

        finish.setOnClickListener { finish() }
    }
}
