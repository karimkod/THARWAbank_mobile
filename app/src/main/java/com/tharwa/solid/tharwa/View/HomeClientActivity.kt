package com.tharwa.solid.tharwa.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import com.tharwa.solid.tharwa.R
import kotlinx.android.synthetic.main.activity_home_client.*

class HomeClientActivity : AppCompatActivity() {

    private  var pageAdapter:CostomPagerAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_client)

        // les fragments de la tab Barre
        pageAdapter = CostomPagerAdapter(supportFragmentManager)
        pageAdapter!!.addFragment(VirementsFragment(), "Virements")
        pageAdapter!!.addFragment(CommissionFragment(), "Commissions")
        homeViewPager.adapter = pageAdapter
        homeTabBarre.setupWithViewPager(homeViewPager)

        // Navigation Drawer



    }
}