package com.algokelvin.movieapp.presentation.cart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.data.model.cart.CartDB
import com.algokelvin.movieapp.databinding.ActivityCartBinding
import com.algokelvin.movieapp.databinding.ItemCartLayoutBinding
import com.algokelvin.movieapp.presentation.di.Injector
import com.algokelvin.movieapp.presentation.onclick.OnClickItemCart
import com.algokelvin.movieapp.utils.EncryptLocal
import javax.inject.Inject

class CartActivity : AppCompatActivity(), OnClickItemCart {
    @Inject
    lateinit var factory: CartViewModelFactory

    private lateinit var cartViewModel: CartViewModel
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter

    private var profileId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)

        (application as Injector).createCartSubComponent()
            .inject(this)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class]

        profileId = EncryptLocal.getIdProfile(this)

        initListCart()
    }

    private fun initListCart() {
        binding.rvCart.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(this)
        binding.rvCart.adapter = adapter
        getListCart()
    }

    override fun onClickIncrease(bindingItem: ItemCartLayoutBinding, cartDB: CartDB) {
        val countNow = cartDB.count + 1
        bindingItem.itemCart.text = countNow.toString()
        cartDB.apply {
            updateListCart(userId, productId, countNow)
            getListCart()
        }
    }

    override fun onClickDecrease(bindingItem: ItemCartLayoutBinding, cartDB: CartDB) {
        val countNow = cartDB.count - 1
        bindingItem.itemCart.text = countNow.toString()
        cartDB.apply {
            updateListCart(userId, productId, countNow)
            getListCart()
        }
    }

    override fun onClickDelete(cartDB: CartDB) {
        cartDB.apply {
            deleteProductInCart(userId, productId)
            getListCart()
        }
    }

    private fun getListCart() {
        profileId?.let { id ->
            cartViewModel.getCartByUserId(id).observe(this, Observer { cart ->
                if(cart != null){
                    cart.data?.let { listCart ->
                        adapter.setList(listCart)
                        adapter.notifyDataSetChanged()
                    }
                }else{
                    Toast.makeText(this,"No data cart", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun updateListCart(userId: Int, productId: Int, count: Int) {
        cartViewModel.updateCountProduct(userId, productId, count).observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })
    }

    private fun deleteProductInCart(userId: Int, productId: Int) {
        cartViewModel.deleteProduct(userId, productId). observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })
    }
}