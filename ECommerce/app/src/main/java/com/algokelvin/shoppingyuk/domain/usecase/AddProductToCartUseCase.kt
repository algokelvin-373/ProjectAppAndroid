package com.algokelvin.shoppingyuk.domain.usecase

import com.algokelvin.shoppingyuk.data.model.cart.CartDB
import com.algokelvin.shoppingyuk.domain.repository.CartRepository

class AddProductToCartUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(cartDB: CartDB): String = cartRepository.addProductInCart(cartDB)
}