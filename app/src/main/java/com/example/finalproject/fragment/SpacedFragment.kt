package com.example.finalproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.Recyclerview.SpacedRecyclerAdapter
import com.example.finalproject.databinding.FragmentSpacedBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.ui.KanjiViewModel
import com.example.finalproject.ui.KanjiViewModelFactory

class SpacedFragment : Fragment() {
    private var _binding : FragmentSpacedBinding?= null
    private val binding get() = _binding!!
    lateinit var kanjiViewModel: KanjiViewModel
    lateinit var SpacedRecyclerAdapter : SpacedRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSpacedBinding.inflate(inflater,container,false)
        initUI()
        getData()
        return binding.root
    }

    private fun initUI() {
        val dao = KanjiDatabase.getInstance(requireContext()).dao()
        val repository =KanjiRepository(dao)
        val factory =KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]
    }

    private fun getData() {
        SpacedRecyclerAdapter = SpacedRecyclerAdapter()
        binding.spacedrecyclerview.layoutManager = LinearLayoutManager(context)
        kanjiViewModel.kanjiList.observe(viewLifecycleOwner, {
            SpacedRecyclerAdapter.submitlist(it)
            binding.spacedrecyclerview.adapter = SpacedRecyclerAdapter
        })
    }


}