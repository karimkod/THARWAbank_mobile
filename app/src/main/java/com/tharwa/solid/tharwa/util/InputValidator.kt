package com.tharwa.solid.tharwa.Bussiness

import android.content.Context
import android.support.design.widget.TextInputLayout
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.enumration.InputType

/**
 * Created by thinkpad on 12/03/2018.
 */
object InputValidator {
    fun checkInput(text: String, context: Context, type: InputType, required: Boolean = false): String? {


        if (text.isEmpty() && required) {
            return context.resources.getString(R.string.champvide)

        }
        when (type) {
            InputType.EMAIL -> {
                if (!Regex(context.resources.getString(R.string.Email_regex)).matches(text)) {
                    return context.resources.getString(R.string.emailInvalide)

                }
            }
            InputType.CODE -> {
                if (!Regex(context.resources.getString(R.string.Code_regex)).matches(text)) {
                    return context.resources.getString(R.string.codeInvalide)

                }
            }
            InputType.NAME ->
            {
                if(!Regex(context.resources.getString(R.string.Name_regex)).matches(text))
                    return context.resources.getString(R.string.champInvalide)
            }
            InputType.NUMERO->
            {
                if(!Regex(context.resources.getString(R.string.Tel_regex)).matches(text))
                    return context.resources.getString(R.string.champInvalide)
            }

            InputType.WILAYA ->
            {
                if(text == context.resources.getString(R.string.wilaya))
                    return context.resources.getString(R.string.wilayaInvalide)
            }

            InputType.ACCOUNTNUMBER ->
            {
                if(text.length < 6)
                    return "Le numéro de compte doit contenir 6 chiffres"

            }

            InputType.MONTANT ->
            {
                try {
                    val montantFloat = text.toFloat()
                    if (montantFloat <= 0)
                        return "le montant doit être supriéure à zero"
                }catch (e:NumberFormatException)
                {
                    return "Veuillez introduire un montant valide"
                }

            }
            else -> return null

        }

        return null

    }

}
