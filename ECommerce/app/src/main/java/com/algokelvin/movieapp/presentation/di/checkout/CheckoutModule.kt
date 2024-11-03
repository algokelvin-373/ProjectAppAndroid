package com.algokelvin.movieapp.presentation.di.checkout

import com.algokelvin.movieapp.domain.usecase.CheckoutUseCase
import com.algokelvin.movieapp.domain.usecase.DeleteProductInCartUseCase
import com.algokelvin.movieapp.domain.usecase.GetCartByUserIdUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateCountProductInCartUseCase
import com.algokelvin.movieapp.presentation.cart.CartViewModelFactory
import com.algokelvin.movieapp.presentation.checkout.CheckoutViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CheckoutModule {
    @CheckoutScope
    @Provides
    fun provideCheckoutViewModelFactory(
        getCartByUserIdUseCase: GetCartByUserIdUseCase,
        checkoutUseCase: CheckoutUseCase,
    ): CheckoutViewModelFactory = CheckoutViewModelFactory(
        getCartByUserIdUseCase,
        checkoutUseCase
    )
}