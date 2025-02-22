package com.algokelvin.shoppingyuk.domain.usecase

import com.algokelvin.shoppingyuk.data.api.ResponseResults
import com.algokelvin.shoppingyuk.data.model.cart.CartDB
import com.algokelvin.shoppingyuk.domain.repository.CartRepository

class GetCartByUserIdUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(userId: Int): ResponseResults<List<CartDB>> = cartRepository.getCartByUserId(userId)
}