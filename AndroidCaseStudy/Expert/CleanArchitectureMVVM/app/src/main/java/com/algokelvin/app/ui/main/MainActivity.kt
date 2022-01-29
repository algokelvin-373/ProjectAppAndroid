package com.algokelvin.app.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.app.base.BaseActivity
import com.algokelvin.app.databinding.ActivityMainBinding
import com.algokelvin.app.model.Resource
import com.algokelvin.app.model.uimodel.ProductUIModel
import com.algokelvin.app.ui.adapter.ProductAdapter

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModelFactory by lazy { MainViewModelFactory(applicationContext) }
    private val mainViewModel by lazy {
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.products.observe(this, {
            Log.i("rsp-products", it.toString())
        })
    }

    private fun initView() {
//        binding.listProduct.adapter = productAdapter
    }

    private fun initObservable() {
        mainViewModel.products.observe(this, {
            Log.i("rsp-products", it.toString())
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    Log.i("rsp-products", "Show All")
//                    val productAdapter = ProductAdapter(it.data, ::onItemProductClicked)
//                    binding.listProduct.adapter = productAdapter
                }
                Resource.Status.LOADING -> toast("Getting the product detail info")
                Resource.Status.ERROR -> toast("Error")
            }
        })
    }

    private fun onItemProductClicked(product: ProductUIModel) {
        mainViewModel.productId(product.id)
    }

}