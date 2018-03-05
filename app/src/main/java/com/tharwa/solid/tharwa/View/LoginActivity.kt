package com.tharwa.solid.tharwa.View

import android.app.FragmentManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        sign_up.setOnClickListener({
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })



    }

    override fun onStart() {
        super.onStart()
       var dialogFrag = CodeReceptionMethodDialog()
        dialogFrag.show(fragmentManager,"ReceptionCode")


    }
}
