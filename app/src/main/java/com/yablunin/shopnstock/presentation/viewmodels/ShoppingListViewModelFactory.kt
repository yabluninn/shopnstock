package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.domain.repositories.ShoppingListHandlerRepository
import com.yablunin.shopnstock.domain.repositories.ShoppingListRepository
import com.yablunin.shopnstock.domain.usecases.list.AddItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetCompletedItemsCountUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetSizeUseCase
import com.yablunin.shopnstock.domain.usecases.list.RemoveItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GetListByIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RemoveListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RenameListUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

class ShoppingListViewModelFactory: ViewModelProvider.Factory {

    private val saveUserUseCase = SaveUserUseCase(FirebaseUserRepository())

    private val addItemUseCase = AddItemUseCase(ShoppingListRepository())
    private val removeItemUseCase = RemoveItemUseCase(ShoppingListRepository())
    private val getSizeUseCase = GetSizeUseCase(ShoppingListRepository())
    private val getCompletedItemsCountUseCase = GetCompletedItemsCountUseCase(ShoppingListRepository())

    private val removeListUseCase = RemoveListUseCase(ShoppingListHandlerRepository())
    private val getListByIdUseCase = GetListByIdUseCase(ShoppingListHandlerRepository())
    private val renameListUseCase = RenameListUseCase(ShoppingListHandlerRepository())
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingListViewModel(
            saveUserUseCase = saveUserUseCase,
            addItemUseCase = addItemUseCase,
            removeItemUseCase = removeItemUseCase,
            getSizeUseCase = getSizeUseCase,
            getCompletedItemsCountUseCase = getCompletedItemsCountUseCase,
            removeListUseCase = removeListUseCase,
            getListByIdUseCase = getListByIdUseCase,
            renameListUseCase = renameListUseCase
        ) as T
    }
}