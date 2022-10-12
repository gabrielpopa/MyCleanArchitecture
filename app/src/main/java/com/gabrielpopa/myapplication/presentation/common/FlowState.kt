package com.gabrielpopa.myapplication.presentation.common

import com.gabrielpopa.myapplication.data.common.utils.WrappedResponse
import com.gabrielpopa.myapplication.domain.common.Entity

sealed class FlowState {
    object Init : FlowState()
    data class IsLoading(val isLoading: Boolean) : FlowState()
    data class ShowToast(val message: String) : FlowState()
    sealed class Loaded<out T>(val result: T) : FlowState() {
        data class Success(val value: Entity): Loaded<Entity>(value)
        data class Error(val value: WrappedResponse<*>): Loaded<WrappedResponse<*>>(value)
    }
}