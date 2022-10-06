package com.thepparat.heisigwithspacedrepetition.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.thepparat.heisigwithspacedrepetition.databinding.FragmentKanjiListBinding
import com.thepparat.heisigwithspacedrepetition.epoxy.controller.ControllerKanjiList
import com.thepparat.heisigwithspacedrepetition.derialization.AllKanjiJson
import com.thepparat.heisigwithspacedrepetition.viewmodel.SpacedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KanjiListFragment : Fragment() {
    private var _binding: FragmentKanjiListBinding? = null
    private val binding get() = _binding!!
    private lateinit var argumentsValue : String
    private val spacedViewModel by viewModels<SpacedViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentKanjiListBinding.inflate(inflater,container,false)
        argumentsValue = arguments?.getString("category").toString()
        val categoryValue = getCategoryValue()
        val gradeKanjiList = feedJsonFile(categoryValue)
        setTitleBar()
        initEpoxy(gradeKanjiList,categoryValue)
        return binding.root
    }

    private fun setTitleBar() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        val title = argumentsValue.lowercase()
        actionBar!!.title = title[0].uppercase()+title.substring(1)
    }

    private fun feedJsonFile(grade: Int): List<String> {
        val data = AllKanjiJson(requireContext())
        return data.getGrade(grade)
    }

    private fun getCategoryValue(): Int {
        val category = when (argumentsValue) {
            "GRADE 1" -> 1
            "GRADE 2" -> 2
            "GRADE 3" -> 3
            "GRADE 4" -> 4
            "GRADE 5" -> 5
            "GRADE 6" -> 6
            else -> 0
        }
        return category
    }
    private fun initEpoxy(kanjiList: List<String>, categoryValue: Int) {
        val recyclerView = binding.epoxyRecyclerviewKanjiList
        val controller = ControllerKanjiList()
        controller.apply { kanjiListController = kanjiList }
        lifecycle.coroutineScope.launch{
            spacedViewModel.getAllCharacter(categoryValue).collect{
                controller.apply { existKanjiListController = it }
                Log.i("EXIST IN ROOM",it.toString())
            }
        }
        recyclerView.layoutManager = GridLayoutManager(requireContext(),5)
        recyclerView.setController(controller)
    }
}