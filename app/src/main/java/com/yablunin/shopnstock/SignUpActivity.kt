package com.yablunin.shopnstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val loginLink: TextView = findViewById(R.id.sign_login_link)

        loginLink.setOnClickListener{
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}