package com.algokelvin.movieapp.presentation.productdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.databinding.ActivityProductDetailBinding
import com.algokelvin.movieapp.presentation.di.Injector
import com.algokelvin.movieapp.utils.EncryptLocal
import com.bumptech.glide.Glide
import javax.inject.Inject

class ProductDetailActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ProductDetailViewModelFactory

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var productDetailViewModel: ProductDetailViewModel

    private var product: Product? = null

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
                addToCart(product)
            }
        })
    }

    private fun addToCart(product: Product) {
        binding.btnAddToCart.setOnClickListener {
            EncryptLocal.getIdProfile(this)?.let { profileId ->
                /*val cartDB = product?.let { dataProduct ->
                    CartDB(
                        userId = profileId,
                        productId = dataProduct.id,
                        productTitle = dataProduct.title,
                        productImage = dataProduct.image,
                        productPrice = dataProduct.price,
                        count = 1
                    )
                }*/
                val cartDB = CartDB(
                    userId = profileId,
                    productId = product.id,
                    productTitle = product.title,
                    productImage = product.image,
                    productPrice = product.price,
                    count = 1
                )
                cartDB.let { data ->
                    productDetailViewModel.addProductToCart(data).observe(this, Observer {
                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                    })
                }
            }
        }
    }

}