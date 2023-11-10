package com.yablunin.shopnstock.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yablunin.shopnstock.databinding.ActivityMainBinding
import com.yablunin.shopnstock.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.welcomeLoginButton.setOnClickListener {
            viewModel.startLoginActivity(this)
        }
        binding.welcomeSignupButton.setOnClickListener {
            viewModel.startSignUpActivity(this)
        }
    }
}