package com.algokelvin.shoppingyuk.presentation.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.algokelvin.shoppingyuk.presentation.product.ProductFragment
import com.algokelvin.shoppingyuk.presentation.productcategory.ProductCategoryFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductFragment()
            1 -> ProductCategoryFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}
