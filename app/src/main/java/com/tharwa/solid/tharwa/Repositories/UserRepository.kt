package com.tharwa.solid.tharwa.Repositories

import com.tharwa.solid.tharwa.Model.AccesInfo
import com.tharwa.solid.tharwa.Model.UserInfo

class UserRepository
{
    private lateinit var accessInfor: AccesInfo
    private lateinit var userInfo: UserInfo




    companion object {
        private var INSTANCE: UserRepository? = null

        private val lock = Any()

        fun getInstance(): UserRepository {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = UserRepository()
                }
                return INSTANCE!!
            }
        }

    }
}