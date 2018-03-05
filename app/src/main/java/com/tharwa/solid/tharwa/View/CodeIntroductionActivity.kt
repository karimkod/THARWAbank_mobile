package com.tharwa.solid.tharwa.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tharwa.solid.tharwa.Model.UserCode
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Remote.UserApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.code_introduction_activity.*

class CodeIntroductionActivity : AppCompatActivity() {

    var disposable: Disposable? = null
    private val Service by lazy {
        UserApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.code_introduction_activity)
        //valider.setOnClickListener({loginCode(code.editText?.text)})


    }
    private fun loginCode(usercd: UserCode):Unit {

        disposable = Service.loginCode(usercd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usercd -> Log.v("user", "" + usercd ) },
                        { error->
                            Log.e("ERROR", error.message )
                            //-> Log.e("ERROR", error.message )
                            //->Log.e("ERROR + la cause est ", error.cause.toString()+"code" + error.stackTrace )

                        }
                )
    }
}
