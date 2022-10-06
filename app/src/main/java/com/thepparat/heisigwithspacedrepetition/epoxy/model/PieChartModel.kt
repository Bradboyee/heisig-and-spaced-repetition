package com.thepparat.heisigwithspacedrepetition.epoxy.model

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.thepparat.heisigwithspacedrepetition.R
import com.thepparat.heisigwithspacedrepetition.valueformatter.PieChartFormatter
import com.thepparat.heisigwithspacedrepetition.roomdatabase.roomentity.SpacedEntity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

data class PieChartModel(val spacedKanji: List<SpacedEntity>) : KotlinModel(R.layout.epoxy_stat_piechart) {
    private val pieChartView by bind<PieChart>(R.id.pieChart)
    override fun bind() {
        val pieDataList = getData(spacedKanji)
        val color = getColors()
        setChart(pieChartView,pieDataList,color)
    }

    private fun getData(spacedKanji: List<SpacedEntity>): ArrayList<PieEntry> {
        val pieDataList = ArrayList<PieEntry>()
        val grade1 = spacedKanji.filter { it.Grade == 1 }
        val grade2 = spacedKanji.filter { it.Grade == 2 }
        val grade3 = spacedKanji.filter { it.Grade == 3 }
        val grade4 = spacedKanji.filter { it.Grade == 4 }
        val grade5 = spacedKanji.filter { it.Grade == 5 }
        val grade6 = spacedKanji.filter { it.Grade == 6 }
        if (grade1.isNotEmpty()) pieDataList.add(PieEntry(grade1.size.toFloat(), "Grade 1"))
        if (grade2.isNotEmpty()) pieDataList.add(PieEntry(grade2.size.toFloat(), "Grade 2"))
        if (grade3.isNotEmpty()) pieDataList.add(PieEntry(grade3.size.toFloat(), "Grade 3"))
        if (grade4.isNotEmpty()) pieDataList.add(PieEntry(grade4.size.toFloat(), "Grade 4"))
        if (grade5.isNotEmpty()) pieDataList.add(PieEntry(grade5.size.toFloat(), "Grade 5"))
        if (grade6.isNotEmpty()) pieDataList.add(PieEntry(grade6.size.toFloat(), "Grade 6"))
        return pieDataList
    }

    private fun setChart(pieChart: PieChart, pieDataList: ArrayList<PieEntry>, color: List<Int>) {
        val myFont = ResourcesCompat.getFont(pieChart.context, R.font.sfprodisplayregular)
        val pieDataSet = PieDataSet(pieDataList, "Spaced Repetition")
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.colors = color
        pieDataSet.valueTypeface = myFont
        pieDataSet.valueTextSize = 12f
        val pieData = PieData(pieDataSet)
        pieData.setValueFormatter(PieChartFormatter(" %"))
        pieData.setValueTextColor(Color.BLACK)
        pieData.setValueTextSize(12f)
        pieData.setValueTypeface(myFont)
        //overall
        pieChartView.invalidate()
        pieChartView.data = pieData
        pieChartView.setUsePercentValues(true)
        pieChartView.description.isEnabled = false
        pieChartView.legend.isEnabled = false
        pieChartView.animateY(1400, Easing.EaseInOutQuad)
    }

    private fun getColors(): ArrayList<Int> {
        val colors = ArrayList<Int>()
        colors.add(ContextCompat.getColor(pieChartView.context, R.color.pie1))
        colors.add(ContextCompat.getColor(pieChartView.context, R.color.pie2))
        colors.add(ContextCompat.getColor(pieChartView.context, R.color.pie3))
        colors.add(ContextCompat.getColor(pieChartView.context, R.color.pie4))
        colors.add(ContextCompat.getColor(pieChartView.context, R.color.pie5))
        colors.add(ContextCompat.getColor(pieChartView.context, R.color.pie6))
        return colors
    }
}