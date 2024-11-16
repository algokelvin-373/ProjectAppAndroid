package com.algokelvin.movieapp.presentation

import android.app.Application
import com.algokelvin.movieapp.BuildConfig
import com.algokelvin.movieapp.presentation.di.Injector
import com.algokelvin.movieapp.presentation.di.cart.CartSubComponent
import com.algokelvin.movieapp.presentation.di.checkout.CheckoutSubComponent
import com.algokelvin.movieapp.presentation.di.core.AppComponent
import com.algokelvin.movieapp.presentation.di.core.AppModule
import com.algokelvin.movieapp.presentation.di.core.DaggerAppComponent
import com.algokelvin.movieapp.presentation.di.core.NetModule
import com.algokelvin.movieapp.presentation.di.core.RemoteDataModule
import com.algokelvin.movieapp.presentation.di.home.HomeSubComponent
import com.algokelvin.movieapp.presentation.di.login.LoginSubComponent
import com.algokelvin.movieapp.presentation.di.product.ProductCategorySubComponent
import com.algokelvin.movieapp.presentation.di.product.ProductDetailSubComponent
import com.algokelvin.movieapp.presentation.di.product.ProductSubComponent

class App: Application(), Injector {
    private lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .remoteDataModule(RemoteDataModule())
            .build()
    }

    override fun createMovieSubComponent(): ProductSubComponent {
        return appComponent.movieSubComponent().create()
    }

    override fun createProductDetailSubComponent(): ProductDetailSubComponent {
        return appComponent.productDetailSubComponent().create()
    }

    override fun createLoginSubComponent(): LoginSubComponent {
        return appComponent.loginSubComponent().create()
    }

    override fun createProductCategorySubComponent(): ProductCategorySubComponent {
        return appComponent.productCategorySubComponent().create()
    }

    override fun createHomeSubComponent(): HomeSubComponent {
        return appComponent.homeSubComponent().create()
    }

    override fun createCartSubComponent(): CartSubComponent {
        return appComponent.cartSubComponent().create()
    }

    override fun createCheckoutSubComponent(): CheckoutSubComponent {
        return appComponent.checkoutSubComponent().create()
    }
}