package com.algokelvin.conversionsimple.base.function

import android.content.Context
import com.algokelvin.conversionsimple.base.model.Conversion

class ConversionFunction(
    private val context: Context
) {

    fun getConversions() = listOf(
        Conversion(1, "Pounds to Kilograms", "lbs", "kg", 0.453592),
        Conversion(2, "Kilograms to Pounds", "kg", "lbs", 2.20462),
        Conversion(3, "Yards to Meters", "yd", "m", 0.9144),
        Conversion(4, "Meters to Yards", "m", "yd", 1.09361),
        Conversion(5, "Miles to Kilometers", "mil", "km", 1.60934),
        Conversion(6, "Kilometers to Miles", "km", "mil", 0.621371),
    )

}