package com.algokelvin.movieapp.presentation.di.login

import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase
import com.algokelvin.movieapp.domain.usecase.LoginUseCase
import com.algokelvin.movieapp.presentation.login.LoginViewModelFactory
import com.algokelvin.movieapp.presentation.productdetail.ProductDetailViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginModule {
    @LoginScope
    @Provides
    fun provideLoginViewModelFactory(
        loginUseCase: LoginUseCase
    ): LoginViewModelFactory = LoginViewModelFactory(loginUseCase)
}