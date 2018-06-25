package com.tharwa.solid.tharwa.Contract

import com.tharwa.solid.tharwa.Presenter.Virement.VirementExternePresenter
import com.tharwa.solid.tharwa.util.BaseActivity

interface VirementExterneContract
{
    interface View: BaseActivity<VirementExternePresenter>
    {

        val montant:String
        val numAccountReceiver:String
        val codeCurrencyReceiver:String
        val nameReceiver:String
        val bankReceiver:String
        fun showDialogMessage(title:String, message:String)
        fun showConfirmationMethod()
        fun isValidInputs():Boolean
        fun showPicturePlace()
        fun hidePicturePlace()
        fun showProgressDialog()
        fun hideProgressDialog()
        fun finish()
        fun showSuccessDialog(title:String, message:String)
        fun loadBankSpinner(banks:ArrayList<String>)

    }
}