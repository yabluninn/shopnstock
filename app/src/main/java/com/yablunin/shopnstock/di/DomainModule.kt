package com.yablunin.shopnstock.di

import com.yablunin.shopnstock.domain.repositories.ConfigRepository
import com.yablunin.shopnstock.domain.repositories.FlagsRepository
import com.yablunin.shopnstock.domain.repositories.ListHandlerRepository
import com.yablunin.shopnstock.domain.repositories.ListRepository
import com.yablunin.shopnstock.domain.repositories.ShoppingListHandlerRepository
import com.yablunin.shopnstock.domain.repositories.ShoppingListRepository
import com.yablunin.shopnstock.domain.repositories.UserRepository
import com.yablunin.shopnstock.domain.usecases.api.GetFlagImageUseCase
import com.yablunin.shopnstock.domain.usecases.config.LoadConfigUseCase
import com.yablunin.shopnstock.domain.usecases.config.SaveConfigUseCase
import com.yablunin.shopnstock.domain.usecases.list.AddItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.DeletePurchasedItemsUseCase
import com.yablunin.shopnstock.domain.usecases.list.GenerateQRCodeBitmapUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetCompletedItemsCountUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetItemByIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetItemByIndexUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetSizeUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetTotalPriceUseCase
import com.yablunin.shopnstock.domain.usecases.list.RemoveItemAtUseCase
import com.yablunin.shopnstock.domain.usecases.list.RemoveItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.UncheckAllItemsUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.ChangeBudgetUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.ConvertToClipboardStringUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.CopyListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GetListByIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RemoveListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RenameListUseCase
import com.yablunin.shopnstock.domain.usecases.user.ChangeUsernameUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideListRepository(): ListRepository{
        return ShoppingListRepository()
    }
    @Provides
    fun provideListHandlerRepository(): ListHandlerRepository{
        return ShoppingListHandlerRepository()
    }

    @Provides
    fun provideAddListUseCase(listHandlerRepository: ListHandlerRepository): AddListUseCase{
        return AddListUseCase(listHandlerRepository = listHandlerRepository)
    }
    @Provides
    fun provideGenerateListIdUseCase(listHandlerRepository: ListHandlerRepository): GenerateListIdUseCase{
        return GenerateListIdUseCase(listHandlerRepository = listHandlerRepository)
    }
    @Provides
    fun provideGetListIdUseCase(listHandlerRepository: ListHandlerRepository): GetListByIdUseCase{
        return GetListByIdUseCase(listHandlerRepository = listHandlerRepository)
    }
    @Provides
    fun provideRemoveListUseCase(listHandlerRepository: ListHandlerRepository): RemoveListUseCase{
        return RemoveListUseCase(listHandlerRepository = listHandlerRepository)
    }
    @Provides
    fun provideRenameListUseCase(listHandlerRepository: ListHandlerRepository): RenameListUseCase{
        return RenameListUseCase(listHandlerRepository = listHandlerRepository)
    }
    @Provides
    fun provideCopyListUseCase(listHandlerRepository: ListHandlerRepository): CopyListUseCase{
        return CopyListUseCase(listHandlerRepository = listHandlerRepository)
    }
    @Provides
    fun provideChangeBudgetUseCase(listHandlerRepository: ListHandlerRepository): ChangeBudgetUseCase{
        return ChangeBudgetUseCase(listHandlerRepository = listHandlerRepository)
    }

    @Provides
    fun provideAddItemUseCase(listRepository: ListRepository): AddItemUseCase{
        return AddItemUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideGetCompletedItemsUseCase(listRepository: ListRepository): GetCompletedItemsCountUseCase{
        return GetCompletedItemsCountUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideGetItemByIdUseCase(listRepository: ListRepository): GetItemByIdUseCase{
        return GetItemByIdUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideGetItemByIndexUseCase(listRepository: ListRepository): GetItemByIndexUseCase{
        return GetItemByIndexUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideGetSizeUseCase(listRepository: ListRepository): GetSizeUseCase{
        return GetSizeUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideRemoveItemAtUseCase(listRepository: ListRepository): RemoveItemAtUseCase{
        return RemoveItemAtUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideRemoveItemUseCase(listRepository: ListRepository): RemoveItemUseCase{
        return RemoveItemUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideConvertToClipboardStringUseCase(listRepository: ListRepository): ConvertToClipboardStringUseCase{
        return ConvertToClipboardStringUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideGenerateQRCodeBitmapUseCase(listRepository: ListRepository): GenerateQRCodeBitmapUseCase{
        return GenerateQRCodeBitmapUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideGetTotalPriceUseCase(listRepository: ListRepository): GetTotalPriceUseCase{
        return GetTotalPriceUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideDeletePurchasedItemsUseCase(listRepository: ListRepository): DeletePurchasedItemsUseCase{
        return DeletePurchasedItemsUseCase(listRepository = listRepository)
    }
    @Provides
    fun provideUncheckAllItemsUseCase(listRepository: ListRepository): UncheckAllItemsUseCase{
        return UncheckAllItemsUseCase(listRepository = listRepository)
    }

    @Provides
    fun provideSaveUserUseCase(userRepository: UserRepository): SaveUserUseCase{
        return SaveUserUseCase(userRepository = userRepository)
    }
    @Provides
    fun provideLoadUserUseCase(userRepository: UserRepository): LoadUserUseCase{
        return LoadUserUseCase(userRepository = userRepository)
    }
    @Provides
    fun provideChangeUsernameUseCase(userRepository: UserRepository): ChangeUsernameUseCase{
        return ChangeUsernameUseCase(userRepository = userRepository)
    }

    @Provides
    fun provideSaveConfigUseCase(configRepository: ConfigRepository): SaveConfigUseCase{
        return SaveConfigUseCase(configRepository = configRepository)
    }
    @Provides
    fun provideLoadConfigUseCase(configRepository: ConfigRepository): LoadConfigUseCase{
        return LoadConfigUseCase(configRepository = configRepository)
    }

    @Provides
    fun provideGetFlagImageUseCase(flagsRepository: FlagsRepository): GetFlagImageUseCase{
        return GetFlagImageUseCase(flagsRepository = flagsRepository)
    }
}