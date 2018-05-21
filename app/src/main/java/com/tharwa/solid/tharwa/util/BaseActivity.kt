package com.tharwa.solid.tharwa.util



/**
 * Created by LE on 16/04/2018.
 */
interface BaseActivity<T>
{
    val presenter:T

     fun showMessage( message:String)
     fun showTag(tag: String, message:String)
}