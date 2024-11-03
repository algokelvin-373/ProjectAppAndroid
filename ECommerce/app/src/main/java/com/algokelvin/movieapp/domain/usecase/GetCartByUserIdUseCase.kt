package com.algokelvin.movieapp.domain.usecase

import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.cart.CartDetail
import com.algokelvin.movieapp.domain.repository.CartRepository

class GetCartByUserIdUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(id: String): ResponseResults<CartDetail> = cartRepository.getCartByUserId(id)
}