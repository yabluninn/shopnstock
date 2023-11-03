package com.yablunin.shopnstock.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.data.repository.FirebaseUserRepository
import com.yablunin.shopnstock.databinding.ActivityLogInBinding
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase

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
                                        // Пользователь не найден в базе данных
                                    }
                                }
                            }
                        } else {
                            // Ошибка аутентификации
                        }
                    }
            }
        }
    }
}