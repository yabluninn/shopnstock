package com.yablunin.shopnstock.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yablunin.shopnstock.databinding.ActivityLogInBinding
import com.yablunin.shopnstock.domain.util.Initiable
import com.yablunin.shopnstock.presentation.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInActivity : AppCompatActivity(), Initiable {

    private lateinit var binding: ActivityLogInBinding
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    override fun init(){
        binding.loginSignLink.setOnClickListener{
            viewModel.startSignUpActivity(this)
        }

        binding.loginButton.setOnClickListener {
            if (!binding.loginEmailInput.text.trim().isEmpty() && !binding.loginPasswordInput.text.trim().isEmpty()){
                val email: String = binding.loginEmailInput.text.toString()
                val password: String = binding.loginPasswordInput.text.toString()
                viewModel.signInWithEmailAndPassword(
                    email = email,
                    password = password,
                    context = this
                )
            }
        }
    }
}