package app.isfaaghyth.architecture.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.isfaaghyth.architecture.base.BaseActivity
import app.isfaaghyth.architecture.data.Resource
import app.isfaaghyth.architecture.databinding.ActivityMainBinding
import app.isfaaghyth.architecture.ui.adapter.ProductAdapter
import app.isfaaghyth.architecture.ui.detail.ProductDetailBottomSheet
import app.isfaaghyth.architecture.ui.uimodel.ProductUIModel
import java.util.*

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy { ProductAdapter(onItemClicked = ::onItemProductClicked) }
    private val viewModelFactory by lazy { MainViewModelFactory(applicationContext) }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObservable()
    }

    private fun initView() {
        binding.lstProduct.adapter = adapter
    }

    private fun initObservable() {
        viewModel.products.observe(this, {
            when(it.status) {
                Resource.Status.SUCCESS -> adapter.addAll(it.data!!)
                Resource.Status.LOADING -> toast("Getting the list of product")
                Resource.Status.ERROR -> {}
            }
        })

        viewModel.productById.observe(this, {
            when(it.status) {
                Resource.Status.SUCCESS ->  it.data?.let { product -> showProductDetailSheet(product) }
                Resource.Status.LOADING -> toast("Getting the product detail info")
                Resource.Status.ERROR -> {}
            }
        })
    }

    private fun showProductDetailSheet(product: ProductUIModel) {
        ProductDetailBottomSheet.show(
            fm = supportFragmentManager,
            product = product
        )
    }

    private fun onItemProductClicked(product: ProductUIModel) {
        viewModel.productId(product.id)
    }

}