package com.tharwa.solid.tharwa.Controller

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
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
import kotlinx.android.synthetic.main.login_activity.*

class CodeIntroductionActivity : AppCompatActivity()
    {

    var disposable: Disposable? = null
    private val Service by lazy {
        UserApiService.create()
    }

    val TAG = "CodeAuthentification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.code_introduction_activity)
        //Toast.makeText(this@CodeIntroductionActivity,intent.getStringExtra("mail"),Toast.LENGTH_LONG).show()
        valider.setOnClickListener({validerClicked()})
    }


    fun validerClicked()
    {
        val mail:String?=intent.getStringExtra("mail")
        val passwd:String?=intent.getStringExtra("password")
        val nonce = code.editText?.text.toString()
        if(!InputValidator.checkInput(code,this,InputType.CODE))
            return
        val usercode = UserCode(mail.toString(), passwd.toString(), nonce)
        loginCode(usercode)
    }


   private fun loginCode(usercd: UserCode):Unit {

       showProgressDialog()
        disposable = Service.loginCode(usercd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        { usercd ->
                            hideProgressDialog()
                            if (usercd.isSuccessful)
                            {
                                // get the token
                                //open the Acceuil activity
                                //Toast.makeText(this@CodeIntroductionActivity,usercd.message(),Toast.LENGTH_LONG).show()
                                Log.d(TAG,usercd.body()?.token)
                                val intent =Intent(applicationContext,Acceuil::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.putExtra("token",usercd.body()?.token)
                                startActivity(intent)

                            }
                            else
                            {
                                when(usercd.code())
                                {
                                    CodeStatus.err_400.status->
                                        //invalide code
                                        Toast.makeText(this@CodeIntroductionActivity,resources.getString(auth_code_400),Toast.LENGTH_LONG).show()
                                    CodeStatus.err_403.status->showDialogMessage("Le code est invalide", "Veuillez introduire le dernier code que vous avez reçu")
                                        //Toast.makeText(this@CodeIntroductionActivity,resources.getString(new_code),Toast.LENGTH_LONG).show()

                                }


                                //403 code expérer réssayer avec le nouveau code
                            }




                        },
                        { error->
                            hideProgressDialog()
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


        fun showDialogMessage(title:String,message:String)
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setNeutralButton("Réessayer", DialogInterface.OnClickListener { _, _ ->

            })
            builder.create().show()
        }


    }
