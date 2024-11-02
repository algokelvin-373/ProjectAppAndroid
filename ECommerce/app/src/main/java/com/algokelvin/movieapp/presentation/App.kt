package com.algokelvin.movieapp.presentation

import android.app.Application
import com.algokelvin.movieapp.BuildConfig
import com.algokelvin.movieapp.presentation.di.Injector
import com.algokelvin.movieapp.presentation.di.core.AppComponent
import com.algokelvin.movieapp.presentation.di.core.AppModule
import com.algokelvin.movieapp.presentation.di.core.DaggerAppComponent
import com.algokelvin.movieapp.presentation.di.core.NetModule
import com.algokelvin.movieapp.presentation.di.core.RemoteDataModule
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
            .remoteDataModule(RemoteDataModule(BuildConfig.API_KEY))
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
}