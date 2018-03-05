package com.tharwa.solid.tharwa.View

import android.app.FragmentManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import com.tharwa.solid.tharwa.Model.User
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Remote.UserApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.login_activity.view.*



class LoginActivity : AppCompatActivity(), CodeReceptionMethodDialog.DialogChoiceInteraction
{
    var mail:String?=null
    var passwd:String?=null
    val TAG = "LoginActivity"
   var user :User?=null
    override fun onTermineClicked(choice: Int)
    {
        user= User(mail.toString(),passwd.toString(),choice)
        login(user as User)
    }


    var disposable: Disposable? = null
    private val Service by lazy {
        UserApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        sign_up.setOnClickListener({
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })
        login.setOnClickListener(
                {
                     mail=email.editText?.text.toString()
                     passwd =motdepasse.editText?.text.toString()
                    showChoiceDialog()
                }
        )
    }
    fun validateInput(mail:String,password:String)
    {
        //

    }
    private fun login(user: User) {
        disposable = Service.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user -> //Log.v("voici le code", "" + user.code() )
                            if (user.isSuccessful)
                            {
                                Log.v("voici le code", "" + "success" )
                                val intent =Intent(this@LoginActivity,CodeIntroductionActivity::class.java)
                                intent.putExtra("mail",mail)
                                intent.putExtra("password",passwd)
                                startActivity(intent)
                            }
                            else
                            {
                                Log.e("voici le code", "" + "error" )
                            }

                        },
                        {  error-> Log.e("error",error.message)
                            //Afficher messgae vérifier votre connection
                        }
                )
    }
    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    fun showChoiceDialog()
    {
        CodeReceptionMethodDialog().show(fragmentManager,"receptionMethod")
    }


}
