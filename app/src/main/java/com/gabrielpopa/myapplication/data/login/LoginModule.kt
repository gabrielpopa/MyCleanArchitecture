package com.gabrielpopa.myapplication.data.login

import com.gabrielpopa.myapplication.data.common.NetworkModule
import com.gabrielpopa.myapplication.domain.login.repository.LoginRepository
import com.gabrielpopa.myapplication.data.login.remote.api.LoginApi
import com.gabrielpopa.myapplication.data.login.repository.LoginRepositoryImpl

import retrofit2.Retrofit
object LoginModule {
    private fun provideLoginApi(retrofit: Retrofit) : LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    private fun provideLoginRepository(loginApi: LoginApi) : LoginRepository {
        return LoginRepositoryImpl(loginApi)
    }

    fun provideLoginRepository() : LoginRepository {
        return provideLoginRepository(provideLoginApi(NetworkModule.retrofit))
    }
}