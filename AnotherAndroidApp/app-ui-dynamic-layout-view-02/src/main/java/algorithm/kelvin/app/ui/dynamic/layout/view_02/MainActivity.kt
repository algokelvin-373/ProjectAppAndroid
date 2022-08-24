package algorithm.kelvin.app.ui.dynamic.layout.view_02

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 0..4) {
            val layout = findViewById<LinearLayout>(R.id.rootViewGroup)
            val textView = TextView(this)
            textView.layoutParams= LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textView.text = "Item ${i+1}"
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textView.setTextColor(Color.BLACK)
            layout?.addView(textView)
        }
    }
}
