package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase

class LoginViewModelFactory: ViewModelProvider.Factory {

    private val loadUserUseCase = LoadUserUseCase(FirebaseUserRepository())
    private val auth = FirebaseAuth.getInstance()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(loadUserUseCase, auth) as T
    }
}