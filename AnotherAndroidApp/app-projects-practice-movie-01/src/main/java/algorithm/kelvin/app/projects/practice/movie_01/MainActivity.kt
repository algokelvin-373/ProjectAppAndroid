package algorithm.kelvin.app.projects.practice.movie_01

//import algorithm.kelvin.lib.app.projects.practice.movie.MainPageAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val mainPageAdapter = MainPageAdapter(supportFragmentManager)
//        mainPageAdapter.addPageMenu(MovieFragment(), TVFragment())
//        main_viewpager.adapter = mainPageAdapter
//        tab_layout_main.setupWithViewPager(main_viewpager)
//
//        tab_layout_main.getTabAt(0)?.setText(R.string.movie)
//        tab_layout_main.getTabAt(1)?.setText(R.string.tv_show)
//        tabMovieCatalogOnClick(tab_layout_main)
    }

    private fun tabMovieCatalogOnClick(tabLayout: TabLayout) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }
        })
    }
}
