package com.tharwa.solid.tharwa.Repositories

class AccountRepository
{



    companion object {
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
