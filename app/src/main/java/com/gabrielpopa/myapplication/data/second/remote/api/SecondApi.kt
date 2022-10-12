package com.gabrielpopa.myapplication.data.second.remote.api

import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondRequest
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SecondApi {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: SecondRequest) : Response<WrappedResponse<SecondResponse>>
}