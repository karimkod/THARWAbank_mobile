package com.tharwa.solid.tharwa.Model

import kotlin.properties.Delegates

/**
 * Created by LE on 12/04/2018.
 */
class UserClass
{
    companion object {
       // lateinit var token:String
        var token: String by Delegates.notNull<String>()
        var  num_acc_sender:Int by Delegates.notNull<Int>()
        var  type_acc_sender:Int by Delegates.notNull<Int>()
        var  code_curr_sender: String by Delegates.notNull<String>()

    }
}