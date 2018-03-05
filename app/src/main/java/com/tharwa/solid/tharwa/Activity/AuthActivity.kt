package com.tharwa.solid.tharwa.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tharwa.solid.tharwa.Model.User
import com.tharwa.solid.tharwa.Model.UserCode
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.Remote.UserApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class AuthActivity : AppCompatActivity() {


    var disposable: Disposable? = null
    private val Service by lazy {
        UserApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)
        val user = User("ez_taklit@esi.dz", "password", 0)
        login(user)
        /*val userCode= UserCode("ez_taklit@esi.dz","password","6765")
        loginCode(userCode)*/

    }
    private fun login(user: User) {
        disposable = Service.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user -> Log.v("voici le code", "" + user.code() ) },
                        { error -> Log.e("ERROR", error.message) }
                )
    }
    private fun loginCode(usercd: UserCode) {

        disposable = Service.loginCode(usercd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usercd -> Log.v("Authentifies user", "" + usercd ) },
                        { error //-> Log.e("ERROR", error.message )
                            ->Log.e("ERROR + la cause est ", error.cause.toString()+"code" + error.stackTrace )

                        }
                )
    }
    fun validateInput()
    {
        //valide email
        //valide password
    }
    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
/*when( startupResponseResponse.code())
                               {
                                   CodeStatus.succ200.status-> Toast.makeText(this@AuthActivity,
                                           getString(R.string.auth_200_ch0),Toast.LENGTH_LONG).show()
                                   CodeStatus.err_400.status->Toast.makeText(this@AuthActivity,
                                           getString(R.string.auth_400),Toast.LENGTH_LONG).show()
                               }*/