package com.tharwa.solid.tharwa.View
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.TextInputLayout
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tharwa.solid.tharwa.Base.BaseActivity
import com.tharwa.solid.tharwa.Model.UserCreate
import com.tharwa.solid.tharwa.Presenter.SignUp
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.sign_up_activity.*
import java.io.File


class SignUpActivity : BaseActivity<SignUp>(),TakePictureFragment.OnFragmentInteractionListener
{
    var Email:String?=null
    var password:String?=null
    var phone_numbe:String?=null
    var LastName:String?=null
    var FirstName:String?=null
    var adress:String?=null
    var function:String?=null
    var wilaya:String?=null
    var Town:String?=null
    var type:Int?=null
    var photo: File?=null

    override fun onFragmentInteraction(uri: Uri) {

    }
    @NonNull
    override fun createPresenter(@NonNull context:Context):SignUp
    {
            return SignUp(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)
        val toolbar = signupToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Inscription"


        sign_up.setOnClickListener( {onConnectClicked()})



    }
    //Set the information of the user
   fun onConnectClicked()
    {

        //To get the data from inputs
        Email="dodo@example.com"
        password="password"
        phone_numbe="+213557894579"
        FirstName="JoJo"
        LastName="Ben 9a9a"

        adress="5anzomia 9armaza"
        function="Youtuber"
        wilaya="Tissemsilt"
        Town="Vialar"
        type=0

        photo=File("")
        // Create an instance of user
        val userCrt:UserCreate=UserCreate(Email as String,password as String,phone_numbe as String,
                "$FirstName  $LastName" ,adress as String,
                function as String,wilaya as String,Town as String,type as Int)

        mPresenter?.createCustomer(userCrt,photo as File)
    }
}
