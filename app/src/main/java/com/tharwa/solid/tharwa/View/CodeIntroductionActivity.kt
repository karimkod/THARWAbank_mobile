package com.tharwa.solid.tharwa.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

class CodeIntroductionActivity : AppCompatActivity() {

    var disposable: Disposable? = null
    private val Service by lazy {
        UserApiService.create()
    }

    val TAG = "CodeAuthentification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.code_introduction_activity)
        Toast.makeText(this@CodeIntroductionActivity,intent.getStringExtra("mail"),Toast.LENGTH_LONG).show()
        val mail:String?=intent.getStringExtra("mail")
        val passwd:String?=intent.getStringExtra("password")
        val nonce =code.editText?.text.toString()
        val usercode = UserCode(mail.toString(),passwd.toString(),nonce)
        valider.setOnClickListener({
            loginCode(usercode)
        })


    }
   private fun loginCode(usercd: UserCode):Unit {

        disposable = Service.loginCode(usercd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usercd ->
                            if (usercd.isSuccessful)
                            {
                                // get the token
                                //open the Acceuil activity
                                Toast.makeText(this@CodeIntroductionActivity,usercd.message(),Toast.LENGTH_LONG).show()
                            }
                            else
                            {
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
                            Toast.makeText(this@CodeIntroductionActivity,error.message,Toast.LENGTH_LONG).show()
                        }
                )
    }
}
