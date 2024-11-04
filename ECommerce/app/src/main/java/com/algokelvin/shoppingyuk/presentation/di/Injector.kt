package com.algokelvin.shoppingyuk.presentation.di

import com.algokelvin.shoppingyuk.presentation.di.cart.CartSubComponent
import com.algokelvin.shoppingyuk.presentation.di.checkout.CheckoutSubComponent
import com.algokelvin.shoppingyuk.presentation.di.home.HomeSubComponent
import com.algokelvin.shoppingyuk.presentation.di.login.LoginSubComponent
import com.algokelvin.shoppingyuk.presentation.di.product.ProductCategorySubComponent
import com.algokelvin.shoppingyuk.presentation.di.product.ProductDetailSubComponent
import com.algokelvin.shoppingyuk.presentation.di.product.ProductSubComponent

interface Injector {
    fun createMovieSubComponent(): ProductSubComponent
    fun createProductDetailSubComponent(): ProductDetailSubComponent
    fun createLoginSubComponent(): LoginSubComponent
    fun createProductCategorySubComponent(): ProductCategorySubComponent
    fun createHomeSubComponent(): HomeSubComponent
    fun createCartSubComponent(): CartSubComponent
    fun createCheckoutSubComponent(): CheckoutSubComponent
}