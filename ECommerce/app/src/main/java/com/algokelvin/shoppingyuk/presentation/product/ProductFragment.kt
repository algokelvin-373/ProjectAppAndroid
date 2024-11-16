package com.algokelvin.shoppingyuk.presentation.product

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
import com.algokelvin.shoppingyuk.databinding.ActivityProductBinding
import com.algokelvin.shoppingyuk.presentation.di.Injector
import com.algokelvin.shoppingyuk.presentation.onclick.OnClickItemProduct
import com.algokelvin.shoppingyuk.presentation.productdetail.ProductDetailActivity
import javax.inject.Inject

class ProductFragment : Fragment(), OnClickItemProduct {
    @Inject
    lateinit var factory: ProductViewModelFactory

    private lateinit var binding: ActivityProductBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_product, container, false)

        (activity?.application as Injector).createMovieSubComponent()
            .inject(this)
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        /*binding.imgProfile.setOnClickListener {
            val profileId = intent.getIntExtra("PROFILE_ID", 0)
            productViewModel.getProfileFromDB(profileId).observe(viewLifecycleOwner, Observer { user ->
                Toast.makeText(context, "User: "+user.username, Toast.LENGTH_SHORT).show()
                val profileBottomSheetFragment = ProfileBottomSheetFragment(user)
                profileBottomSheetFragment.show(supportFragmentManager, profileBottomSheetFragment.tag)
            })
        }*/
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.update, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update -> {
                //updateMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    /*private fun updateMovies() {
        *//*binding.productProgressBar.visibility = View.VISIBLE
        val response = productViewModel.updateMovies()
        response.observe(this, Observer {
            if (it != null) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                binding.productProgressBar.visibility = View.GONE
            } else {
                binding.productProgressBar.visibility = View.GONE
            }
        })*//*
    }*/

    private fun initRecyclerView(){
        binding.productRecyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = ProductAdapter(this)
        binding.productRecyclerView.adapter = adapter
        displayPopularMovies()
    }

    private fun displayPopularMovies(){
        binding.productProgressBar.visibility = View.VISIBLE
        val responseLiveData = productViewModel.getProducts()
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