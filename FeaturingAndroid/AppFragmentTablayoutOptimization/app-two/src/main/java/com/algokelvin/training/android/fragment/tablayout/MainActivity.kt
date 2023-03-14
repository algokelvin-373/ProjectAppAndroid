package com.algokelvin.training.android.fragment.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.findFragmentByTag(PageFragment::class.java.simpleName)
        fragmentTransaction.add(R.id.fragmentSample, PageFragment(), PageFragment::class.java.simpleName)
        fragmentTransaction.commit()

    }
}
