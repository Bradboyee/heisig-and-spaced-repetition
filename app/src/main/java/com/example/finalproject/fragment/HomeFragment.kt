package com.example.finalproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.HeisigActivity
import com.example.finalproject.R
import com.example.finalproject.data_kanji.Data
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.recyclerview.CategoryRecyclerAdapter
import com.example.finalproject.ui.KanjiViewModel
import com.example.finalproject.ui.KanjiViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homefragrecyclerview.layoutManager = LinearLayoutManager(context)
        binding.homefragrecyclerview.adapter = CategoryRecyclerAdapter(Data.category)
        return binding.root
    }

}