package com.yablunin.shopnstock.di

import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
    @Provides
    fun provideFirebaseUserRepository(): UserRepository{
        return FirebaseUserRepository()
    }
}