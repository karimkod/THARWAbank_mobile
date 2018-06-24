package com.tharwa.solid.tharwa.Controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import com.tharwa.solid.tharwa.FormInterface
import com.tharwa.solid.tharwa.InvalideInputException
import com.tharwa.solid.tharwa.Model.AccesInfo
import com.tharwa.solid.tharwa.Model.UserCode
import com.tharwa.solid.tharwa.Model.UserInfo
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.Repositories.Injection
import com.tharwa.solid.tharwa.View.ClientAcountActivity
import com.tharwa.solid.tharwa.enumration.CodeStatus
import com.tharwa.solid.tharwa.enumration.InputType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.code_introduction_activity.*

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


                        { response ->
                            hideProgressDialog()
                            if (response.isSuccessful)
                            {
                                // get the token
                                //open the Acceuil activity
                                //Toast.makeText(this@CodeIntroductionActivity,usercd.message(),Toast.LENGTH_LONG).show()
                                Log.d(TAG,response.body()?.toString())
                                val intent =Intent(applicationContext, ClientAcountActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                val body = response.body()!!

                                Injection.apply {
                                    provideUserRepository().apply {
                                        accessInfos = AccesInfo(body.userId, body.token!!, body.expiresIn!!, body.type)
                                        userInfo = UserInfo(body.name, body.photoPath)
                                    }
                                    provideAccountRepository().apply {
                                        cachedAccounts[1] = body.currentAccount
                                        availableAccountsType = body.accountTypes
                                    }
                                }


                                val sharedPref = this.getSharedPreferences(getString(com.tharwa.solid.tharwa.R.string.shared_pref_name),Context.MODE_PRIVATE)
                                with (sharedPref.edit()) {
                                    putString(getString(R.string.token_key), response.body()?.token)
                                    putString(getString(R.string.refresh_token_key), response.body()?.refreshToken)
                                    commit()
                                }

                                startActivity(intent)
                            }
                            else
                            {
                                // display messages acording to the recieved code
                                when(response.code())
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

                            showDialogMessage(this,"Oops", error.message.toString())//"Une erreur c'est produite, veuillez reéssayer plus tard")
                            //Toast.makeText(this@CodeIntroductionActivity,"Hello someone",Toast.LENGTH_LONG).show()

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
