package com.gabrielpopa.myapplication.domain.second.usecase

import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondRequest
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondResponse
import com.gabrielpopa.myapplication.domain.common.BaseResult
import com.gabrielpopa.myapplication.domain.second.SecondRepository
import com.gabrielpopa.myapplication.domain.second.entity.SecondEntity
import kotlinx.coroutines.flow.Flow

class SecondUseCase constructor(private val registerRepository: SecondRepository) {
    suspend fun invoke(registerRequest: SecondRequest) : Flow<BaseResult<SecondEntity, WrappedResponse<SecondResponse>>> {
        return registerRepository.register(registerRequest)
    }
}