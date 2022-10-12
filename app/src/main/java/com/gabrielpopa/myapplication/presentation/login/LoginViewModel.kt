package com.gabrielpopa.myapplication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielpopa.myapplication.data.login.remote.dto.LoginRequest
import com.gabrielpopa.myapplication.domain.common.BaseResult
import com.gabrielpopa.myapplication.domain.login.usecase.LoginUseCase
import com.gabrielpopa.myapplication.presentation.common.FlowState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
class LoginViewModel : ViewModel() {

    private val loginUseCase : LoginUseCase = LoginUseCase()
    private val state = MutableStateFlow<FlowState>(FlowState.Init)
    val mState: StateFlow<FlowState> get() = state

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            loginUseCase.execute(loginRequest)
                .onStart {
                    state.value = FlowState.IsLoading(true)
                }
                .catch { exception ->
                    state.value = FlowState.IsLoading(false)
                    state.value = FlowState.ShowToast(exception.message.toString())
                }
                .collect { baseResult ->
                    state.value = FlowState.IsLoading(false)
                    when(baseResult){
                        is BaseResult.Error -> state.value = FlowState.Loaded.Error(baseResult.rawResponse)
                        is BaseResult.Success -> state.value = FlowState.Loaded.Success(baseResult.data)
                    }
                }
        }
    }

}

