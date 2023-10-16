package com.yablunin.shopnstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yablunin.shopnstock.user.User
import com.yablunin.shopnstock.util.DatabaseHandler

class SignUpActivity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        dbReference = FirebaseDatabase.getInstance().getReference(DatabaseHandler.DB_USERS_NAME)

        val loginLink: TextView = findViewById(R.id.sign_login_link)
        val usernameInput: EditText = findViewById(R.id.sign_username_input)
        val emailInput: EditText = findViewById(R.id.sign_email_input)
        val passwordInput: EditText = findViewById(R.id.sign_password_input)

        val signUpButton: Button = findViewById(R.id.sign_button)

        val firebaseAuth = FirebaseAuth.getInstance()

        loginLink.setOnClickListener{
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            if (!usernameInput.text.trim().isEmpty() && !emailInput.text.trim().isEmpty() && !passwordInput.text.trim().isEmpty()){
                val username: String = usernameInput.text.toString()
                val email: String = emailInput.text.toString()
                val password: String = passwordInput.text.toString()
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val currentUser = firebaseAuth.currentUser
                        if (currentUser != null){
                            val uId = currentUser.uid
                            DatabaseHandler.save(dbReference, User(uId, username, email, password))
                            val intent = Intent(this, LogInActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}