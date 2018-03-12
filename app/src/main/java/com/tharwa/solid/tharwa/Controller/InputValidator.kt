package com.tharwa.solid.tharwa.Controller

import android.content.Context
import android.support.design.widget.TextInputLayout
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.enumration.InputType

/**
 * Created by thinkpad on 12/03/2018.
 */
object InputValidator
{
    fun checkInput(inputLayout: TextInputLayout, context: Context? = null, type: InputType = InputType.NAME):Boolean
    {
        val text = inputLayout.editText?.text

        if(text!!.isEmpty())
        {
            inputLayout.error =  "Veuillez remplir ce champ"
            return false
        }

        when (type)
        {
            InputType.EMAIL -> {
                if (!Regex(context?.resources!!.getString(R.string.Email_regex)).matches(text as CharSequence))
                {
                    inputLayout.error = "Veuillez bien écrire votre mail"
                    return false

                }
            }
            InputType.CODE ->
            {
                if(!Regex(context?.resources!!.getString(R.string.Code_regex)).matches(text as CharSequence))
                {
                    inputLayout.error = "Le code ne doit contenir que des chiffres"
                    return false

                }
            }
            else -> return true

        }

        return true


    }
}