package com.example.finalproject.fragment.stat

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class MyValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value == 0f) {
            ""
        } else {
            value.roundToInt().toString() + " Kanji"
        }
    }
}
//source : https://codeutility.org/mpandroidchart-how-to-hide-zero-values-in-pie-chart-mpchart-android-stack-overflow/