package com.gabrielpopa.myapplication.presentation.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabrielpopa.myapplication.domain.second.usecase.SecondUseCase

class SecondViewModelFactory(private val loginUseCase: SecondUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SecondUseCase::class.java).newInstance(loginUseCase)
    }
}