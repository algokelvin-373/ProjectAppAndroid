package algorithm.kelvin.stackoverflow.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var statusRowOne = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLayoutOneYes.setOnClickListener {
            statusRowOne = true
            if (statusRowOne) {
                layoutRowOne.visibility = View.GONE
                layoutRowTwo.visibility = View.VISIBLE
            }
        }
        btnLayoutOneNo.setOnClickListener {
            statusRowOne = true
            if (statusRowOne) {
                layoutRowOne.visibility = View.GONE
                layoutRowTwo.visibility = View.VISIBLE
            }
        }
    }
}
