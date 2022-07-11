package com.example.finalproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.databinding.FragmentSpacedBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.ui.KanjiViewModel
import com.example.finalproject.ui.KanjiViewModelFactory
import com.example.finalproject.utils.SpacedRecyclerAdapter


class SpacedFragment : Fragment() {
    lateinit var kanjiViewModel : KanjiViewModel
    lateinit var spacedRecyclerAdapter : SpacedRecyclerAdapter
    private var _binding : FragmentSpacedBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentSpacedBinding.inflate(inflater,container,false)
        initUI()
        return binding.root
    }
    private fun initUI() {
        val dao = KanjiDatabase.getInstance(requireContext()).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]
        getData()
    }

    private fun getData() {
        spacedRecyclerAdapter = SpacedRecyclerAdapter()
        binding.spacedrecyclerview.layoutManager = GridLayoutManager(context,5)
        kanjiViewModel.kanjiList.observe(viewLifecycleOwner, {
            spacedRecyclerAdapter.submitlist(it)
            binding.spacedrecyclerview.adapter = spacedRecyclerAdapter
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //test update git
}
