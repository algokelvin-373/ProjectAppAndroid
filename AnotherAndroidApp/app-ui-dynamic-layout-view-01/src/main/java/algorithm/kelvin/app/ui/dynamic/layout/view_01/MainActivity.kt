package algorithm.kelvin.app.ui.dynamic.layout.view_01

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

        val layout = findViewById<LinearLayout>(R.id.rootViewGroup)

        // Create TextView programmatically.
        val textView = TextView(this)

        // setting height and width
        textView.layoutParams= LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        // setting text
        textView.text = "Kelvin"
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        textView.setTextColor(Color.MAGENTA)

        layout ?.addView(textView) // Add to Linear Layout ( id: rootViewGroup )


        val textView2 = TextView(this)
        val params2 = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView2.layoutParams = params2
        textView2.text = "Kelvin 2"
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        textView2.setTextColor(Color.MAGENTA)
        layout ?.addView(textView2)

        val textView3 = TextView(this)
        val params3 = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView3.layoutParams = params3
        textView3.text = "Kelvin 3"
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        textView3.setTextColor(Color.MAGENTA)
        layout ?.addView(textView3)
    }
}
