package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yablunin.shopnstock.domain.usecases.config.LoadConfigUseCase

class MainViewModelFactory(
    private val loadConfigUseCase: LoadConfigUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            loadConfigUseCase = loadConfigUseCase
        ) as T
    }
}