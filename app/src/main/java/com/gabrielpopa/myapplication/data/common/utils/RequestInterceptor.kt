package com.gabrielpopa.myapplication.data.common.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "token")
            .build()

        Log.i("RequestInterceptor", "url: " + newRequest.url.toString())
        return chain.proceed(newRequest)
    }
}