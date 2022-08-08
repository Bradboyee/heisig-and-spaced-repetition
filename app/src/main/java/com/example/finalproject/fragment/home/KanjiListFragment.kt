package com.example.finalproject.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.data_kanji.Data
import com.example.finalproject.databinding.FragmentKanjiListBinding
import com.example.finalproject.utils.KanjiRecyclerAdapter

class KanjiListFragment : Fragment() {
    private var _binding: FragmentKanjiListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentKanjiListBinding.inflate(inflater,container,false)
        val categoricalValue = arguments?.getInt("amount")
        binding.kanjiListRecyclerView.layoutManager = GridLayoutManager(this.context,5)
        binding.kanjiListRecyclerView.adapter = KanjiRecyclerAdapter(Data.kanji,categoricalValue!!)
        return binding.root
    }
}