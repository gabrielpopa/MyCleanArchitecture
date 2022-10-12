package com.gabrielpopa.myapplication.domain.second

import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondRequest
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondResponse
import com.gabrielpopa.myapplication.domain.common.BaseResult
import com.gabrielpopa.myapplication.domain.second.entity.SecondEntity
import kotlinx.coroutines.flow.Flow

interface SecondRepository {
    suspend fun register(registerRequest: SecondRequest) : Flow<BaseResult<SecondEntity, WrappedResponse<SecondResponse>>>
}