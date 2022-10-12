package com.gabrielpopa.myapplication.presentation.common

import com.gabrielpopa.myapplication.presentation.second.SecondActivityState

sealed class WrappedState<T>() {
    data class Success<T>(val value: T): WrappedState<T>()
    //object Init<T> : WrappedActivityState<T>()
    //class Success<T>(data: T) : WrappedActivityState<T>(data)
}