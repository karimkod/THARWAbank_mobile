package com.tharwa.solid.tharwa.Base

/**
 * Created by LE on 18/03/2018.
 */
import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity

 abstract class BaseActivity <Presenter  : BasePresenter>:AppCompatActivity() {

   protected var mPresenter: Presenter?=null
    @NonNull
    protected abstract fun createPresenter(@NonNull context:Context):Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter(this)
        mPresenter?.onCreate(savedInstanceState)
    }

    public override fun onResume() {
        super.onResume()
        mPresenter?.onResume()
    }

    public override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }

     public override fun onPause() {
         super.onPause()
         mPresenter?.onPause()
     }
    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mPresenter?.onSaveInstanceState(outState)
    }

}
