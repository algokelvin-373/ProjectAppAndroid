package com.algokelvin.shoppingyuk.presentation.di.core

import android.content.Context
import com.algokelvin.shoppingyuk.presentation.di.cart.CartSubComponent
import com.algokelvin.shoppingyuk.presentation.di.checkout.CheckoutSubComponent
import com.algokelvin.shoppingyuk.presentation.di.home.HomeSubComponent
import com.algokelvin.shoppingyuk.presentation.di.login.LoginSubComponent
import com.algokelvin.shoppingyuk.presentation.di.product.ProductCategorySubComponent
import com.algokelvin.shoppingyuk.presentation.di.product.ProductDetailSubComponent
import com.algokelvin.shoppingyuk.presentation.di.product.ProductSubComponent
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