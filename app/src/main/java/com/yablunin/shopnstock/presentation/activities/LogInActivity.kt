package com.yablunin.shopnstock.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.data.DatabaseHandler
import com.yablunin.shopnstock.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firebaseAuth = FirebaseAuth.getInstance()
        dbReference = FirebaseDatabase.getInstance().getReference(DatabaseHandler.DB_USERS_NAME)

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
                                val database = FirebaseDatabase.getInstance()
                                val userRef = database.reference.child("users").child(userId)

                                DatabaseHandler.load(userRef) { user ->
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