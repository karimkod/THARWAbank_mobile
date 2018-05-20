package com.tharwa.solid.tharwa

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ScrollView
import com.tharwa.solid.tharwa.Bussiness.InputValidator
import com.tharwa.solid.tharwa.enumration.InputType

/**
 * Created by thinkpad on 27/03/2018.
 */


interface FormInterface
{
    @Throws(InvalideInputException::class)
    fun verifyField(text:String, input: TextInputLayout, type: InputType, required:Boolean,context: Context)
    {
        val error = InputValidator.checkInput(text,context.resources,type,required)
        if(error != null)
        {
            input.error = error
            if(context is AppCompatActivity)
            {
                context.findViewById<ScrollView>(R.id.scroll_view)?.apply {
                    post({kotlin.run { this.smoothScrollTo(0,input.top) }})
                }
            }
            throw InvalideInputException()
        }
    }

    fun clearErrors(vararg inputs:TextInputLayout)
    {
        for(i in inputs)
            i.error = ""
    }

    fun showDialogMessage(context:Context,title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setNeutralButton("Réessayer", null)
        builder.create().show()
    }


}

class InvalideInputException : Exception()
