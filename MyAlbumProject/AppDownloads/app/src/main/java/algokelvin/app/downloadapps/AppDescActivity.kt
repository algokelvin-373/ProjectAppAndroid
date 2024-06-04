package algokelvin.app.downloadapps

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_app_desc.*
import android.graphics.Point
import android.view.View
import com.tsm.aptoide.R
import com.tsm.base.ViewUtil
import com.tsm.base.ViewUtil.getViewOffset

class AppDescActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_desc)
        //ViewUtil.customStatusAndNav(this)

        val bundle = intent.getBundleExtra("app")
        if (bundle != null) {
            val nameApp = bundle.getString("app_name")
            val descApp = bundle.getString("app_desc")
            val newApp = bundle.getString("app_new")
            val colorApp = bundle.getInt("app_color")
            val newFocus = bundle.getInt("new_focus", 0)

            toolbar.title = nameApp
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            full_desc.text = descApp
            full_new.text = newApp
            appbar_main.setBackgroundColor(colorApp)
            ViewUtil.setColorStatusbar(this, colorApp)

            if (full_new.text.isEmpty()) new_container.visibility = View.GONE

            Handler().postDelayed({
                val childOffset = Point()
                getViewOffset(nested_scroll, full_new.parent, full_new, childOffset)

                when (newFocus) {
                    1 -> nested_scroll.smoothScrollTo(0, childOffset.y)
                }
            }, 500)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}