package com.yablunin.shopnstock.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.databinding.ActivityLogInBinding
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.presentation.toasts.ErrorToast

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private val loadUserUseCase = LoadUserUseCase(FirebaseUserRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginSignLink.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            if (!binding.loginEmailInput.text.trim().isEmpty() && !binding.loginPasswordInput.text.trim().isEmpty()){
                val email: String = binding.loginEmailInput.text.toString()
                val password: String = binding.loginPasswordInput.text.toString()
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = firebaseAuth.currentUser

                            if (currentUser != null) {
                                val userId = currentUser.uid

                                loadUserUseCase.execute(userId){ user ->
                                    if (user != null) {
                                        val intent = Intent(this, HomeActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        val errorToast = ErrorToast(
                                            this,
                                            getString(R.string.error_user_not_found),
                                            Toast.LENGTH_LONG
                                        )
                                        errorToast.show()
                                    }
                                }
                            }
                        } else {
                            if(task.exception is FirebaseAuthInvalidCredentialsException){
                                val exception = task.exception as FirebaseAuthInvalidCredentialsException
                                if (exception.errorCode == "ERROR_INVALID_EMAIL"){
                                    val errorToast = ErrorToast(
                                        this,
                                        getString(R.string.error_invalid_email),
                                        Toast.LENGTH_LONG
                                    )
                                    errorToast.show()
                                }
                                else if (exception.errorCode == "ERROR_WRONG_PASSWORD"){
                                    val errorToast = ErrorToast(
                                        this,
                                        getString(R.string.error_wrong_password),
                                        Toast.LENGTH_LONG
                                    )
                                    errorToast.show()
                                }
                            }
                            else if (task.exception is FirebaseAuthInvalidUserException){
                                val exception = task.exception as FirebaseAuthInvalidUserException
                                if (exception.errorCode == "ERROR_USER_NOT_FOUND"){
                                    val errorToast = ErrorToast(
                                        this,
                                        getString(R.string.error_user_not_found),
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