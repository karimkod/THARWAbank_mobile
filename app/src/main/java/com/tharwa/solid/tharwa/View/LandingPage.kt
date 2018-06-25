package com.tharwa.solid.tharwa.View


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.tharwa.solid.tharwa.Controller.LoginActivity
import com.tharwa.solid.tharwa.Model.*
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.R.string.Verif_int
import com.tharwa.solid.tharwa.Remote.CheckConnection
import com.tharwa.solid.tharwa.Remote.UserApiService
import com.tharwa.solid.tharwa.Repositories.Injection
import com.tharwa.solid.tharwa.enumration.CodeStatus
import com.tharwa.solid.tharwa.util.Config
import com.tharwa.solid.tharwa.util.FirebaseMessagingManager
import com.tharwa.solid.tharwa.util.getCorrectIntent
import kotlinx.android.synthetic.main.landing_page_activity.*
import retrofit2.Response

class LandingPage : AppCompatActivity() {


    val loadingFragment by lazy { LoadingFragment() }

    lateinit var attemptToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page_activity)

        startApplication()


    }

    fun startApplication() {
        if (!CheckConnection.isOnline(this)) {

            Snackbar.make(parentView, resources.getString(Verif_int), Snackbar.LENGTH_INDEFINITE).setAction("Réessayer") {
                startApplication()
            }.show()
        }
        //else we could show the login activity
        else {
            val sharedPref = this.getSharedPreferences(getString(com.tharwa.solid.tharwa.R.string.shared_pref_name), Context.MODE_PRIVATE)
            val token = sharedPref.getString(getString(R.string.token_key), "NaT")
            SignInWithToken(token)
        }
    }


    fun SignInWithToken(token: String) {
        attemptToken = token
        UserApiService.apply {
            sendRequest(create().signInWithToken(token), ::onSignInWithTokenSuccess, ::onFailed)
        }
    }

    fun onSignInWithTokenSuccess(response: Response<TokenResponse>) {

        if (response.isSuccessful) {

            val body = response.body()!!

            Injection.apply {
                provideUserRepository().apply {
                    accessInfos = AccesInfo(body.userId, attemptToken, 0, body.type)
                    userInfo = UserInfo(body.name, body.photoPath)
                }
                provideAccountRepository().apply {
                    cachedAccounts[1] = body.currentAccount
                    availableAccountsType = body.accountTypes
                }
            }
            FirebaseMessagingManager.registerFirebaseMessaging(attemptToken, FirebaseInstanceId.getInstance().token)

            startActivity(getCorrectIntent(response.body()!!.currentAccount,applicationContext,true))

        } else {
            // display messages acording to the recieved code
            when (response.code()) {
                CodeStatus.err_401.status -> {
                    refreshToken()
                }
                else ->
                    showDialogMessage(this, "Oops", "Une erreur c'est produite, veuillez reéssayer plus tard")

            }

        }
    }


    fun refreshToken() {
        val sharedPref = this.getSharedPreferences(getString(com.tharwa.solid.tharwa.R.string.shared_pref_name), Context.MODE_PRIVATE)
        val refreshToken = sharedPref.getString(getString(R.string.refresh_token_key), "NaT")

        UserApiService.apply {
            sendRequest(create().refreshToken(RefreshBody(refreshToken)), ::onRefreshSuccess, ::onFailed)

        }
    }

    fun onRefreshSuccess(response: Response<RefreshResponse>) {
        if (response.isSuccessful) {
            val sharedPref = this.getSharedPreferences(getString(com.tharwa.solid.tharwa.R.string.shared_pref_name), Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(getString(R.string.token_key), response.body()?.token)
                putString(getString(R.string.refresh_token_key), response.body()?.refreshToken)
                commit()
            }
            SignInWithToken(attemptToken)
        } else {
            when (response.code()) {
                CodeStatus.err_401.status -> {
                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else ->
                    showDialogMessage(this, "Oops", "Une erreur c'est produite, veuillez reéssayer plus tard")

            }
        }
    }


    fun onFailed(error: Throwable) {

        showDialogMessage(this, "Oops", error.message.toString())//"Une erreur c'est produite, veuillez reéssayer plus tard")
        //Toast.makeText(this@CodeIntroductionActivity,"Hello someone",Toast.LENGTH_LONG).show()

    }


    fun showDialogMessage(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setNeutralButton("Réessayer", null)
        builder.create().show()
    }
}

