package com.gabrielpopa.myapplication.data.login

import com.gabrielpopa.myapplication.domain.login.repository.LoginRepository
import com.gabrielpopa.myapplication.data.login.remote.api.LoginApi
import com.gabrielpopa.myapplication.data.login.repository.LoginRepositoryImpl

import retrofit2.Retrofit
class LoginModule {
    fun provideLoginApi(retrofit: Retrofit) : LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    fun provideLoginRepository(loginApi: LoginApi) : LoginRepository {
        return LoginRepositoryImpl(loginApi)
    }
}