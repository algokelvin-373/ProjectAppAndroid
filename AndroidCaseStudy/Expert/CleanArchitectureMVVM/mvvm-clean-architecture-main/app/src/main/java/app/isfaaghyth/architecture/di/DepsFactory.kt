package app.isfaaghyth.architecture.di

import android.content.Context
import app.isfaaghyth.architecture.data.datasource.local.ProductDatabase
import app.isfaaghyth.architecture.data.datasource.remote.RemoteProductFactory
import app.isfaaghyth.architecture.data.repository.ProductRepository
import app.isfaaghyth.architecture.data.repository.ProductRepositoryImpl
import app.isfaaghyth.architecture.domain.GetProductByIdUseCase
import app.isfaaghyth.architecture.domain.GetProductsUseCase
import app.isfaaghyth.architecture.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object DepsFactory {

    private fun provideProductRepository(
        context: Context
    ): ProductRepository {
        return ProductRepositoryImpl(
            localSource = ProductDatabase.instance(context).productDao(),
            remoteSource = RemoteProductFactory()
        )
    }

    private fun provideGetProductByIdUseCase(
        repository: ProductRepository,
        dispatcher: CoroutineDispatcher
    ): GetProductByIdUseCase {
        return GetProductByIdUseCase(repository, dispatcher)
    }

    private fun provideProductsUseCase(
        repository: ProductRepository,
        dispatcher: CoroutineDispatcher
    ): GetProductsUseCase {
        return GetProductsUseCase(repository, dispatcher)
    }

    private fun provideMainViewModel(
        getProductsUseCase: GetProductsUseCase,
        getProductByIdUseCase: GetProductByIdUseCase,
    ): MainViewModel {
       return MainViewModel(
           getProductByIdUseCase,
           getProductsUseCase
       )
    }

    fun buildMainViewModel(context: Context): MainViewModel {
        val repository = provideProductRepository(context)
        val dispatcherIo = Dispatchers.IO

        return provideMainViewModel(
            provideProductsUseCase(repository, dispatcherIo),
            provideGetProductByIdUseCase(repository, dispatcherIo),
        )
    }

}