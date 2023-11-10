package com.yablunin.shopnstock.di

import com.yablunin.shopnstock.domain.repositories.ListHandlerRepository
import com.yablunin.shopnstock.domain.repositories.ListRepository
import com.yablunin.shopnstock.domain.repositories.ShoppingListHandlerRepository
import com.yablunin.shopnstock.domain.repositories.ShoppingListRepository
import com.yablunin.shopnstock.domain.usecases.list.AddItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetCompletedItemsCountUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetItemByIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetItemByIndexUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetSizeUseCase
import com.yablunin.shopnstock.domain.usecases.list.RemoveItemAtUseCase
import com.yablunin.shopnstock.domain.usecases.list.RemoveItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GetListByIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RemoveListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RenameListUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase
import org.koin.dsl.module

val domainModule = module {
    single<ListHandlerRepository> {
        ShoppingListHandlerRepository()
    }

    single<ListRepository> {
        ShoppingListRepository()
    }

    factory<SaveUserUseCase> {
        SaveUserUseCase(userRepository = get())
    }

    factory<LoadUserUseCase> {
        LoadUserUseCase(userRepository = get())
    }

    factory<AddListUseCase> {
        AddListUseCase(listHandlerRepository = get())
    }

    factory<RemoveListUseCase> {
        RemoveListUseCase(listHandlerRepository = get())
    }

    factory<GetListByIdUseCase> {
        GetListByIdUseCase(listHandlerRepository = get())
    }

    factory<RenameListUseCase> {
        RenameListUseCase(listHandlerRepository = get())
    }

    factory<GenerateListIdUseCase> {
        GenerateListIdUseCase(listHandlerRepository = get())
    }

    factory<GetSizeUseCase> {
        GetSizeUseCase(listRepository = get())
    }

    factory<GetCompletedItemsCountUseCase> {
        GetCompletedItemsCountUseCase(listRepository = get())
    }

    factory<AddItemUseCase> {
        AddItemUseCase(listRepository = get())
    }

    factory<RemoveItemUseCase> {
        RemoveItemUseCase(listRepository = get())
    }

    factory<GetItemByIdUseCase> {
        GetItemByIdUseCase(listRepository = get())
    }

    factory<GetItemByIndexUseCase> {
        GetItemByIndexUseCase(listRepository = get())
    }

    factory<RemoveItemAtUseCase> {
        RemoveItemAtUseCase(listRepository = get())
    }
}