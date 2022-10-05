package com.gabrielpopa.myapplication.domain.login

import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.login.remote.dto.LoginRequest
import com.gabrielpopa.myapplication.data.login.remote.dto.LoginResponse
import com.gabrielpopa.myapplication.domain.base.BaseResult
import com.gabrielpopa.myapplication.domain.login.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest) : Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>>
}