package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

class SignupViewModelFactory: ViewModelProvider.Factory {
    private val auth = FirebaseAuth.getInstance()
    private val saveUserUseCase = SaveUserUseCase(FirebaseUserRepository())
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignupViewModel(
            saveUserUseCase = saveUserUseCase,
            auth = auth
        ) as T
    }
}