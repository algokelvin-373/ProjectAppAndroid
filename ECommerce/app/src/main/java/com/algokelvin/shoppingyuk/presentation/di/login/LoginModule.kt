package com.algokelvin.shoppingyuk.presentation.di.login

import com.algokelvin.shoppingyuk.domain.usecase.GetProfileUseCase
import com.algokelvin.shoppingyuk.domain.usecase.LoginUseCase
import com.algokelvin.shoppingyuk.presentation.login.LoginViewModelFactory
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