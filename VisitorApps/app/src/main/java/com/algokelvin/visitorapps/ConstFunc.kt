package com.algokelvin.visitorapps

import android.content.res.Resources

object ConstFunc {
    fun getSizeDp(resource: Resources, dp: Int): Float {
        val scale = resource.displayMetrics.density
        return dp * scale + 0.5f
    }
}