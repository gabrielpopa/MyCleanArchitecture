package com.gabrielpopa.myapplication.presentation.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondRequest
import com.gabrielpopa.myapplication.data.second.remote.dto.SecondResponse
import com.gabrielpopa.myapplication.domain.common.BaseResult
import com.gabrielpopa.myapplication.domain.second.entity.SecondEntity
import com.gabrielpopa.myapplication.domain.second.usecase.SecondUseCase
import com.gabrielpopa.myapplication.presentation.common.FlowState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class SecondViewModel : ViewModel() {

    private val registerUseCase : SecondUseCase = SecondUseCase()
    private val state = MutableStateFlow<FlowState>(FlowState.Init)
    val mState: StateFlow<FlowState> get() = state

    fun doNetworkCall(registerRequest: SecondRequest){
        viewModelScope.launch {
            registerUseCase.invoke(registerRequest)
                .onStart {
                    state.value = FlowState.IsLoading(true)
                }
                .catch { exception ->
                    state.value = FlowState.IsLoading(false)
                    state.value  = FlowState.ShowToast(exception.message.toString())
                }
                .collect { result ->
                    state.value = FlowState.IsLoading(false)
                    when(result){
                        is BaseResult.Success -> state.value = FlowState.Loaded.Success(result.data)
                        is BaseResult.Error -> state.value = FlowState.Loaded.Error(result.rawResponse)
                    }
                }
        }
    }
}
