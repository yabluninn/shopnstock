package com.yablunin.shopnstock.presentation.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.yablunin.shopnstock.presentation.activities.LogInActivity
import com.yablunin.shopnstock.presentation.activities.SignUpActivity

class MainViewModel: ViewModel() {


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
}