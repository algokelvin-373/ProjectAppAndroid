package com.algokelvin.movieapp.data.repository.cart.datasourceImpl

import com.algokelvin.movieapp.data.db.CartDao
import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.data.repository.cart.datasource.CartLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartLocalDataSourceImpl(
    private val cartDao: CartDao,
): CartLocalDataSource {

    override suspend fun getCart(userId: Int): List<CartDB> = cartDao.getCart(userId)

    override suspend fun addProductInCart(cartDB: CartDB) {
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.addProductInCart(cartDB)
        }
    }

    override suspend fun updateCountProductInCart(userId: Int, productId: Int, count: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.updateCountProductInCart(userId, productId, count)
        }
    }

    override suspend fun deleteProductInCart(userId: Int, productId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.deleteProductInCart(userId, productId)
        }
    }
}