package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.domain.repository.CartRepository

class AddProductToCartUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(cartDB: CartDB): String = cartRepository.addProductInCart(cartDB)
}