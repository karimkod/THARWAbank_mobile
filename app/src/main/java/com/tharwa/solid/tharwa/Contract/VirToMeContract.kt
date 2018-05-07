package com.tharwa.solid.tharwa.Contract

import com.tharwa.solid.tharwa.Presenter.Virement.VirToMePresenter
import com.tharwa.solid.tharwa.util.BaseActivity


/**
 * Created by LE on 05/05/2018.
 */
interface VirToMeContract
{
    interface  View: BaseActivity<VirToMePresenter>
    {
        fun closeDialog()
        fun relodeActivity()
    }
}