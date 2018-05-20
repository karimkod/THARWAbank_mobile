package com.tharwa.solid.tharwa.Repositories

object Injection
{

    fun provideUserRepository():UserRepository
    {
        return UserRepository.getInstance()
    }

    fun provideAccountRepository():AccountRepository
    {
        return AccountRepository.getInstance()
    }
}