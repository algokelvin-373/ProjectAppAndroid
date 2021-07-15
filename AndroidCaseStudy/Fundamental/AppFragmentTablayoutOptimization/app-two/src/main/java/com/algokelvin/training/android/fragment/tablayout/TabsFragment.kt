package com.algokelvin.training.android.fragment.tablayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tabs.*

class TabsFragment(private val index: Int) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(index) {
            0 -> {
                layoutTabFragment.setBackgroundResource(R.color.colorPrimaryDark)
                textHelloFragment.text = getString(R.string.hello_blank_fragment, "Hello Fragment Tabs 1")
            }
            1 -> {
                layoutTabFragment.setBackgroundResource(R.color.colorPrimary)
                textHelloFragment.text = getString(R.string.hello_blank_fragment, "Hello Fragment Tabs 2")
            }
            2 -> {
                layoutTabFragment.setBackgroundResource(R.color.colorAccent)
                textHelloFragment.text = getString(R.string.hello_blank_fragment, "Hello Fragment Tabs 3")
            }
            3 -> {
                layoutTabFragment.setBackgroundResource(R.color.colorPrimary)
                textHelloFragment.text = getString(R.string.hello_blank_fragment, "Hello Fragment Tabs 4")
            }
            4 -> {
                layoutTabFragment.setBackgroundResource(R.color.colorPrimaryDark)
                textHelloFragment.text = getString(R.string.hello_blank_fragment, "Hello Fragment Tabs 5")
            }
        }
    }

}