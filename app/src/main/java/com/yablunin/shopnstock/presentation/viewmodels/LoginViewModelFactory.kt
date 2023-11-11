package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase

class LoginViewModelFactory(
    val auth: FirebaseAuth,
    val loadUserUseCase: LoadUserUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(auth, loadUserUseCase) as T
    }
}