package com.example.finalproject.fragment.stat

import android.graphics.Color.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentStatBinding
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.example.finalproject.viewmodel.SpacedViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatFragment : Fragment() {
    private var _binding: FragmentStatBinding? = null
    private val binding get() = _binding!!
    private val spacedViewModel by viewModels<SpacedViewModel>()
    private lateinit var pieDataList: ArrayList<PieEntry>
    private lateinit var pieDataSet: PieDataSet
    private lateinit var colors: ArrayList<Int>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStatBinding.inflate(inflater, container, false)
        initColor()
        prepareData()
        return binding.root
    }
    private fun prepareData() {
        lifecycle.coroutineScope.launch { spacedViewModel.allKanji.collect { initData(it) } }
    }

    private fun initData(spacedKanji: List<SpacedEntity>) {
        pieDataList = ArrayList()
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
        setPieChart()
    }

    private fun setPieChart() {
        val myFont = ResourcesCompat.getFont(requireContext(), R.font.sfprodisplayregular)
        val pieChartView = binding.pieChart
        pieDataSet = PieDataSet(pieDataList, "Spaced Repetition")
        pieDataSet.valueTextColor = WHITE
        pieDataSet.colors = colors
        pieDataSet.valueTypeface = myFont
        pieDataSet.valueTextSize = 12f
        val pieData = PieData(pieDataSet)
        pieData.setValueFormatter(MyValueFormatter())
        pieData.setValueTextColor(BLACK)
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


    private fun initColor() {
        colors = ArrayList()
        colors.add(ContextCompat.getColor(requireContext(), R.color.pie1))
        colors.add(ContextCompat.getColor(requireContext(), R.color.pie2))
        colors.add(ContextCompat.getColor(requireContext(), R.color.pie3))
        colors.add(ContextCompat.getColor(requireContext(), R.color.pie4))
        colors.add(ContextCompat.getColor(requireContext(), R.color.pie5))
        colors.add(ContextCompat.getColor(requireContext(), R.color.pie6))
    }


}
