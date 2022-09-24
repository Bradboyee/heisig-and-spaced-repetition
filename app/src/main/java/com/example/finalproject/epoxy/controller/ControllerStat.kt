package com.example.finalproject.epoxy.controller

import com.airbnb.epoxy.EpoxyController
import com.example.finalproject.R
import com.example.finalproject.epoxy.model.KotlinModel
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.github.mikephil.charting.charts.PieChart

class ControllerStat:EpoxyController() {
    val spacedKanji = listOf<SpacedEntity>()
    val pieChartColor = listOf<Int>()
    override fun buildModels() {
        PieChartModel(pieChartColor).id("PIE CHART").addTo(this)
    }
    data class PieChartModel(val color:List<Int>):KotlinModel(R.layout.epoxy_stat_piechart){
        private val pieChart by bind<PieChart>(R.id.pieChart)
        override fun bind() {
            TODO("Not yet implemented")
        }
    }
}