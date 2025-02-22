// BottomSheetFragment.kt
package com.algokelvin.shoppingyuk.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.algokelvin.shoppingyuk.R
import com.algokelvin.shoppingyuk.data.model.user.User
import com.algokelvin.shoppingyuk.databinding.ProfilePageBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileBottomSheetFragment(private val user: User) : BottomSheetDialogFragment() {
    private lateinit var binding: ProfilePageBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_page_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = user.name.firstName+" "+user.name.lastName
        binding.nameProfileUser.text = name
        binding.emailProfileUser.text = user.email
        binding.phoneProfileUser.text = user.phone
    }
}
