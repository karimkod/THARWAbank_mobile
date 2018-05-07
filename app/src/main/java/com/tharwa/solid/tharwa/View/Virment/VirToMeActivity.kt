package com.tharwa.solid.tharwa.View.Virment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tharwa.solid.tharwa.R
import android.content.ContentValues.TAG


class VirToMeActivity : AppCompatActivity() {

    val dialog = VirToMeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vir_to_me)
        showBottomSheetDialog()

    }
    fun showBottomSheetDialog() {
        val ft = supportFragmentManager.beginTransaction()
        dialog.show(ft, TAG)
    }
}

