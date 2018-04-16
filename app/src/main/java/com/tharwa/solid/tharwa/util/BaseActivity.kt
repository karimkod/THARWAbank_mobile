package com.tharwa.solid.tharwa.util

import android.app.Application
import android.content.Context
import android.widget.Toast
import java.security.AccessController.getContext


/**
 * Created by LE on 16/04/2018.
 */
interface BaseActivity<T>
{
    val presenter:T

     fun showMessage( message:String)

}