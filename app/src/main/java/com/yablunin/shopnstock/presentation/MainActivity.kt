package com.yablunin.shopnstock.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yablunin.shopnstock.databinding.ActivityMainBinding
import com.yablunin.shopnstock.presentation.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.welcomeLoginButton.setOnClickListener {
            viewModel.startLoginActivity(this)
        }
        binding.welcomeSignupButton.setOnClickListener {
            viewModel.startSignUpActivity(this)
        }
    }
}