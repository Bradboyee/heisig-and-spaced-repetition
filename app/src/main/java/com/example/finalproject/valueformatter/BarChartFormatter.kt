package com.example.finalproject.valueformatter

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class BarChartFormatter: ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return when (value) {
            0f -> "Never Do"
            1f -> {"${value.roundToInt()} time" }
            else -> "${value.roundToInt()} times"
        }
    }
}