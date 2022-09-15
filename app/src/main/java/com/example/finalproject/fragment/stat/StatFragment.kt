package com.example.finalproject.fragment.stat

import android.graphics.Color.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentStatBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun setChart() {
        val pieChartView = binding.pieChart
        pieDataSet = PieDataSet(pieDataList, "Spaced Repetition")
        pieDataSet.valueTextColor = WHITE
        pieDataSet.colors = colors
        pieDataSet.valueTextSize = 12f
        pieDataSet.sliceSpace = 5f
        pieDataSet.selectionShift = 5f
        val pieData = PieData(pieDataSet)
        pieData.setValueFormatter(MyValueFormatter())
        pieData.setValueTextColor(WHITE)
        //overall
        pieChartView.data = pieData
        pieChartView.setUsePercentValues(false)
        pieChartView.animateY(1400, Easing.EaseInOutQuad)
        pieChartView.description.isEnabled = false
        pieChartView.invalidate()
        val legend = pieChartView.legend
        legend.isEnabled = false
    }

    private fun init() {
        lifecycle.coroutineScope.launch {
            spacedViewModel.allKanji.collect {
                loadData(it)
            }
        }
    }

    private fun loadData(listKanji: List<SpacedEntity>) {
        pieDataList = ArrayList()
        val grade1 = listKanji.filter { it.Grade == 1 }
        val grade2 = listKanji.filter { it.Grade == 2 }
        val grade3 = listKanji.filter { it.Grade == 3 }
        val grade4 = listKanji.filter { it.Grade == 4 }
        val grade5 = listKanji.filter { it.Grade == 5 }
        val grade6 = listKanji.filter { it.Grade == 6 }
        if (grade1.isNotEmpty()){pieDataList.add(PieEntry(grade1.size.toFloat(), "grade1"))}
        if (grade2.isNotEmpty()){pieDataList.add(PieEntry(grade2.size.toFloat(), "grade2"))}
        if (grade3.isNotEmpty()){pieDataList.add(PieEntry(grade3.size.toFloat(), "grade3"))}
        if (grade4.isNotEmpty()){pieDataList.add(PieEntry(grade4.size.toFloat(), "grade4"))}
        if (grade5.isNotEmpty()){pieDataList.add(PieEntry(grade5.size.toFloat(), "grade5"))}
        if (grade6.isNotEmpty()){pieDataList.add(PieEntry(grade5.size.toFloat(), "grade6"))}
        colors = ArrayList()
        colors.add(ContextCompat.getColor(requireContext(), R.color.soft_red))
        colors.add(ContextCompat.getColor(requireContext(), R.color.seasonal_orange))
        colors.add(ContextCompat.getColor(requireContext(), R.color.natural_green))
        colors.add(ContextCompat.getColor(requireContext(), R.color.sky_light_blue))
        colors.add(ContextCompat.getColor(requireContext(), R.color.supreme_blue))
        setChart()
    }


}
