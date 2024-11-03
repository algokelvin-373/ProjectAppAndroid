package com.algokelvin.movieapp.presentation.di.cart

import com.algokelvin.movieapp.domain.usecase.GetCartByUserIdUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase
import com.algokelvin.movieapp.domain.usecase.GetProfileUseCase
import com.algokelvin.movieapp.domain.usecase.LoginUseCase
import com.algokelvin.movieapp.presentation.cart.CartViewModelFactory
import com.algokelvin.movieapp.presentation.login.LoginViewModelFactory
import com.algokelvin.movieapp.presentation.productdetail.ProductDetailViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CartModule {
    @CartScope
    @Provides
    fun provideCartViewModelFactory(
        getCartByUserIdUseCase: GetCartByUserIdUseCase,
    ): CartViewModelFactory = CartViewModelFactory(getCartByUserIdUseCase)
}