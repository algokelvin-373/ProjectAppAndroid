package com.algokelvin.movieapp.presentation.productcategory

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.product.Product
import com.algokelvin.movieapp.databinding.ActivityProductCategoryBinding
import com.algokelvin.movieapp.presentation.di.Injector
import com.algokelvin.movieapp.presentation.onclick.OnClickItemProduct
import com.algokelvin.movieapp.presentation.product.ProductAdapter
import com.algokelvin.movieapp.presentation.productdetail.ProductDetailActivity
import javax.inject.Inject

class ProductCategoryActivity : AppCompatActivity(), OnClickItemProduct {
    @Inject
    lateinit var factory: ProductCategoryViewModelFactory

    private lateinit var binding: ActivityProductCategoryBinding
    private lateinit var productCategoryViewModel: ProductCategoryViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_category)

        (application as Injector).createProductCategorySubComponent()
            .inject(this)
        productCategoryViewModel = ViewModelProvider(this, factory)[ProductCategoryViewModel::class]

        initProductCategory()
    }

    private fun initProductCategory() {
        binding.productRecyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = ProductAdapter(this)
        binding.productRecyclerView.adapter = adapter
        displayProductsSortByCategory()
    }

    private fun displayProductsSortByCategory() {
        binding.productProgressBar.visibility = View.VISIBLE
        val responseLiveData = productCategoryViewModel.getProductsSortByCategory()
        responseLiveData.observe(this, Observer {
            if(it!=null){
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                binding.productProgressBar.visibility = View.GONE
            }else{
                binding.productProgressBar.visibility = View.GONE
                Toast.makeText(applicationContext,"No data available", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClickItemProduct(product: Product) {
        val toProductDetailPage = Intent(this, ProductDetailActivity::class.java)
        toProductDetailPage.putExtra("PRODUCT_ID", product.id)
        startActivity(toProductDetailPage)
    }
}