package com.thepparat.heisigwithspacedrepetition.epoxy.controller

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.thepparat.heisigwithspacedrepetition.epoxy.model.BarChartModelCorrect
import com.thepparat.heisigwithspacedrepetition.epoxy.model.BarChartModelWrong
import com.thepparat.heisigwithspacedrepetition.epoxy.model.NoDataModel
import com.thepparat.heisigwithspacedrepetition.epoxy.model.PieChartModel
import com.thepparat.heisigwithspacedrepetition.roomdatabase.roomentity.SpacedEntity


class ControllerStat : EpoxyController() {
    var spacedKanji = listOf<SpacedEntity>()
    override fun buildModels() {
        if (spacedKanji.isEmpty()){
            NoDataModel().id("NO DATA").addTo(this)
        }else{
            val correct = spacedKanji.sortedBy { it.status.correct }.takeLast(5)
            val wrong = spacedKanji.sortedBy { it.status.wrong }.takeLast(5)
            Log.i("WRONG",wrong.toString())
            PieChartModel(spacedKanji).id("PIE CHART").addTo(this)
            BarChartModelCorrect(correct,"Most Correct Bar Chart").id("BAR CHART").addTo(this)
            BarChartModelWrong(wrong,"Most Wrong Bar Chart").id("BAR CHART").addTo(this)
        }
    }

}