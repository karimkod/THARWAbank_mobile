package com.tharwa.solid.tharwa.util
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity


/**
 * Created by LE on 23/04/2018.
 */


fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}
fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction{replace(frameId, fragment)}
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}