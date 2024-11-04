package com.algokelvin.shoppingyuk.presentation.di.core

import com.algokelvin.shoppingyuk.presentation.di.cart.CartSubComponent
import com.algokelvin.shoppingyuk.presentation.di.checkout.CheckoutSubComponent
import com.algokelvin.shoppingyuk.presentation.di.home.HomeSubComponent
import com.algokelvin.shoppingyuk.presentation.di.login.LoginSubComponent
import com.algokelvin.shoppingyuk.presentation.di.product.ProductCategorySubComponent
import com.algokelvin.shoppingyuk.presentation.di.product.ProductDetailSubComponent
import com.algokelvin.shoppingyuk.presentation.di.product.ProductSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetModule::class,
    DatabaseModule::class,
    UseCaseModule::class,
    RepositoryModule::class,
    RemoteDataModule::class,
    LocalDataModule::class,
    CacheDataModule::class
])
interface AppComponent {
    fun movieSubComponent(): ProductSubComponent.Factory
    fun productDetailSubComponent(): ProductDetailSubComponent.Factory
    fun loginSubComponent(): LoginSubComponent.Factory
    fun productCategorySubComponent(): ProductCategorySubComponent.Factory
    fun homeSubComponent(): HomeSubComponent.Factory
    fun cartSubComponent(): CartSubComponent.Factory
    fun checkoutSubComponent(): CheckoutSubComponent.Factory
}