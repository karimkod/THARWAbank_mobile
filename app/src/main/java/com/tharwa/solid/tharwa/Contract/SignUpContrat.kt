package com.tharwa.solid.tharwa.Contract

import com.tharwa.solid.tharwa.Presenter.SignUpPresenter
import com.tharwa.solid.tharwa.util.BaseActivity

/**
 * Created by LE on 16/04/2018.
 */
interface SignUpContrat
{
    interface View:BaseActivity<SignUpPresenter>
    {
        fun hideProgressDialog()
        fun showSuccessDialog()
        fun isValidInputs():Boolean
        fun showProgressDialog()
        fun finish()
        fun showDialogMessage(title:String, message:String)
        val mail:String
        val password :String
        val phone_number :String
        val lastName :String
        val firstName :String
        val adress :String
        val function :String
        val wilaya :String
        val commune :String
        val type :Int

    }

}