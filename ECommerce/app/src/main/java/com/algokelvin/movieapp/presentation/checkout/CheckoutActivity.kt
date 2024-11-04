package com.algokelvin.movieapp.presentation.checkout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.databinding.ActivityCheckoutBinding
import com.algokelvin.movieapp.presentation.di.Injector
import com.algokelvin.movieapp.presentation.home.HomeActivity
import com.algokelvin.movieapp.utils.EncryptLocal
import javax.inject.Inject

class CheckoutActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: CheckoutViewModelFactory

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var checkoutViewModel: CheckoutViewModel
    private lateinit var adapter: CheckoutAdapter

    private var profileId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout)

        (application as Injector).createCheckoutSubComponent()
            .inject(this)
        checkoutViewModel = ViewModelProvider(this, factory)[CheckoutViewModel::class]

        profileId = EncryptLocal.getIdProfile(this)

        initCheckout()

        binding.btnConfirm.setOnClickListener {
            confirmCheckout()
        }
    }

    private fun confirmCheckout() {
        binding.btnConfirm.setBackgroundColor(resources.getColor(R.color.green_00ff00))
        binding.btnConfirm.text = "Done"

        profileId?.let { id ->
            checkoutViewModel.confirmCheckout(id).observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                Handler().postDelayed({
                    val intentToHome = Intent(this, HomeActivity::class.java)
                    startActivity(intentToHome)
                    finishAffinity()
                }, 5000)
            }
        }
    }

    private fun initCheckout() {
        binding.rvShopping.layoutManager = LinearLayoutManager(this)
        adapter = CheckoutAdapter()
        binding.rvShopping.adapter = adapter
        getListCheckout()
    }

    private fun getListCheckout() {
        profileId?.let { id ->
            checkoutViewModel.getCartByUserId(id).observe(this) { checkout ->
                if (checkout != null) {
                    checkout.data?.let { listCart ->
                        getTotalAllPrice(listCart)
                        adapter.setList(listCart)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this, "No data checkout", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getTotalAllPrice(list: List<CartDB>) {
        var totalAll = 0.0f
        for (price in list) {
            val total = price.count.toFloat() * price.productPrice?.toFloat()!!
            totalAll += total
        }
        binding.totalAllPrice.text = getString(R.string.total_all_price, totalAll.toString())
    }
}