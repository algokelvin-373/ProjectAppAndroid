package algorithm.kelvin.app.ui.tablayout_01

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

class AlgoViewPager(context: Context, attributeSet: AttributeSet) : androidx.viewpager.widget.ViewPager(context, attributeSet) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}