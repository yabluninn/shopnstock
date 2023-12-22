package com.yablunin.shopnstock.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase
import com.yablunin.shopnstock.presentation.activities.LogInActivity
import com.yablunin.shopnstock.presentation.toasts.ErrorToast

class SignupViewModel(
    private val saveUserUseCase: SaveUserUseCase,
    private val auth: FirebaseAuth
): ViewModel() {

    fun showLoginActivity(context: Context){
        val intent = Intent(context, LogInActivity::class.java)
        context.startActivity(intent)
    }
    fun signUp(username: String, email: String, password: String, context: Context){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                val currentUser = auth.currentUser
                if (currentUser != null){
                    val uId = currentUser.uid
                    val user = User(
                        uId,
                        username,
                        email,
                        password
                    )
                    saveUserUseCase.execute(user)
                }
            } else{
                if (it.exception is FirebaseAuthUserCollisionException){
                    val exception = it.exception as FirebaseAuthUserCollisionException
                    if (exception.errorCode == "ERROR_EMAIL_ALREADY_IN_USE"){
                        ErrorToast(
                            context,
                            message = context.getString(R.string.error_email_already_in_use),
                            duration = Toast.LENGTH_LONG,
                            position = Gravity.TOP
                        ).show()
                    }
                }
                else if(it.exception is FirebaseAuthInvalidCredentialsException){
                    val exception = it.exception as FirebaseAuthInvalidCredentialsException
                    if (exception.errorCode == "ERROR_INVALID_EMAIL"){
                        ErrorToast(
                            context,
                            message = context.getString(R.string.error_email_already_in_use),
                            duration = Toast.LENGTH_LONG,
                            position = Gravity.TOP
                        ).show()
                    }
                    else if (exception.errorCode == "ERROR_WEAK_PASSWORD"){
                        ErrorToast(
                            context,
                            message = context.getString(R.string.error_weak_password),
                            duration = Toast.LENGTH_LONG,
                            position = Gravity.TOP
                        ).show()
                    }
                }
            }
        }
    }
}