package com.tharwa.solid.tharwa.View

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.tharwa.solid.tharwa.R

/**
 * Created by thinkpad on 27/03/2018.
 */
class LoadingFragment : DialogFragment()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val progressDialog = ProgressDialog(getActivity(), getTheme())
        progressDialog.setTitle(getString(R.string.proceding))
        progressDialog.setMessage(getString(R.string.inscription_msg))
        progressDialog.isIndeterminate = true
        progressDialog.progress = ProgressDialog.STYLE_SPINNER
        return progressDialog
    }

}
