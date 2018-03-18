package com.tharwa.solid.tharwa.Presenter

import com.tharwa.solid.tharwa.R
import com.tharwa.solid.tharwa.enumration.CodeStatus

/**
 * Created by LE on 18/03/2018.
 */
class Error
{
    companion object {
        fun codeMessage(code:Int):String
        {
            var message:String?=null
            when(code)
            {
                CodeStatus.succ_201.status->{message= R.string.succ_201 as String}
                CodeStatus.err_400.status->{message= R.string.err_400 as String}
                CodeStatus.err_403.status->{message= R.string.err_403 as String}
                CodeStatus.err_405.status->{message= R.string.err_405 as String}
                CodeStatus.err_422.status->{message= R.string.err_422 as String}
                CodeStatus.err_500.status->{message= R.string.err_500 as String}
                CodeStatus.err_503.status->{message= R.string.err_503 as String}
            }
            return message as String
        }
    }
}
