package com.example.finalproject.fragment.stat

import android.graphics.Color.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentStatBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.viewmodel.KanjiViewModel
import com.example.finalproject.viewmodel.KanjiViewModelFactory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch


class StatFragment : Fragment() {
    private var _binding: FragmentStatBinding? = null
    private val binding get() = _binding!!
    private lateinit var kanjiViewModel : KanjiViewModel
    private lateinit var pieDataList : ArrayList<PieEntry>
    private lateinit var pieDataSet: PieDataSet
    private lateinit var colors: ArrayList<Int>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentStatBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }

    private fun setChart() {
        val pieChartView = binding.pieChart
        pieDataSet = PieDataSet(pieDataList,"Spaced Repetition")
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
        val dao = KanjiDatabase.getInstance(requireContext()).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this, factory)[KanjiViewModel::class.java]
        lifecycle.coroutineScope.launch {
            kanjiViewModel.allKanji.collect{
                loadData(it)
            }
        }
    }

    private fun loadData(listKanji: List<KanjiEntity>) {
        val n1 = listKanji.filter { it.Japanese_Language_Proficiency_Test == 1 }.size
        val n2 = listKanji.filter { it.Japanese_Language_Proficiency_Test == 2 }.size
        val n3 = listKanji.filter { it.Japanese_Language_Proficiency_Test == 3 }.size
        val n4 = listKanji.filter { it.Japanese_Language_Proficiency_Test == 4 }.size
        val n5 = listKanji.filter { it.Japanese_Language_Proficiency_Test == 5 }.size
        pieDataList = ArrayList()
        pieDataList.add(PieEntry(n1.toFloat(),"N1"))
        pieDataList.add(PieEntry(n2.toFloat(),"N2"))
        pieDataList.add(PieEntry(n3.toFloat(),"N3"))
        pieDataList.add(PieEntry(n4.toFloat(),"N4"))
        pieDataList.add(PieEntry(n5.toFloat(),"N5"))
        colors = ArrayList()
        colors.add(ContextCompat.getColor(requireContext(),R.color.soft_red))
        colors.add(ContextCompat.getColor(requireContext(),R.color.seasonal_orange))
        colors.add(ContextCompat.getColor(requireContext(),R.color.natural_green))
        colors.add(ContextCompat.getColor(requireContext(),R.color.sky_light_blue))
        colors.add(ContextCompat.getColor(requireContext(),R.color.supreme_blue))
        setChart()
    }


}
