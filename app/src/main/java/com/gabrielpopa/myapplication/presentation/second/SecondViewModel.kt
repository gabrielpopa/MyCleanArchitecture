package com.gabrielpopa.myapplication.presentation.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondRequest
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondResponse
import com.gabrielpopa.myapplication.domain.common.BaseResult
import com.gabrielpopa.myapplication.domain.login.usecase.LoginUseCase
import com.gabrielpopa.myapplication.domain.second.entity.SecondEntity
import com.gabrielpopa.myapplication.domain.second.usecase.SecondUseCase
import com.gabrielpopa.myapplication.presentation.common.WrappedState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class SecondViewModel : ViewModel() {

    private val registerUseCase : SecondUseCase = SecondUseCase()
    private val state = MutableStateFlow<SecondActivityState>(SecondActivityState.Init)
    val mState: StateFlow<SecondActivityState> get() = state

    private fun setLoading(){
        state.value = SecondActivityState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = SecondActivityState.IsLoading(false)
    }

    private fun showToast(message: String){
        state.value  = SecondActivityState.ShowToast(message)
    }

    private fun successRegister(registerEntity: SecondEntity){
        state.value = SecondActivityState.Success(registerEntity)
    }

    private fun failedRegister(rawResponse: WrappedResponse<SecondResponse>){
        state.value = SecondActivityState.Error(rawResponse)
    }

    fun doNetworkCall(registerRequest: SecondRequest){
        viewModelScope.launch {
            registerUseCase.invoke(registerRequest)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    showToast(exception.message.toString())
                    hideLoading()
                }
                .collect { result ->
                    hideLoading()
                    when(result){
                        is BaseResult.Success -> successRegister(result.data)
                        is BaseResult.Error -> failedRegister(result.rawResponse)
                    }
                }
        }
    }
}

sealed class SecondActivityState {
    object Init : SecondActivityState()
    data class IsLoading(val isLoading: Boolean) : SecondActivityState()
    data class ShowToast(val message: String) : SecondActivityState()
    data class Success(val registerEntity: SecondEntity) : SecondActivityState()
    data class Error(val rawResponse: WrappedResponse<SecondResponse>) : SecondActivityState()
}