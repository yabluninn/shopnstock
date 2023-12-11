package com.yablunin.shopnstock.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yablunin.shopnstock.domain.models.Configuration
import com.yablunin.shopnstock.domain.usecases.config.LoadConfigUseCase
import com.yablunin.shopnstock.presentation.activities.LogInActivity
import com.yablunin.shopnstock.presentation.activities.SignUpActivity

class MainViewModel(
    private val loadConfigUseCase: LoadConfigUseCase
): ViewModel() {

    var configuration: Configuration? = null

    override fun onCleared() {
        super.onCleared()
    }

    fun startLoginActivity(context: Context){
        val intent = Intent(context, LogInActivity::class.java)
        context.startActivity(intent)
    }

    fun startSignUpActivity(context: Context){
        val intent = Intent(context, SignUpActivity::class.java)
        context.startActivity(intent)
    }

    fun loadConfiguration(){
        configuration = loadConfigUseCase.execute()
    }
}