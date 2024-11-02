package com.algokelvin.movieapp.presentation.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.databinding.ActivityLoginBinding
import com.algokelvin.movieapp.presentation.di.Injector
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: LoginViewModelFactory

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        (application as Injector).createLoginSubComponent()
            .inject(this)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class]

        initLogin()
    }

    private fun initLogin() {
        binding.btnLogin.setOnClickListener {
            val username = binding.usernameData.text.toString()
            val password = binding.passwordData.text.toString()
            val login = Login(username, password)

            loginViewModel.login(login).observe(this, Observer {
                if (it != null) {
                    val token = it
                    Toast.makeText(this, token.token, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}