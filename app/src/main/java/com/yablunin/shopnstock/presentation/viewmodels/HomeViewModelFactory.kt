package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.domain.repositories.ShoppingListHandlerRepository
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

class HomeViewModelFactory: ViewModelProvider.Factory {

    private val auth = FirebaseAuth.getInstance()
    private val saveUserUseCase = SaveUserUseCase(FirebaseUserRepository())
    private val loadUserUseCase = LoadUserUseCase(FirebaseUserRepository())
    private val addListUseCase = AddListUseCase(ShoppingListHandlerRepository())
    private val generateListIdUseCase = GenerateListIdUseCase(ShoppingListHandlerRepository())
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