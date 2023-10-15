package com.yablunin.shopnstock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.yablunin.shopnstock.kotlinpractice.KotlinLearn

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton: Button = findViewById(R.id.welcome_login_button)
        val signupButton: Button = findViewById(R.id.welcome_signup_button)

        loginButton.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
        signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        Log.d(KotlinLearn.DEBUG_TAG, "Sum is: ${KotlinLearn.sumOf(KotlinLearn.sumArray)}")
        KotlinLearn.getWeekDay(1)
        KotlinLearn.getWeekDay(4)

        KotlinLearn.loadFile(this)

        KotlinLearn.loadFile()

        KotlinLearn.unloadFile()

        KotlinLearn.launchCoroutines()
    }
}