package com.tharwa.solid.tharwa.Controller

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import com.tharwa.solid.tharwa.FormInterface
import com.tharwa.solid.tharwa.InvalideInputException
import com.tharwa.solid.tharwa.Model.User
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.R.string.*
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.View.CodeReceptionMethodDialog
import com.tharwa.solid.tharwa.View.SignUpActivity
import com.tharwa.solid.tharwa.enumration.CodeStatus
import com.tharwa.solid.tharwa.enumration.InputType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity(), CodeReceptionMethodDialog.DialogChoiceInteraction,FormInterface {
    var mail: String? = null
    var passwd: String? = null
    val TAG = "LoginActivity"

    var user :User?=null
    var choice:Int?=null
    var code:Int?=null

    //the methode which get the choice from the dialog
    override fun onTermineClicked(choice: Int)
    {
        this.choice=choice
        user= User(mail.toString(),passwd.toString(),choice)
        login(user as User)
    }
    //Disposable will hold the response after
    var disposable: Disposable? = null
    //Garantee it gonna not be created untill needed
    private val Service by lazy {
        UserApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        val clickListenr = { it: View ->
            (it as TextInputLayout).error = null
        }



        sign_up.setOnClickListener({
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })
        email.setOnClickListener(clickListenr)
        motdepasse.setOnClickListener(clickListenr)

        email.editText?.setOnClickListener({ email.callOnClick() })
        motdepasse.editText?.setOnClickListener({ motdepasse.callOnClick() })



        login.setOnClickListener({ onConnectClicked() })

    }

    //function that recieve the response of send mail, password,choice to get the code
     fun login(user: User) {

        showProgressDialog()
        disposable = Service.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            hideProgressDialog()
                            if (user.isSuccessful) {

                                this.code=user.code()


                                if (user.code().equals(CodeStatus.succ200.status)) {

                                    val intent = Intent(this@LoginActivity, CodeIntroductionActivity::class.java)
                                    intent.putExtra("mail", mail)
                                    intent.putExtra("password", passwd)
                                    startActivity(intent)

                                }
                            } else {

                                this.code=user.code()

                                var title: String
                                var message: String
                                when (user.code()) {
                                //in this case karim with invalide input cahnge the color of the input
                                    CodeStatus.err_400.status -> {
                                        message = "Format des entrès invalide"
                                        title = "Oops..."
                                    }
                                    CodeStatus.err_401.status ->
                                    {
                                        title = "Compte introuvable"
                                        message = "Veuillez vérifier vos données. \n" +
                                                "Si vous êtes nouveau sur Tharwa créez un nouveaux compte"
                                    }
                                    CodeStatus.err_500.status -> {
                                        message = resources.getString(err_500)
                                        title = "Oops..."
                                    }
                                    else -> {
                                        message = "Oops"
                                        title = "Erreur inattendue"
                                    }

                                }
                                showDialogMessage(title,message)
                            }


                        },
                        { error ->
                            Log.e("error", error.message)
                            hideProgressDialog()
                            // Display the error as it is cause it's related to system not reponse
                            //Toast.makeText(this@LoginActivity,error.message,Toast.LENGTH_LONG).show()
                            showDialogMessage("Oops",error.message.toString())
                        }
                )
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    //To show the dialog to choose between SMS & Mail
    fun showChoiceDialog() {
        CodeReceptionMethodDialog().show(fragmentManager, "receptionMethod")
    }

    fun showProgressDialog() {
        progressbar.visibility = ProgressBar.VISIBLE
        black_overlay.visibility = View.VISIBLE
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    fun hideProgressDialog() {
        progressbar.visibility = ProgressBar.INVISIBLE
        black_overlay.visibility = View.INVISIBLE
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    //to veryfy if the entered data is valid
    fun onConnectClicked()
    {
       try {
           verifyField(email.editText?.text.toString(),email,InputType.EMAIL,true,this)
           verifyField(motdepasse.editText?.text.toString(),motdepasse,InputType.OTHER,true,this)

           mail=email.editText?.text.toString()
           passwd = motdepasse.editText?.text.toString()

           showChoiceDialog()
       }catch (e:InvalideInputException)
       {

       }

    }

    fun showDialogMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setNeutralButton("Réessayer", DialogInterface.OnClickListener { _, _ ->

        })
        builder.create().show()
    }



}
