package com.yablunin.shopnstock.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yablunin.shopnstock.databinding.ActivitySignUpBinding
import com.yablunin.shopnstock.domain.util.Initiable
import com.yablunin.shopnstock.presentation.viewmodels.SignupViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity(), Initiable {

    private lateinit var binding: ActivitySignUpBinding

    private val viewModel by viewModel<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    override fun init(){
        binding.signLoginLink.setOnClickListener{
            viewModel.showLoginActivity(this)
        }

        binding.signButton.setOnClickListener {
            if (!binding.signUsernameInput.text.trim().isEmpty() && !binding.signEmailInput.text.trim().isEmpty() && !binding.signPasswordInput.text.trim().isEmpty()){
                val username: String = binding.signUsernameInput.text.toString()
                val email: String = binding.signEmailInput.text.toString()
                val password: String = binding.signPasswordInput.text.toString()
                val context = this
                binding.signButton.isEnabled = false

                lifecycleScope.launch {
                    try {
                        viewModel.signUp(username, email, password, context)
                        viewModel.showLoginActivity(context)
                    } catch (e: Exception) {

                    } finally {
                        binding.signButton.isEnabled = true
                    }
                }
            }
        }
    }
}