package com.tharwa.solid.tharwa.View

import android.content.Context
import android.widget.Toast

/**
 * Created by LE on 18/03/2018.
 */
interface ViewInterface
{
    //to display the error
    fun showError(context: Context, message:String)
    {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }
    //to display te given message according to the code
    fun showMessage(context: Context,message:String)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG)
    }
}