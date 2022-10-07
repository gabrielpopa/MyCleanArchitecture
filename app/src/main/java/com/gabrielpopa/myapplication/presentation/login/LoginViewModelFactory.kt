package com.gabrielpopa.myapplication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabrielpopa.myapplication.domain.login.usecase.LoginUseCase

class LoginViewModelFactory(private val loginUseCase: LoginUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginUseCase::class.java).newInstance(loginUseCase)
    }
}