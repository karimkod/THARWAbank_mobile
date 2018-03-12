package com.tharwa.solid.tharwa.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.tharwa.solid.tharwa.Model.UserCode
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.enumration.CodeStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.code_introduction_activity.*
import com.tharwa.solid.tharwa.R.string.*
import com.tharwa.solid.tharwa.enumration.InputType

class CodeIntroductionActivity : AppCompatActivity()
    {
    //used to hold the service
    var disposable: Disposable? = null
     //lazy to garantee that it gonna not be used until we need it
    private val Service by lazy {
        UserApiService.create()
    }

    var token:String?=null
    val TAG = "CodeAuthentification" //fix the TAG for Lofcat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.code_introduction_activity)
        Toast.makeText(this@CodeIntroductionActivity,intent.getStringExtra("mail"),Toast.LENGTH_LONG).show()
        valider.setOnClickListener({validerClicked()})
    }

    //Sed the request once the user click on "valider"
    fun validerClicked()
    {
        val mail:String?=intent.getStringExtra("mail")
        val passwd:String?=intent.getStringExtra("password")
        val nonce = code.editText?.text.toString()
        if(!InputValidator.checkInput(code,type = InputType.CODE))
            return
        val usercode = UserCode(mail.toString(), passwd.toString(), nonce)
        loginCode(usercode)
    }

// this is the function that get the response once the user send the code
   private fun loginCode(usercd: UserCode):Unit {

        disposable = Service.loginCode(usercd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        { usercd ->
                            if (usercd.isSuccessful) //checked if the response id succeed
                            {
                                Log.d(TAG,usercd.body()?.token) //check if the token is well recived
                                this.token=usercd.body()?.token
                            }
                            else
                            {
                                // display messages acording to the recieved code
                                when(usercd.code())
                                {
                                    CodeStatus.err_400.status->
                                        //invalide code
                                        Toast.makeText(this@CodeIntroductionActivity,resources.getString(auth_code_400),Toast.LENGTH_LONG).show()
                                    CodeStatus.err_403.status->
                                        Toast.makeText(this@CodeIntroductionActivity,resources.getString(new_code),Toast.LENGTH_LONG).show()
                                }


                                //403 code expérer réssayer avec le nouveau code
                            }


                        },
                        { error->
                            //show the error
                            Toast.makeText(this@CodeIntroductionActivity,error.message,Toast.LENGTH_LONG).show()
                        }
                )
    }
}
