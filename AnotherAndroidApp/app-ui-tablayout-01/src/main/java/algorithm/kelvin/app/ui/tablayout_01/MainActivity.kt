package algorithm.kelvin.app.ui.tablayout_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appsViewPageAdapter = AppsViewPageAdapter(supportFragmentManager)
        appsViewPageAdapter.addAppsPage(OneTabFragment(), TwoTabFragment(), ThreeTabFragment(), FourTabFragment())
        layoutViewPager.adapter = appsViewPageAdapter

        tabsLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) { }
            override fun onTabUnselected(p0: TabLayout.Tab?) { }
            override fun onTabSelected(p0: TabLayout.Tab?) { layoutViewPager.setCurrentItem(p0?.position!!, true) }
        })

        btnToFragmentTwo.setOnClickListener {
            layoutViewPager.setCurrentItem(2, true)
        }
    }
}
