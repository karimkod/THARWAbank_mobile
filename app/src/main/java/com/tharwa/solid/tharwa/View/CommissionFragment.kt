package com.tharwa.solid.tharwa.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tharwa.solid.tharwa.R

/**
 * Created by mac on 09/03/2018.
 */
class CommissionFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_commission, container, false)
    }


}