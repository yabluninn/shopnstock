package com.yablunin.shopnstock.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.presentation.activities.HomeActivity
import com.yablunin.shopnstock.presentation.activities.SignUpActivity
import com.yablunin.shopnstock.presentation.toasts.ErrorToast

class LoginViewModel(
    private val auth: FirebaseAuth,
    private val loadUserUseCase: LoadUserUseCase
): ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

    fun startSignUpActivity(context: Context){
        val intent = Intent(context, SignUpActivity::class.java)
        context.startActivity(intent)
    }

    fun signInWithEmailAndPassword(email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        val userId = currentUser.uid
                        loadUser(userId, context)
                    }
                } else {
                    if(task.exception is FirebaseAuthInvalidCredentialsException){
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        if (exception.errorCode == "ERROR_INVALID_EMAIL"){
                             ErrorToast(
                                context,
                                message = context.getString(R.string.error_invalid_email),
                                duration = Toast.LENGTH_LONG,
                                position = Gravity.TOP
                            ).show()
                        } else if (exception.errorCode == "ERROR_WRONG_PASSWORD"){
                            ErrorToast(
                                context,
                                message = context.getString(R.string.error_wrong_password),
                                duration = Toast.LENGTH_LONG,
                                position = Gravity.TOP
                            ).show()
                        }
                    } else if (task.exception is FirebaseAuthInvalidUserException){
                        val exception = task.exception as FirebaseAuthInvalidUserException
                        if (exception.errorCode == "ERROR_USER_NOT_FOUND"){
                            ErrorToast(
                                context,
                                message = context.getString(R.string.error_user_not_found),
                                duration = Toast.LENGTH_LONG,
                                position = Gravity.TOP
                            ).show()
                        }
                    }
                }
            }
    }

    private fun loadUser(userId: String, context: Context){
        loadUserUseCase.execute(userId){ user ->
            if (user != null) {
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
            } else {
                ErrorToast(
                    context,
                    message = context.getString(R.string.error_user_not_found),
                    duration = Toast.LENGTH_LONG,
                    position = Gravity.TOP
                ).show()
            }
        }
    }
}