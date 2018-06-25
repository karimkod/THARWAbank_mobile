package com.tharwa.solid.tharwa.util

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.tharwa.solid.tharwa.Model.Account
import com.tharwa.solid.tharwa.View.AccountBlockedActivity
import com.tharwa.solid.tharwa.View.AccountWaitingActivity
import com.tharwa.solid.tharwa.View.ClientAcountActivity


/**
 * Created by LE on 23/04/2018.
 */


fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun getCorrectIntent(account: Account, applicationContext: Context, firstActivity: Boolean): Intent? =
        when (account.status) {
            Config.BLOCKED_ACCOUNT_CODE -> {
                Intent(applicationContext, AccountBlockedActivity::class.java).apply {
                    if (firstActivity)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra("account_code", account.id)
                    putExtra("currency_code", account.currency)
                    putExtra("type", account.type)
                }

            }
            Config.WAITING_ACCOUNT_CODE -> {
                Intent(applicationContext, AccountWaitingActivity::class.java).apply {
                    if (firstActivity)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                }
            }
            Config.UNBLOCKED_ACCOUNT_CODE, Config.VALIDATE_ACCOUNT_CODE -> {
                if (firstActivity)
                    Intent(applicationContext, ClientAcountActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    } else
                    null
            }
            else -> {
                null
            }
        }