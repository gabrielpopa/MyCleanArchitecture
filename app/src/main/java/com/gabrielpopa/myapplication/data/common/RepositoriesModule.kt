package com.gabrielpopa.myapplication.data.common

import com.gabrielpopa.myapplication.data.login.remote.api.LoginApi
import com.gabrielpopa.myapplication.data.login.repository.LoginRepositoryImpl
import com.gabrielpopa.myapplication.data.second.remote.api.SecondApi
import com.gabrielpopa.myapplication.data.second.repository.SecondRepositoryImpl
import com.gabrielpopa.myapplication.domain.login.repository.LoginRepository
import com.gabrielpopa.myapplication.domain.second.SecondRepository

object RepositoriesModule {
    fun provideLoginRepository() : LoginRepository {
        return LoginRepositoryImpl(NetworkModule.retrofit.create(LoginApi::class.java))
    }

    fun provideSecondRepository() : SecondRepository {
        return SecondRepositoryImpl(NetworkModule.retrofit.create(SecondApi::class.java))
    }
}