package com.algokelvin.shoppingyuk.presentation.di.login

import com.algokelvin.shoppingyuk.presentation.login.LoginActivity
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