package com.algokelvin.movieapp.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.algokelvin.movieapp.data.model.user.Login
import com.algokelvin.movieapp.domain.usecase.GetCartByUserIdUseCase
import com.algokelvin.movieapp.domain.usecase.GetProfileUseCase
import com.algokelvin.movieapp.domain.usecase.LoginUseCase

class CartViewModel(
    private val getCartByUserIdUseCase: GetCartByUserIdUseCase,
): ViewModel() {
    fun getCartByUserId(userId: Int) = liveData {
        val listCart = getCartByUserIdUseCase.execute(userId)
        emit(listCart)
    }
}