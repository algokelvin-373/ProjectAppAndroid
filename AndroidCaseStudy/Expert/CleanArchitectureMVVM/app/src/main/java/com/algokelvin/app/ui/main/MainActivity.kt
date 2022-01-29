package com.algokelvin.app.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.app.base.BaseActivity
import com.algokelvin.app.databinding.ActivityMainBinding
import com.algokelvin.app.model.Resource
import com.algokelvin.app.model.uimodel.ProductUIModel
import com.algokelvin.app.ui.adapter.ProductAdapter

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private val productAdapter by lazy { ProductAdapter(onItemClicked = ::onItemProductClicked) }
    private val mainViewModelFactory by lazy { MainViewModelFactory(applicationContext) }
    private val mainViewModel by lazy {
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        getAllProducts()
    }

    private fun initView() {
        binding.listProduct.adapter = productAdapter
    }

    private fun getAllProducts() {
        mainViewModel.products.observe(this, {
            when(it.status) {
                Resource.Status.SUCCESS -> productAdapter.addAll(it.data!!)
                Resource.Status.LOADING -> toast("Getting the list of product")
                Resource.Status.ERROR -> {}
            }
        })
    }

    private fun onItemProductClicked(product: ProductUIModel) {
        mainViewModel.productId(product.id)
    }

}