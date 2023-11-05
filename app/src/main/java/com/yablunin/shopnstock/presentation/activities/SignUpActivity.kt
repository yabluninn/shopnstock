package com.yablunin.shopnstock.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.databinding.ActivitySignUpBinding
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase
import com.yablunin.shopnstock.presentation.toasts.ErrorToast

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var saveUserUseCase: SaveUserUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        firebaseAuth = FirebaseAuth.getInstance()
        saveUserUseCase = SaveUserUseCase(FirebaseUserRepository())

        binding.signLoginLink.setOnClickListener{
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        binding.signButton.setOnClickListener {
            if (!binding.signUsernameInput.text.trim().isEmpty() && !binding.signEmailInput.text.trim().isEmpty() && !binding.signPasswordInput.text.trim().isEmpty()){
                val username: String = binding.signUsernameInput.text.toString()
                val email: String = binding.signEmailInput.text.toString()
                val password: String = binding.signPasswordInput.text.toString()
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val currentUser = firebaseAuth.currentUser
                        if (currentUser != null){
                            val uId = currentUser.uid
                            val user = User(
                                uId,
                                username,
                                email,
                                password
                            )
                            saveUserUseCase.execute(user)
                            val intent = Intent(this, LogInActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else{
                        if (it.exception is FirebaseAuthUserCollisionException){
                            val exception = it.exception as FirebaseAuthUserCollisionException
                            if (exception.errorCode == "ERROR_EMAIL_ALREADY_IN_USE"){
                                val errorToast = ErrorToast(
                                    this,
                                    getString(R.string.error_email_already_in_use),
                                    Toast.LENGTH_LONG
                                )
                                errorToast.show()
                            }
                        }
                        else if(it.exception is FirebaseAuthInvalidCredentialsException){
                            val exception = it.exception as FirebaseAuthInvalidCredentialsException
                            if (exception.errorCode == "ERROR_INVALID_EMAIL"){
                                val errorToast = ErrorToast(
                                    this,
                                    getString(R.string.error_email_already_in_use),
                                    Toast.LENGTH_LONG
                                )
                                errorToast.show()
                            }
                        }
                        else if(it.exception is FirebaseAuthWeakPasswordException){
                            val exception = it.exception as FirebaseAuthWeakPasswordException
                            if (exception.errorCode == "ERROR_WEAK_PASSWORD"){
                                val errorToast = ErrorToast(
                                    this,
                                    getString(R.string.error_weak_password),
                                    Toast.LENGTH_LONG
                                )
                                errorToast.show()
                            }
                        }
                    }
                }
            }
        }
    }
}