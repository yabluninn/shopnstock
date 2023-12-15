package com.yablunin.shopnstock.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.data.clients.RetrofitFlagsClient
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.data.repositories.FlagsApiRepository
import com.yablunin.shopnstock.domain.models.Configuration
import com.yablunin.shopnstock.data.repositories.SharedPrefsConfigRepository
import com.yablunin.shopnstock.data.services.FlagsApiService
import com.yablunin.shopnstock.domain.repositories.ConfigRepository
import com.yablunin.shopnstock.domain.repositories.FlagsRepository
import com.yablunin.shopnstock.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(private val context: Context) {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }
    @Provides
    fun provideFirebaseUserRepository(): UserRepository{
        return FirebaseUserRepository()
    }

    @Provides
    fun provideSharedPreferences(): SharedPreferences{
        return context.getSharedPreferences(Configuration.CONFIG_SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideSharedPrefsConfigRepository(sharedPreferences: SharedPreferences): ConfigRepository{
        return SharedPrefsConfigRepository(sharedPreferences = sharedPreferences)
    }

    @Provides
    fun provideFlagsApiService(): FlagsApiService{
        return RetrofitFlagsClient.flagsApiService
    }
    @Provides
    fun provideFlagsApiRepository(flagsApiService: FlagsApiService): FlagsRepository{
        return FlagsApiRepository(flagsApiService = flagsApiService)
    }
}