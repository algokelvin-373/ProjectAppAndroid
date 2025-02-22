package com.algokelvin.loginplay.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.algokelvin.loginplay.api.request.LoginRequest
import com.algokelvin.loginplay.databinding.ActivityMainBinding
import com.algokelvin.loginplay.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var isEmailValid = false
    private var isPasswordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        binding.apply {
            tilLoginEmail.editText?.doOnTextChanged { data, _, _, _ ->
                validateEmailData(data.toString())
            }
            tilLoginPw.editText?.doOnTextChanged { data, _, _, _ ->
                validatePasswordData(data.toString())
            }
            btnLoginMasuk.setOnClickListener {
                doLoginAction()
            }
        }
    }

    private fun validateEmailData(email: String) {
        binding.apply {
            isEmailValid = mainViewModel.validateEmail(email)
            if (isEmailValid) {
                tilLoginEmail.isErrorEnabled = false
            } else {
                tilLoginPw.isErrorEnabled = true
                btnLoginMasuk.isEnabled = false
                tilLoginEmail.error = "Email harus sesuai format penulisan"
            }
            btnLoginMasuk.isEnabled = isEmailValid && isPasswordValid
        }
    }

    private fun validatePasswordData(password: String) {
        binding.apply {
            isPasswordValid = mainViewModel.validatePassword(password)
            if (isPasswordValid) {
                binding.tilLoginPw.isErrorEnabled = false
            } else {
                binding.btnLoginMasuk.isEnabled = false
            }
            btnLoginMasuk.isEnabled = isEmailValid && isPasswordValid
        }
    }

    private fun doLoginAction() {
        binding.apply {
            val email = tilLoginEmail.editText?.text.toString()
            val password = tilLoginPw.editText?.text.toString()
            val loginRequest = LoginRequest(email, password)
            mainViewModel.doLogin(loginRequest).observe(this@MainActivity, Observer { result ->
                result.fold(
                    onSuccess = { response ->
                        Toast.makeText(this@MainActivity, "Success Login: "+ response?.token, Toast.LENGTH_LONG).show()
                    }, onFailure = { error ->
                        Toast.makeText(this@MainActivity, "Gagal Login: "+ error.message.toString(), Toast.LENGTH_LONG).show()
                    })
            })
        }
    }
}