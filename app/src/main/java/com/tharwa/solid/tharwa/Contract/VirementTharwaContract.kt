package com.tharwa.solid.tharwa.Contract

import com.tharwa.solid.tharwa.Presenter.VirementTharwaPresenter
import com.tharwa.solid.tharwa.View.VirementTharwaActivity
import com.tharwa.solid.tharwa.util.BaseActivity

interface VirementTharwaContract
{
    interface View:BaseActivity<VirementTharwaPresenter>
    {
        fun showDialogMessage(title:String, message:String)
        fun showConfirmationMethod(name:String,wilaya:String,commune:String)
        fun isValidInputs():Boolean
        fun showPicturePlace()
        fun hidePicturePlase()
        fun showProgressDialog()
        fun hideProgressDialog()
        fun finish()
        fun showSuccessDialog(title:String, message:String)
        val destinationAccount:String
        val montant:String


    }
}