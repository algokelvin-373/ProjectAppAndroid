package com.algokelvin.movieapp.presentation.productdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.databinding.ActivityProductDetailBinding
import com.algokelvin.movieapp.presentation.di.Injector
import com.bumptech.glide.Glide
import javax.inject.Inject

class ProductDetailActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ProductDetailViewModelFactory

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var productDetailViewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        (application as Injector).createProductDetailSubComponent()
            .inject(this)
        productDetailViewModel = ViewModelProvider(this, factory)[ProductDetailViewModel::class]
        initProductDetail()
    }

    private fun initProductDetail() {
        val id = intent.getIntExtra("PRODUCT_ID", 0)
        productDetailViewModel.getProductDetail(id.toString()).observe(this, Observer {
            if (it != null) {
                val product = it
                Glide.with(binding.imageProduct.context)
                    .load(product.image)
                    .into(binding.imageProduct)
                binding.nameProduct.text = product.title
                binding.categoryProduct.text = product.category
                binding.descriptionProduct.text = product.description
            }
        })

    }

}