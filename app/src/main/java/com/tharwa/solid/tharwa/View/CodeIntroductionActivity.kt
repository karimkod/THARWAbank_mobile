package com.tharwa.solid.tharwa.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.tharwa.solid.tharwa.FormInterface
import com.tharwa.solid.tharwa.InvalideInputException
import com.tharwa.solid.tharwa.Model.UserCode
import com.tharwa.solid.tharwa.Model.UserData
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.enumration.CodeStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.code_introduction_activity.*
import com.tharwa.solid.tharwa.View.ClientAcountActivity
import com.tharwa.solid.tharwa.enumration.InputType

class CodeIntroductionActivity : AppCompatActivity(),FormInterface
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
        valider.setOnClickListener({validerClicked()})
    }

    //Sed the request once the user click on "valider"
    fun validerClicked()
    {
        val mail:String?=intent.getStringExtra("mail")
        val passwd:String?=intent.getStringExtra("password")
        val nonce = code.editText?.text.toString()
        try
        {
            verifyField(nonce,code,InputType.CODE,true,this)
            val usercode = UserCode(mail.toString(), passwd.toString(), nonce)
            loginCode(usercode)
            Log.e("errlogin", UserData.user.toString())
        }catch  (e:InvalideInputException)
        {

        }

    }

// this is the function that get the response once the user send the code
   private fun loginCode(usercd: UserCode):Unit {

       showProgressDialog()
        disposable = Service.loginCode(usercd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        { usercd ->
                            Log.e("errlogin",usercd.body().toString())
                            hideProgressDialog()
                            if (usercd.isSuccessful)
                            {
                                UserData.user = usercd.body()
                                val intent =Intent(applicationContext, ClientAcountActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else
                            {
                                // display messages acording to the recieved code
                                when(usercd.code())
                                {
                                    CodeStatus.err_401.status->
                                        showDialogMessage(this,"Code invalide", "Le code que vous avez saisi est invalide")
                                    CodeStatus.err_400.status->
                                        showDialogMessage(this,"Code invalide", "Le code doit être composé de 4 chiffre")
                                    else->
                                        showDialogMessage(this,"Oops", "Une erreur c'est produite, veuillez reéssayer plus tard")
                                }
                            }
                        },
                        { error->
                            hideProgressDialog()
                            Log.e("errlogin",error.message.toString())
                           Toast.makeText(this@CodeIntroductionActivity,error.message,Toast.LENGTH_LONG).show()
                        }
                )
    }


        fun showProgressDialog()
        {
            progressbar_code.visibility = ProgressBar.VISIBLE
            black_overlay_code.visibility = View.VISIBLE
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }

        fun hideProgressDialog()
        {
            progressbar_code.visibility = ProgressBar.INVISIBLE
            black_overlay_code.visibility = View.INVISIBLE
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }


    }
