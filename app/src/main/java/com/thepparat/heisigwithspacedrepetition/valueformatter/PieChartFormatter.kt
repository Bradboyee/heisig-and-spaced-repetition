package com.thepparat.heisigwithspacedrepetition.valueformatter

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class PieChartFormatter(val text:String) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value == 0f) {
            ""
        } else {
            value.roundToInt().toString() + text
        }
    }
}
//source : https://codeutility.org/mpandroidchart-how-to-hide-zero-values-in-pie-chart-mpchart-android-stack-overflow/