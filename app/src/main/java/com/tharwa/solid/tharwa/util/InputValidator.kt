package com.tharwa.solid.tharwa.Bussiness

import android.content.Context
import android.content.res.Resources
import android.support.design.widget.TextInputLayout
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.enumration.InputType

/**
 * Created by thinkpad on 12/03/2018.
 */
object InputValidator {
    fun checkInput(text: String, resources: Resources, type: InputType, required: Boolean = false): String? {


        if (text.isEmpty() && required) {
            return resources.getString(R.string.champvide)

        }
        when (type) {
            InputType.EMAIL -> {
                if (!Regex(resources.getString(R.string.Email_regex)).matches(text)) {
                    return resources.getString(R.string.emailInvalide)

                }
            }
            InputType.CODE -> {
                if (!Regex(resources.getString(R.string.Code_regex)).matches(text)) {
                    return resources.getString(R.string.codeInvalide)

                }
            }
            InputType.NAME ->
            {
                if(!Regex(resources.getString(R.string.Name_regex)).matches(text))
                    return resources.getString(R.string.champInvalide)
            }
            InputType.NUMERO->
            {
                if(!Regex(resources.getString(R.string.Tel_regex)).matches(text))
                    return resources.getString(R.string.champInvalide)
            }



            InputType.WILAYA ->
            {
                if(text == resources.getString(R.string.wilaya))
                    return resources.getString(R.string.wilayaInvalide)
            }

            InputType.ACCOUNTNUMBER ->
            {
                if(text.length < 6 )
                    return resources.getString(R.string.accountNumberInvalide)
                else if(!Regex(resources.getString(R.string.number_regex)).matches(text))
                    return resources.getString(R.string.champInvalide)

            }

            InputType.ACCOUNTNUMBEREXTERNE ->
            {
                if(!Regex(resources.getString(R.string.number_regex)).matches(text))
                    return resources.getString(R.string.champInvalide)

            }



            InputType.MONTANT ->
            {
                try {
                    val montantFloat = text.toFloat()
                    if (montantFloat <= 0)
                        return resources.getString(R.string.montantNegatif)
                }catch (e:NumberFormatException)
                {
                    return resources.getString(R.string.montantInvalide)
                }

            }
            else -> return null

        }

        return null

    }

}
