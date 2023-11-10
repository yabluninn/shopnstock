package com.yablunin.shopnstock.di

import com.yablunin.shopnstock.presentation.viewmodels.HomeViewModel
import com.yablunin.shopnstock.presentation.viewmodels.LoginViewModel
import com.yablunin.shopnstock.presentation.viewmodels.MainViewModel
import com.yablunin.shopnstock.presentation.viewmodels.ShoppingListViewModel
import com.yablunin.shopnstock.presentation.viewmodels.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainViewModel> {
        MainViewModel()
    }

    viewModel<HomeViewModel>{
        HomeViewModel(
            auth = get(),
            saveUserUseCase = get(),
            loadUserUseCase = get(),
            addListUseCase = get(),
            generateListIdUseCase = get()
        )
    }

    viewModel<LoginViewModel>{
        LoginViewModel(
            loadUserUseCase = get(),
            auth = get()
        )
    }

    viewModel<ShoppingListViewModel> {
        ShoppingListViewModel(
            saveUserUseCase = get(),
            addItemUseCase = get(),
            removeItemUseCase = get(),
            getSizeUseCase = get(),
            getCompletedItemsCountUseCase = get(),
            removeListUseCase = get(),
            getListByIdUseCase = get(),
            renameListUseCase = get()
        )
    }

    viewModel<SignupViewModel> {
        SignupViewModel(
            saveUserUseCase = get(),
            auth = get()
        )
    }
}