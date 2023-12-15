package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.domain.usecases.api.GetFlagImageUseCase
import com.yablunin.shopnstock.domain.usecases.config.LoadConfigUseCase
import com.yablunin.shopnstock.domain.usecases.config.SaveConfigUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.user.ChangeUsernameUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

class HomeViewModelFactory(
    val auth: FirebaseAuth,
    val saveUserUseCase: SaveUserUseCase,
    val loadUserUseCase: LoadUserUseCase,
    val addListUseCase: AddListUseCase,
    val generateListIdUseCase: GenerateListIdUseCase,
    val changeUsernameUseCase: ChangeUsernameUseCase,
    val loadConfigUseCase: LoadConfigUseCase,
    val saveConfigUseCase: SaveConfigUseCase,
    val getFlagImageUseCase: GetFlagImageUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            auth = auth,
            saveUserUseCase = saveUserUseCase,
            loadUserUseCase = loadUserUseCase,
            addListUseCase = addListUseCase,
            generateListIdUseCase = generateListIdUseCase,
            changeUsernameUseCase = changeUsernameUseCase,
            loadConfigUseCase = loadConfigUseCase,
            saveConfigUseCase = saveConfigUseCase,
            getFlagImageUseCase = getFlagImageUseCase
        ) as T
    }
}