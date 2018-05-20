package com.tharwa.solid.tharwa.Repositories

import com.tharwa.solid.tharwa.Model.Account
import java.util.*

class AccountRepository
{

    lateinit var availableAccountsType:Array<Int>
    var cachedAccounts: TreeMap<Int,Account> = TreeMap()
    private var selectedAccount:Int = 1


    fun getSelectedAccount():Account = cachedAccounts[selectedAccount]!!



    companion object
    {
        private var INSTANCE: AccountRepository? = null

        private val lock = Any()

        fun getInstance(): AccountRepository {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = AccountRepository()
                }
                return INSTANCE!!
            }
        }
    }
}
