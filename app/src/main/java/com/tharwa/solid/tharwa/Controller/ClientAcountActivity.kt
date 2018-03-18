package com.tharwa.solid.tharwa.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.Menu
import android.widget.Toast
import android.widget.Toolbar
import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.View.CommissionFragment
import com.tharwa.solid.tharwa.View.CostomPagerAdapter
import com.tharwa.solid.tharwa.View.VirementsFragment
import kotlinx.android.synthetic.main.activity_client_acount.*

class ClientAcountActivity : AppCompatActivity() {


    private var pageAdapter: CostomPagerAdapter? = null
    private var pager: ViewPager? = null
    private var buttomNavigation:BottomNavigationView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_acount)
        setSupportActionBar(findViewById(R.id.acount_toolbar))


        // les fragments de la tab Barre
        pageAdapter = CostomPagerAdapter(supportFragmentManager)
        pager = findViewById<ViewPager>(R.id.homeViewPager)

        pageAdapter!!.addFragment(VirementsFragment(), "Virements")
        pageAdapter!!.addFragment(CommissionFragment(), "Commissions")

        pager?.adapter = pageAdapter
        homeTabBarre.setupWithViewPager(pager)

        buttomNavigation = findViewById(R.id.bottom_navigation)
        buttomNavigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.acount_action_bar_menu, menu)
        return true
    }

    // Gestion des evenements de Buttom Navigation Bar
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.buttom_navigation_comptes -> {
                //"Mes comptes" clicked
                Toast.makeText(this@ClientAcountActivity,"MES COMTES",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.buttom_navigation_taux_changes -> {
                // "Taux de changes"  clicked
                Toast.makeText(this@ClientAcountActivity,"TAUX DE CHANGES",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.buttom_navigation_virements -> {
                // "virements clicked"  clicked
                Toast.makeText(this@ClientAcountActivity,"VIREMENTS",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
