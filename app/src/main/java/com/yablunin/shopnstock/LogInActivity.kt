package com.yablunin.shopnstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yablunin.shopnstock.user.User
import com.yablunin.shopnstock.util.DatabaseHandler

class LogInActivity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val signupLink: TextView = findViewById(R.id.login_sign_link)
        val emailInput: EditText = findViewById(R.id.login_email_input)
        val passwordInput: EditText = findViewById(R.id.login_password_input)
        val logInButton: Button = findViewById(R.id.login_button)

        val firebaseAuth = FirebaseAuth.getInstance()
        dbReference = FirebaseDatabase.getInstance().getReference(DatabaseHandler.DB_USERS_NAME)

        signupLink.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        logInButton.setOnClickListener {

            if (!emailInput.text.trim().isEmpty() && !passwordInput.text.trim().isEmpty()){
                val email: String = emailInput.text.toString()
                val password: String = passwordInput.text.toString()
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