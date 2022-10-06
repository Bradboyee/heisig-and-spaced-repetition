package com.thepparat.heisigwithspacedrepetition.epoxy.model

import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.thepparat.heisigwithspacedrepetition.valueformatter.BarChartFormatter
import com.thepparat.heisigwithspacedrepetition.R
import com.thepparat.heisigwithspacedrepetition.roomdatabase.roomentity.SpacedEntity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

data class BarChartModelCorrect(
    val spaced: List<SpacedEntity>,
    val headerText: String,
) :
    KotlinModel(R.layout.epoxy_stat_barchart) {
    private val barChartView by bind<BarChart>(R.id.barChart)
    private val textViewHeader by bind<TextView>(R.id.textViewHead)
    override fun bind() {
        setBarChart(getData())
        textViewHeader.text = headerText
    }

    private fun getData(): ArrayList<BarEntry> {
        val barGroup = ArrayList<BarEntry>()
        for (i in spaced.indices) {
            val barEntry =
                BarEntry(i.toFloat(), spaced[i].status.correct.toFloat(), spaced[i].kanji)
            barGroup.add(barEntry)
        }
        return barGroup
    }

    private fun setBarChart(barGroup: ArrayList<BarEntry>) {
        val myFont = ResourcesCompat.getFont(barChartView.context, R.font.sfprodisplayregular)
        val barDataSet = BarDataSet(barGroup, "")
        val barData = BarData(barDataSet)
        barChartView.data = barData
        barChartView.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChartView.xAxis.labelCount = spaced.size
        barChartView.xAxis.granularity = 1.0f
        barChartView.extraBottomOffset = 10f
        barChartView.axisLeft.granularity = 1.0f
        barChartView.axisRight.granularity = 1.0f
        barChartView.xAxis.enableGridDashedLine(5f, 5f, 0f)
        barChartView.axisRight.enableGridDashedLine(5f, 5f, 0f)
        barChartView.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        barChartView.description.isEnabled = false
        barChartView.animateY(1000)
        barChartView.legend.isEnabled = false
        barChartView.setPinchZoom(true)
        //xAxis Label
        val xAxisLabel = spaced.map { it.kanji }
        Log.i("xAxisLabel",xAxisLabel.toString())
        barChartView.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
        barChartView.xAxis.textSize = 15f
        //draw values
        barChartView.data.setDrawValues(true)
        barChartView.data.setValueFormatter(BarChartFormatter())
        barDataSet.valueTypeface = myFont
        barDataSet.colors = getColors()
        barDataSet.valueTextSize = 10f
    }

    private fun getColors(): ArrayList<Int> {
        val colors = ArrayList<Int>()
        colors.add(ContextCompat.getColor(barChartView.context, R.color.bar_correct1))
        colors.add(ContextCompat.getColor(barChartView.context, R.color.bar_correct2))
        colors.add(ContextCompat.getColor(barChartView.context, R.color.bar_correct3))
        colors.add(ContextCompat.getColor(barChartView.context, R.color.bar_correct4))
        colors.add(ContextCompat.getColor(barChartView.context, R.color.bar_correct5))
        return colors
    }
}