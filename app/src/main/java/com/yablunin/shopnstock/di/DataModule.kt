package com.yablunin.shopnstock.di

import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.domain.repositories.UserRepository
import org.koin.dsl.module

val dataModule = module{

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<UserRepository>{
         FirebaseUserRepository()
    }

}