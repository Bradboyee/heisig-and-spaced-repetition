package com.example.finalproject.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.databinding.FragmentKanjiListBinding
import com.example.finalproject.epoxy.controller.ControllerKanjiList
import com.example.finalproject.json.AllKanjiJson

class KanjiListFragment : Fragment() {
    private var _binding: FragmentKanjiListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentKanjiListBinding.inflate(inflater,container,false)
        val categoryValue = getCategoryValue()
        val gradeKanjiList = feedJsonFile(categoryValue)
        initEpoxy(gradeKanjiList)
        return binding.root
    }

    private fun feedJsonFile(grade: Int): List<String> {
        val data = AllKanjiJson(requireContext())
        return data.getGrade(grade)
    }

    private fun getCategoryValue(): Int {
        val category = when (arguments?.getString("category")) {
            "GRADE 1" -> 1
            "GRADE 2" -> 2
            "GRADE 3" -> 3
            "GRADE 4" -> 4
            "GRADE 5" -> 5
            "GRADE 6" -> 6
            else -> {
                0
            }
        }
        return category
    }
    private fun initEpoxy(kanjiList: List<String>) {
        val recyclerView = binding.epoxyRecyclerviewKanjiList
        val controller = ControllerKanjiList()
        controller.apply { kanjiListController = kanjiList }
        recyclerView.layoutManager = GridLayoutManager(requireContext(),5)
        recyclerView.setController(controller)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}