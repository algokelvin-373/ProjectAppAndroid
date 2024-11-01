package com.algokelvin.movieapp.presentation.di.login

import com.algokelvin.movieapp.presentation.login.LoginActivity
import dagger.Subcomponent

@LoginScope
@Subcomponent(modules = [LoginModule::class])
interface LoginSubComponent {
    fun inject(loginActivity: LoginActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginSubComponent
    }
}