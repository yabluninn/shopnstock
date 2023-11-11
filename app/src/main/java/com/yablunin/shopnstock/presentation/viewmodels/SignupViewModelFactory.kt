package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

class SignupViewModelFactory(
    val saveUserUseCase: SaveUserUseCase,
    val auth: FirebaseAuth
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel(saveUserUseCase, auth) as T
    }
}