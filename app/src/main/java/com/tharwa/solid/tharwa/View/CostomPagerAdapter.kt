package com.tharwa.solid.tharwa.View

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by mac on 09/03/2018.
 */
class CostomPagerAdapter(fragManger: FragmentManager) : FragmentPagerAdapter(fragManger) {



    private val fragmentList = ArrayList <Fragment> ()
    private val fragmentTitleList = ArrayList <String> ()

    override fun getItem(position: Int): Fragment {

        return fragmentList.get(position)
    }

    override fun getCount(): Int {
       return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList.get(position)
    }

    fun addFragment(fragmentItem: Fragment, fragmentTitle:String){
        fragmentList.add(fragmentItem)
        fragmentTitleList.add(fragmentTitle)
    }

}