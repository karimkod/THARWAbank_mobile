package com.tharwa.solid.tharwa.Controller

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
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {

        var choice:Int = 0

        if (activity is DialogChoiceInteraction)
        {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Veuillez choisir une méthode de recéption du code")
        val choices:Array<CharSequence> = arrayOf("Email","SMS")
        builder.setSingleChoiceItems(choices,0, DialogInterface.OnClickListener { _, which ->
            choice = which
        })

        builder.setNeutralButton("Terminé",DialogInterface.OnClickListener { _, _ ->

            (activity as DialogChoiceInteraction).onTermineClicked(choice)

        })
            return builder.create()

        }else
            return super.getDialog()



    }

    interface DialogChoiceInteraction
    {
        fun onTermineClicked(choice:Int)
    }



}