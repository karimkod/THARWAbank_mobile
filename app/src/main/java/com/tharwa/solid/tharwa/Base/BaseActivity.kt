package com.tharwa.solid.tharwa.Base

/**
 * Created by LE on 18/03/2018.
 */
import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

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
    //to display the error
   fun showError(context: Context, message:String)
    {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }
    //to display te given message according to the code
    fun showMessage(context: Context,message:String)
    {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }
}
