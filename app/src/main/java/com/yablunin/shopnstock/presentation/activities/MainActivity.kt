package com.yablunin.shopnstock.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.app.App
import com.yablunin.shopnstock.databinding.ActivityMainBinding
import com.yablunin.shopnstock.domain.enums.AppTheme
import com.yablunin.shopnstock.domain.models.Configuration
import com.yablunin.shopnstock.domain.util.Initiable
import com.yablunin.shopnstock.presentation.viewmodels.MainViewModel
import com.yablunin.shopnstock.presentation.viewmodels.MainViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Initiable {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var configuration: Configuration
    private var isConfigurationInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }

    override fun init() {
        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.loadConfiguration()
        configuration = viewModel.configuration!!
        isConfigurationInitialized = true
        setAppTheme()


        binding.welcomeLoginButton.setOnClickListener {
            viewModel.startLoginActivity(this)
        }
        binding.welcomeSignupButton.setOnClickListener {
            viewModel.startSignUpActivity(this)
        }
    }

    private fun setAppTheme(){
        if (isConfigurationInitialized){
            when(configuration.theme){
                AppTheme.THEME_LIGHT ->{
                    setTheme(R.style.AppThemeLight)
                }
                AppTheme.THEME_DARK ->{
                    setTheme(R.style.AppThemeDark)
                }
            }
        }
    }
}