package com.algokelvin.movieapp.data.repository.cart

import android.util.Log
import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.cart.Cart
import com.algokelvin.movieapp.data.model.cart.CartDetail
import com.algokelvin.movieapp.data.model.cart.ProductCountInCart
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.data.repository.cart.datasource.CartRemoteDataSource
import com.algokelvin.movieapp.data.repository.product.datasource.ProductLocalDataSource
import com.algokelvin.movieapp.domain.repository.CartRepository

class CartRepositoryImpl(
    private val remote: CartRemoteDataSource,
    private val local: ProductLocalDataSource,
): CartRepository {
    override suspend fun getCartByUserId(id: String): ResponseResults<CartDetail> = getDataCart(id)

    private suspend fun getDataCart(id: String): ResponseResults<CartDetail> {
        try {
            val response = remote.getCartByUserid(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val listCart = body[0].products
                    val listProductInCart = ArrayList<ProductCountInCart>()
                    for (product in listCart) {
                        val productId = product.productId
                        val productCount = product.quantity
                        val productResult = local.getProductByIdFromDB(productId)
                        val productCountInCart = ProductCountInCart(
                            productResult.id,
                            productResult.title,
                            productResult.price,
                            productResult.description,
                            productResult.category,
                            productResult.image,
                            productCount
                        )
                        listProductInCart.add(productCountInCart)
                    }
                    Log.i("ALGOKELVIN_DEBUG", "Done List Cart")
                    val cartDetail = CartDetail(
                        body[0].id,
                        body[0].userId,
                        listProductInCart
                    )
                    return ResponseResults(cartDetail, null)
                } else {
                    return ResponseResults(null, "Value Body is NULL")
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                return ResponseResults(null, errorMessage)
            }
        } catch (e: Exception) {
            return ResponseResults(null, e.message)
        }
    }
}