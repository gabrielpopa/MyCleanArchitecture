package com.gabrielpopa.myapplication.domain.login.usecase

import com.gabrielpopa.myapplication.data.common.RepositoriesModule.provideLoginRepository
import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.login.remote.dto.LoginRequest
import com.gabrielpopa.myapplication.data.login.remote.dto.LoginResponse
import com.gabrielpopa.myapplication.domain.login.repository.LoginRepository
import com.gabrielpopa.myapplication.domain.login.entity.LoginEntity
import com.gabrielpopa.myapplication.domain.common.BaseResult
import kotlinx.coroutines.flow.Flow

class LoginUseCase {
    private val loginRepository : LoginRepository = provideLoginRepository()
    suspend fun execute(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>> {
        return loginRepository.login(loginRequest)
    }
}