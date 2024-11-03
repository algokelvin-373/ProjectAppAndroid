package com.algokelvin.movieapp.presentation.di.core

import android.content.Context
import com.algokelvin.movieapp.presentation.di.cart.CartSubComponent
import com.algokelvin.movieapp.presentation.di.checkout.CheckoutSubComponent
import com.algokelvin.movieapp.presentation.di.home.HomeSubComponent
import com.algokelvin.movieapp.presentation.di.login.LoginSubComponent
import com.algokelvin.movieapp.presentation.di.product.ProductCategorySubComponent
import com.algokelvin.movieapp.presentation.di.product.ProductDetailSubComponent
import com.algokelvin.movieapp.presentation.di.product.ProductSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    ProductSubComponent::class,
    ProductDetailSubComponent::class,
    LoginSubComponent::class,
    ProductCategorySubComponent::class,
    HomeSubComponent::class,
    CartSubComponent::class,
    CheckoutSubComponent::class,
])
class AppModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return context.applicationContext
    }
}