package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

class HomeViewModelFactory(
    val auth: FirebaseAuth,
    val saveUserUseCase: SaveUserUseCase,
    val loadUserUseCase: LoadUserUseCase,
    val addListUseCase: AddListUseCase,
    val generateListIdUseCase: GenerateListIdUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            auth = auth,
            saveUserUseCase = saveUserUseCase,
            loadUserUseCase = loadUserUseCase,
            addListUseCase = addListUseCase,
            generateListIdUseCase = generateListIdUseCase
        ) as T
    }
}