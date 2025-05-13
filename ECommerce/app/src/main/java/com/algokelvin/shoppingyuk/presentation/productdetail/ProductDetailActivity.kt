package com.algokelvin.shoppingyuk.presentation.productdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.shoppingyuk.R
import com.algokelvin.shoppingyuk.data.model.cart.CartDB
import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.databinding.ActivityProductDetailBinding
import com.algokelvin.shoppingyuk.presentation.di.Injector
import com.algokelvin.shoppingyuk.utils.EncryptLocal
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
        productDetailViewModel.getProductDetail(id.toString()).observe(this) {
            if (it != null) {
                val product = it
                Glide.with(binding.imageProduct.context)
                    .load(product.image)
                    .into(binding.imageProduct)
                binding.nameProduct.text = product.title
                binding.categoryProduct.text = product.category
                binding.priceProduct.text = getString(R.string.total_all_price, product.price.toString())
                binding.descriptionProduct.text = product.description
                addToCart(product)
            }
        }
    }

    private fun addToCart(product: Product) {
        binding.btnAddToCart.setOnClickListener {
            EncryptLocal.getIdProfile(this).let { profileId ->
                val cartDB = CartDB(
                    userId = profileId,
                    productId = product.id,
                    productTitle = product.title,
                    productImage = product.image,
                    productPrice = product.price,
                    count = 1
                )
                cartDB.let { data ->
                    productDetailViewModel.addProductToCart(data).observe(this) {
                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}