package com.algokelvin.movieapp.data.repository.cart

import com.algokelvin.movieapp.data.api.ResponseResults
import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.data.repository.cart.datasource.CartLocalDataSource
import com.algokelvin.movieapp.domain.repository.CartRepository

class CartRepositoryImpl(
    private val localCart: CartLocalDataSource,
): CartRepository {
    //override suspend fun getCartByUserId(id: String): ResponseResults<CartDetail> = getDataCart(id)

    /*private suspend fun getDataCart(id: String): ResponseResults<CartDetail> {
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
    }*/

    override suspend fun addProductInCart(cartDB: CartDB): String = addProductInCartDB(cartDB)

    override suspend fun getCartByUserId(userId: Int): ResponseResults<List<CartDB>> = getCartInDB(userId)

    override suspend fun updateCountProductInCart(
        userId: Int,
        productId: Int,
        count: Int
    ): String = updateCountProduct(userId, productId, count)

    override suspend fun deleteProductInCart(
        userId: Int,
        productId: Int
    ): String = deleteProduct(userId, productId)

    override suspend fun deleteProductForCheckout(userId: Int): String = deleteForCheckout(userId)

    private suspend fun addProductInCartDB(cartDB: CartDB): String {
        try {
            localCart.addProductInCart(cartDB)
            return "Success Add to Cart"
        } catch (e: Exception) {
            return "Failed Add to Cart - ${e.message}"
        }
    }

    private suspend fun getCartInDB(userId: Int): ResponseResults<List<CartDB>> {
        try {
            val listCart = localCart.getCart(userId)
            return ResponseResults(listCart, null)
        } catch (e: Exception) {
            return ResponseResults(null, "Failed to Show Cart - ${e.message}")
        }
    }

    private suspend fun updateCountProduct(userId: Int, productId: Int, count: Int): String {
        try {
            localCart.updateCountProductInCart(userId, productId, count)
            return "Success Update Count Product"
        } catch (e: Exception) {
            return "Failed Update Count Product"
        }
    }

    private suspend fun deleteProduct(userId: Int, productId: Int): String {
        try {
            localCart.deleteProductInCart(userId, productId)
            return "Success Delete Product In Cart"
        } catch (e: Exception) {
            return "Failed Delete Product In Cart"
        }
    }

    private suspend fun deleteForCheckout(userId: Int): String {
        try {
            localCart.deleteAllForCheckout(userId)
            return "Success Confirm Your Shopping"
        } catch (e: Exception) {
            return "Failed Confirm Your Shopping"
        }
    }
}