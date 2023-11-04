package com.yablunin.shopnstock.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.databinding.ActivitySignUpBinding
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

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
                            val user = com.yablunin.shopnstock.domain.models.User(
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
                }
            }
        }
    }
}