package com.algokelvin.movieapp.presentation.product

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.Product
import com.algokelvin.movieapp.databinding.ActivityProductBinding
import com.algokelvin.movieapp.presentation.di.Injector
import com.algokelvin.movieapp.presentation.onclick.OnClickItemProduct
import com.algokelvin.movieapp.presentation.productdetail.ProductDetailActivity
import javax.inject.Inject

class ProductActivity : AppCompatActivity(), OnClickItemProduct {
    @Inject
    lateinit var factory: ProductViewModelFactory

    private lateinit var binding: ActivityProductBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)

        (application as Injector).createMovieSubComponent()
            .inject(this)
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class]

        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.update, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update -> {
                updateMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateMovies() {
        binding.productProgressBar.visibility = View.VISIBLE
        val response = productViewModel.updateMovies()
        response.observe(this, Observer {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                binding.productProgressBar.visibility = View.GONE
            } else {
                binding.productProgressBar.visibility = View.GONE
            }
        })
    }

    private fun initRecyclerView(){
        binding.productRecyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = ProductAdapter(this)
        binding.productRecyclerView.adapter = adapter
        displayPopularMovies()
    }

    private fun displayPopularMovies(){
        binding.productProgressBar.visibility = View.VISIBLE
        val responseLiveData = productViewModel.getProducts()
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