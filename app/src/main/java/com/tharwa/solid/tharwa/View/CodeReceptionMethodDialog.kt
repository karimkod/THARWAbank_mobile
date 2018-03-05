package com.tharwa.solid.tharwa.View

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle

/**
 * Created by thinkpad on 05/03/2018.
 */
class CodeReceptionMethodDialog : DialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Veuillez choisir une méthode de recéptino du code")
        val choices:Array<CharSequence> = arrayOf("SMS","Email")
        builder.setSingleChoiceItems(choices,1, null)
        builder.setPositiveButton("Terminé",null)

        return builder.create()

    }
}