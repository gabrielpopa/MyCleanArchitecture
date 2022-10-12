package com.gabrielpopa.myapplication.data.common

import com.gabrielpopa.myapplication.data.common.utils.RequestInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
object NetworkModule {

    private var okHttp : OkHttpClient = provideOkHttp(provideRequestInterceptor())
    var retrofit : Retrofit = createRetrofit()

    private fun createRetrofit() : Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl("https://golang-heroku.herokuapp.com/api/")
        }.build()
    }

    private fun provideOkHttp(requestInterceptor: RequestInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
        }.build()
    }

    private fun provideRequestInterceptor() : RequestInterceptor {
        return RequestInterceptor()
    }

}