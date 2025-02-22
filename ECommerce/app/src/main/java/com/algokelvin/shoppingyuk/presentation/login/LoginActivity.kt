package com.algokelvin.shoppingyuk.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.shoppingyuk.R
import com.algokelvin.shoppingyuk.data.model.user.Login
import com.algokelvin.shoppingyuk.databinding.ActivityLoginBinding
import com.algokelvin.shoppingyuk.presentation.di.Injector
import com.algokelvin.shoppingyuk.presentation.home.HomeActivity
import com.algokelvin.shoppingyuk.utils.EncryptLocal
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: LoginViewModelFactory

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkStatusLogin()
    }

    private fun checkStatusLogin() {
        val getToken = EncryptLocal.getToken(this)
        if (getToken != null) {
            Toast.makeText(this, "Anda sudah Login", Toast.LENGTH_SHORT).show()
            val intentToHome = Intent(this, HomeActivity::class.java)
            startActivity(intentToHome)
            finish()
        } else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

            (application as Injector).createLoginSubComponent()
                .inject(this)
            loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class]

            initLogin()
        }
    }

    private fun initLogin() {
        binding.btnLogin.setOnClickListener {
            binding.materialCardView.visibility = View.GONE
            binding.layoutLoading.visibility = View.VISIBLE

            val username = binding.usernameData.text.toString()
            val password = binding.passwordData.text.toString()
            val login = Login(username, password)

            loginViewModel.login(login).observe(this) { token ->
                if (token != null) {
                    if (token.errorMessage == null) {
                        val tokenStr = token.data?.token
                        tokenStr?.let { it1 -> EncryptLocal.saveToken(this, it1) }
                        loginViewModel.getProfile(login).observe(this) { profile ->
                            Toast.makeText(this, "Success Login", Toast.LENGTH_SHORT).show()
                            val profileId = profile.data?.id
                            profileId?.let { it1 -> EncryptLocal.saveIdProfile(this, it1) }
                            val intentToHome = Intent(this, HomeActivity::class.java)
                            intentToHome.putExtra("PROFILE_ID", profileId)
                            startActivity(intentToHome)
                            finish()
                        }
                    } else {
                        Toast.makeText(this, token.errorMessage, Toast.LENGTH_SHORT).show()
                        binding.materialCardView.visibility = View.VISIBLE
                        binding.layoutLoading.visibility = View.GONE
                    }
                }
            }
        }
    }

}