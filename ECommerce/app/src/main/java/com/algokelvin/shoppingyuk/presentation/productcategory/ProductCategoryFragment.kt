package com.algokelvin.shoppingyuk.presentation.productcategory

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.algokelvin.shoppingyuk.R
import com.algokelvin.shoppingyuk.data.model.product.Product
import com.algokelvin.shoppingyuk.databinding.ActivityProductCategoryBinding
import com.algokelvin.shoppingyuk.presentation.di.Injector
import com.algokelvin.shoppingyuk.presentation.onclick.OnClickItemProduct
import com.algokelvin.shoppingyuk.presentation.product.ProductAdapter
import com.algokelvin.shoppingyuk.presentation.productdetail.ProductDetailActivity
import javax.inject.Inject

class ProductCategoryFragment : Fragment(), OnClickItemProduct {
    @Inject
    lateinit var factory: ProductCategoryViewModelFactory

    private lateinit var binding: ActivityProductCategoryBinding
    private lateinit var productCategoryViewModel: ProductCategoryViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_product_category, container, false)

        (activity?.application as Injector).createProductCategorySubComponent()
            .inject(this)
        productCategoryViewModel = ViewModelProvider(this, factory)[ProductCategoryViewModel::class]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProductCategory()
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(context, R.layout.activity_product_category)

        *//*(context as Injector).createProductCategorySubComponent()
            .inject(this)
        productCategoryViewModel = ViewModelProvider(this, factory)[ProductCategoryViewModel::class]*//*

        //initProductCategory()
    }*/

    private fun initProductCategory() {
        binding.productRecyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = ProductAdapter(this)
        binding.productRecyclerView.adapter = adapter
        displayProductsSortByCategory()
    }

    private fun displayProductsSortByCategory() {
        binding.productProgressBar.visibility = View.VISIBLE
        val responseLiveData = productCategoryViewModel.getProductsSortByCategory()
        responseLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                binding.productProgressBar.visibility = View.GONE
            }else{
                binding.productProgressBar.visibility = View.GONE
                Toast.makeText(context,"No data available", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onClickItemProduct(product: Product) {
        val toProductDetailPage = Intent(context, ProductDetailActivity::class.java)
        toProductDetailPage.putExtra("PRODUCT_ID", product.id)
        startActivity(toProductDetailPage)
    }
}