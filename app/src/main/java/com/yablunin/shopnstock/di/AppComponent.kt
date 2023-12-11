package com.yablunin.shopnstock.di

import androidx.appcompat.app.AppCompatActivity
import com.yablunin.shopnstock.presentation.activities.HomeActivity
import com.yablunin.shopnstock.presentation.activities.LogInActivity
import com.yablunin.shopnstock.presentation.activities.MainActivity
import com.yablunin.shopnstock.presentation.activities.ShoppingListActivity
import com.yablunin.shopnstock.presentation.activities.SignUpActivity
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(logInActivity: LogInActivity)
    fun inject(shoppingListActivity: ShoppingListActivity)
    fun inject(signUpActivity: SignUpActivity)
}