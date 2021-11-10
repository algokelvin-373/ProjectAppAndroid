package app.isfaaghyth.architecture.ui.main

import androidx.lifecycle.*
import app.isfaaghyth.architecture.data.Resource
import app.isfaaghyth.architecture.domain.GetProductByIdUseCase
import app.isfaaghyth.architecture.domain.GetProductsUseCase
import app.isfaaghyth.architecture.ui.uimodel.ProductUIModel
import app.isfaaghyth.architecture.ui.uimodel.ProductsUIModel

class MainViewModel constructor(
    private val getProductById: GetProductByIdUseCase,
    getProducts: GetProductsUseCase,
) : ViewModel() {

    val products: LiveData<Resource<ProductsUIModel>>
    = getProducts(Unit).asLiveData()

    private val _productId = MutableLiveData<Int>()

    val productById: LiveData<Resource<ProductUIModel>>
    = _productId.switchMap { id ->
        getProductById(id).asLiveData()
    }

    fun productId(id: Int) {
        _productId.value = id
    }

}