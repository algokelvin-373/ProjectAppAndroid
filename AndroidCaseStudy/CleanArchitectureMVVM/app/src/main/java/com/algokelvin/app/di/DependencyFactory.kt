package com.algokelvin.app.di

import android.content.Context
import com.algokelvin.app.domain.GetProductByIdUseCase
import com.algokelvin.app.domain.GetProductsUseCase
import com.algokelvin.app.model.datasource.local.ProductDatabase
import com.algokelvin.app.model.datasource.remote.RemoteProductFactory
import com.algokelvin.app.model.repository.ProductRepository
import com.algokelvin.app.model.repository.ProductRepositoryImpl
import com.algokelvin.app.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DependencyFactory {

    fun buildMainViewModel(context: Context): MainViewModel {
        val repository = provideProductRepository(context)
        val dispatcherIo = Dispatchers.IO

        val provideProductsUseCase = provideProductsUseCase(repository, dispatcherIo)
        val provideGetProductByIdUseCase = provideGetProductByIdUseCase(repository, dispatcherIo)
        return provideMainViewModel(provideProductsUseCase, provideGetProductByIdUseCase)
    }

    private fun provideMainViewModel(
        getProductsUseCase: GetProductsUseCase,
        getProductByIdUseCase: GetProductByIdUseCase
    ) = MainViewModel(getProductByIdUseCase, getProductsUseCase)

    private fun provideProductsUseCase(
        repository: ProductRepository,
        dispatcher: CoroutineDispatcher
    ) = GetProductsUseCase(repository, dispatcher)

    private fun provideGetProductByIdUseCase(
        repository: ProductRepository,
        dispatcher: CoroutineDispatcher
    ) = GetProductByIdUseCase(repository, dispatcher)

    private fun provideProductRepository(context: Context): ProductRepository {
        return ProductRepositoryImpl(
            localSource = ProductDatabase.instance(context).productDao(),
            remoteSource = RemoteProductFactory()
        )
    }

}