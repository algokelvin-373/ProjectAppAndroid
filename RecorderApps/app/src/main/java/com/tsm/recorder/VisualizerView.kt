package com.tsm.recorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class VisualizerView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {
    private var amplitudes = mutableListOf<Float>()

    fun addAmplitude(amplitude: Float) {
        amplitudes.add(amplitude)
        if (amplitudes.size > width / 5) {
            amplitudes.removeAt(0)
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val paint = Paint().apply {
                color = Color.BLUE
                strokeWidth = 5f
                style = Paint.Style.STROKE
            }
            val middle = height / 2f
            var x = 0f
            amplitudes.forEach { amp ->
                val scaledAmp = amp / 32767f * height
                canvas.drawLine(x, middle + scaledAmp, x, middle - scaledAmp, paint)
                x += 5f
            }
        }
    }
}