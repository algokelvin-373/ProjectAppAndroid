package com.algokelvin.movieapp.presentation.di.login

import com.algokelvin.movieapp.domain.usecase.GetProfileUseCase
import com.algokelvin.movieapp.domain.usecase.LoginUseCase
import com.algokelvin.movieapp.presentation.login.LoginViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginModule {
    @LoginScope
    @Provides
    fun provideLoginViewModelFactory(
        loginUseCase: LoginUseCase,
        getProfileUseCase: GetProfileUseCase,
    ): LoginViewModelFactory = LoginViewModelFactory(loginUseCase, getProfileUseCase)
}