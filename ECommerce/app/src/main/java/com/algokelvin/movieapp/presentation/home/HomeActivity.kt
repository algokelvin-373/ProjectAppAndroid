package com.algokelvin.movieapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.algokelvin.movieapp.R
import com.algokelvin.movieapp.databinding.ActivityHome2Binding
import com.algokelvin.movieapp.presentation.cart.CartActivity
import com.algokelvin.movieapp.presentation.di.Injector
import com.algokelvin.movieapp.presentation.profile.ProfileBottomSheetFragment
import com.algokelvin.movieapp.utils.EncryptLocal
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: HomeViewModelFactory

    private lateinit var binding: ActivityHome2Binding
    private lateinit var homeViewModel: HomeViewModel

    private var profileId : Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home2)

        (application as Injector).createHomeSubComponent()
            .inject(this)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class]

        profileId = EncryptLocal.getIdProfile(this)

        binding.imgProfile.setOnClickListener {
            profileId?.let { id ->
                homeViewModel.getProfileFromDB(id).observe(this, Observer { user ->
                    Toast.makeText(this, "User: "+user.username, Toast.LENGTH_SHORT).show()
                    val profileBottomSheetFragment = ProfileBottomSheetFragment(user)
                    profileBottomSheetFragment.show(supportFragmentManager, profileBottomSheetFragment.tag)
                })
            }
        }

        binding.imgCart.setOnClickListener {
            val intentToCart = Intent(this, CartActivity::class.java)
            startActivity(intentToCart)
        }

        initTabLayout()
    }

    private fun initTabLayout() {
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Product"
                1 -> "Category"
                else -> null
            }
        }.attach()
    }
}