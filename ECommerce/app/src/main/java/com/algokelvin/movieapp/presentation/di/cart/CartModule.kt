package com.algokelvin.movieapp.presentation.di.cart

import com.algokelvin.movieapp.domain.usecase.DeleteProductInCartUseCase
import com.algokelvin.movieapp.domain.usecase.GetCartByUserIdUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateCountProductInCartUseCase
import com.algokelvin.movieapp.presentation.cart.CartViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CartModule {
    @CartScope
    @Provides
    fun provideCartViewModelFactory(
        getCartByUserIdUseCase: GetCartByUserIdUseCase,
        updateCountProductInCartUseCase: UpdateCountProductInCartUseCase,
        deleteProductInCartUseCase: DeleteProductInCartUseCase,
    ): CartViewModelFactory = CartViewModelFactory(
        getCartByUserIdUseCase,
        updateCountProductInCartUseCase,
        deleteProductInCartUseCase
    )
}