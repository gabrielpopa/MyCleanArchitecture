package com.gabrielpopa.myapplication.presentation.common

sealed class FlowState {
    object Init : FlowState()
    data class IsLoading(val isLoading: Boolean) : FlowState()
    data class ShowToast(val message: String) : FlowState()
    sealed class Loaded<out T>(val result: T) : FlowState() {
        data class Success<T>(val value: T): Loaded<T>(value)
        data class Error<T>(val value: T): Loaded<T>(value)
    }
}