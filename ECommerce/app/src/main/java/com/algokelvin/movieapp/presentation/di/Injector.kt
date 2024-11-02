package com.algokelvin.movieapp.presentation.di

import com.algokelvin.movieapp.presentation.di.login.LoginSubComponent
import com.algokelvin.movieapp.presentation.di.product.ProductCategorySubComponent
import com.algokelvin.movieapp.presentation.di.product.ProductDetailSubComponent
import com.algokelvin.movieapp.presentation.di.product.ProductSubComponent

interface Injector {
    fun createMovieSubComponent(): ProductSubComponent
    fun createProductDetailSubComponent(): ProductDetailSubComponent
    fun createLoginSubComponent(): LoginSubComponent
    fun createProductCategorySubComponent(): ProductCategorySubComponent
}