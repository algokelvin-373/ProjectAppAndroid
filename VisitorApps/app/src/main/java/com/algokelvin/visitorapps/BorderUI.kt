package com.algokelvin.visitorapps

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat

class BorderUI(
    private val context: Context,
    private val color: Int? = null,
    private val colorStroke: Int? = null,
    private val sizeStroke: Int? = null,
    private val radius: Float? = null
) {
    private var border: GradientDrawable? = null

    init {
        border = GradientDrawable()
        setBorderColor()
        setBorderColorStroke()
        setBorderRadius()
    }

    fun getBorder() = border

    private fun setBorderColor() {
        if (color != null) {
            border?.setColor(ContextCompat.getColor(context, color))
        }
    }

    private fun setBorderColorStroke() {
        if (colorStroke != null && sizeStroke != null) {
            border?.setStroke(sizeStroke, colorStroke)
        }
    }

    private fun setBorderRadius() {
        if (radius != null) {
            border?.cornerRadius = radius
        }
    }

}