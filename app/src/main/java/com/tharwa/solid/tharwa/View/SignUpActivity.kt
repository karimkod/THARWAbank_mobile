package com.tharwa.solid.tharwa.View

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.sign_up_activity.*


class SignUpActivity : AppCompatActivity(), TakePictureFragment.OnFragmentInteractionListener
{


    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)
        val toolbar = signupToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Inscription"


    }
}
