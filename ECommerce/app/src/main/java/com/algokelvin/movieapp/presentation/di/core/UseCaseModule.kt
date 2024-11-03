package com.algokelvin.movieapp.presentation.di.core

import com.algokelvin.movieapp.domain.repository.CartRepository
import com.algokelvin.movieapp.domain.repository.LoginRepository
import com.algokelvin.movieapp.domain.repository.ProductCategoryRepository
import com.algokelvin.movieapp.domain.repository.ProductDetailRepository
import com.algokelvin.movieapp.domain.repository.ProductRepository
import com.algokelvin.movieapp.domain.usecase.AddProductToCartUseCase
import com.algokelvin.movieapp.domain.usecase.DeleteProductInCartUseCase
import com.algokelvin.movieapp.domain.usecase.GetCartByUserIdUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductDetailUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductsCategoryUseCase
import com.algokelvin.movieapp.domain.usecase.GetProductsUseCase
import com.algokelvin.movieapp.domain.usecase.GetProfileFromDBUseCase
import com.algokelvin.movieapp.domain.usecase.GetProfileUseCase
import com.algokelvin.movieapp.domain.usecase.LoginUseCase
import com.algokelvin.movieapp.domain.usecase.UpdateCountProductInCartUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun provideGetMovieUseCase(productRepository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(productRepository)
    }

    @Provides
    fun provideGetProductDetailUseCase(productDetailRepository: ProductDetailRepository): GetProductDetailUseCase {
        return GetProductDetailUseCase(productDetailRepository)
    }

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    fun provideGetProductsCategoryUseCase(productCategoryRepository: ProductCategoryRepository): GetProductsCategoryUseCase {
        return GetProductsCategoryUseCase(productCategoryRepository)
    }

    @Provides
    fun provideGetProfileUseCase(loginRepository: LoginRepository): GetProfileUseCase {
        return GetProfileUseCase(loginRepository)
    }

    @Provides
    fun provideGetProfileFromDBUseCase(loginRepository: LoginRepository): GetProfileFromDBUseCase {
        return GetProfileFromDBUseCase(loginRepository)
    }

    @Provides
    fun provideGetCartByUserIdUseCase(cartRepository: CartRepository): GetCartByUserIdUseCase {
        return GetCartByUserIdUseCase(cartRepository)
    }

    @Provides
    fun provideAddProductToCartUseCase(cartRepository: CartRepository): AddProductToCartUseCase {
        return AddProductToCartUseCase(cartRepository)
    }

    @Provides
    fun provideUpdateCountProductInCartUseCase(cartRepository: CartRepository): UpdateCountProductInCartUseCase {
        return UpdateCountProductInCartUseCase(cartRepository)
    }

    @Provides
    fun provideDeleteProductInCartUseCase(cartRepository: CartRepository): DeleteProductInCartUseCase {
        return DeleteProductInCartUseCase(cartRepository)
    }
}