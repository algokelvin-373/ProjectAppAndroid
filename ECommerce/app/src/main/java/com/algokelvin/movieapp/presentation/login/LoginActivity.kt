package com.algokelvin.movieapp.presentation.login

import android.content.Intent
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
import com.algokelvin.movieapp.presentation.product.ProductActivity
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

            loginViewModel.login(login).observe(this, Observer { token ->
                if (token != null) {
                    if (token.errorMessage == null) {
                        Toast.makeText(this, token.data?.token.toString(), Toast.LENGTH_SHORT).show()
                        loginViewModel.getProfile(login).observe(this, Observer {  profile ->
                            Toast.makeText(this, profile.data?.email, Toast.LENGTH_SHORT).show()
                            val intentToHome = Intent(this, ProductActivity::class.java)
                            startActivity(intentToHome)
                        })
                    } else {
                        Toast.makeText(this, token.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

}