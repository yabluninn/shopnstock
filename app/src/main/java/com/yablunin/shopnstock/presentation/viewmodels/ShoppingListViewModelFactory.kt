package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yablunin.shopnstock.domain.usecases.list.AddItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetCompletedItemsCountUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetSizeUseCase
import com.yablunin.shopnstock.domain.usecases.list.RemoveItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GetListByIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RemoveListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RenameListUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

class ShoppingListViewModelFactory(
    val saveUserUseCase: SaveUserUseCase,
    val addItemUseCase: AddItemUseCase,
    val removeItemUseCase: RemoveItemUseCase,
    val getSizeUseCase: GetSizeUseCase,
    val getCompletedItemsCountUseCase: GetCompletedItemsCountUseCase,
    val removeListUseCase: RemoveListUseCase,
    val getListByIdUseCase: GetListByIdUseCase,
    val renameListUseCase: RenameListUseCase
): ViewModelProvider.Factory {
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