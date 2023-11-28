package com.yablunin.shopnstock.di

import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.domain.usecases.list.AddItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.GenerateQRCodeBitmapUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetCompletedItemsCountUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetSizeUseCase
import com.yablunin.shopnstock.domain.usecases.list.RemoveItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.ConvertToClipboardStringUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.CopyListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GetListByIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RemoveListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RenameListUseCase
import com.yablunin.shopnstock.domain.usecases.user.ChangeUsernameUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase
import com.yablunin.shopnstock.presentation.viewmodels.HomeViewModelFactory
import com.yablunin.shopnstock.presentation.viewmodels.LoginViewModelFactory
import com.yablunin.shopnstock.presentation.viewmodels.ShoppingListViewModelFactory
import com.yablunin.shopnstock.presentation.viewmodels.SignupViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideHomeViewModelFactory(
        auth: FirebaseAuth,
        saveUserUseCase: SaveUserUseCase,
        loadUserUseCase: LoadUserUseCase,
        addListUseCase: AddListUseCase,
        generateListIdUseCase: GenerateListIdUseCase,
        changeUsernameUseCase: ChangeUsernameUseCase
    ): HomeViewModelFactory {
        return HomeViewModelFactory(
            auth = auth,
            saveUserUseCase = saveUserUseCase,
            loadUserUseCase = loadUserUseCase,
            addListUseCase = addListUseCase,
            generateListIdUseCase = generateListIdUseCase,
            changeUsernameUseCase = changeUsernameUseCase
        )
    }

    @Provides
    fun provideLoginViewModelFactory(
        auth: FirebaseAuth,
        loadUserUseCase: LoadUserUseCase
    ): LoginViewModelFactory {
        return LoginViewModelFactory(
            auth = auth,
            loadUserUseCase = loadUserUseCase)
    }

    @Provides
    fun provideShoppingListViewModelFactory(
        saveUserUseCase: SaveUserUseCase,
        addItemUseCase: AddItemUseCase,
        removeItemUseCase: RemoveItemUseCase,
        getSizeUseCase: GetSizeUseCase,
        getCompletedItemsCountUseCase: GetCompletedItemsCountUseCase,
        removeListUseCase: RemoveListUseCase,
        getListByIdUseCase: GetListByIdUseCase,
        renameListUseCase: RenameListUseCase,
        copyListUseCase: CopyListUseCase,
        addListUseCase: AddListUseCase,
        convertToClipboardStringUseCase: ConvertToClipboardStringUseCase,
        generateQRCodeBitmapUseCase: GenerateQRCodeBitmapUseCase
    ): ShoppingListViewModelFactory{
        return ShoppingListViewModelFactory(
            saveUserUseCase = saveUserUseCase,
            addItemUseCase = addItemUseCase,
            removeItemUseCase = removeItemUseCase,
            getSizeUseCase = getSizeUseCase,
            getCompletedItemsCountUseCase = getCompletedItemsCountUseCase,
            removeListUseCase = removeListUseCase,
            getListByIdUseCase = getListByIdUseCase,
            renameListUseCase = renameListUseCase,
            copyListUseCase = copyListUseCase,
            addListUseCase = addListUseCase,
            convertToClipboardStringUseCase = convertToClipboardStringUseCase,
            generateQRCodeBitmapUseCase = generateQRCodeBitmapUseCase
        )
    }

    @Provides
    fun provideSignupViewModelFactory(
        saveUserUseCase: SaveUserUseCase,
        auth: FirebaseAuth
    ): SignupViewModelFactory{
        return SignupViewModelFactory(saveUserUseCase, auth)
    }
}