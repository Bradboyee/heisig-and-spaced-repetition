package com.example.finalproject.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cesarferreira.tempo.Tempo
import com.example.finalproject.databinding.FragmentHeisigBinding

class HeisigFragment : Fragment() {
    private var _binding: FragmentHeisigBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentHeisigBinding.inflate(inflater,container,false)
        initUI()
        addHeisigToSpaced()
        return binding.root
    }

    private fun initUI() {
        val data =  arguments?.getString("chooseKanji")
        binding.textView2.text = data
        //epoxy
//        val epoxyRecyclerView = binding.recyclerviewHeisig
//        val controller = ControllerHeisig().apply {  }
//        epoxyRecyclerView.layoutManager = LinearLayoutManager(context)
//        epoxyRecyclerView.setHasFixedSize(false)
//        epoxyRecyclerView.setController(controller)
    }

    private fun addHeisigToSpaced() {
        val addDate = Tempo.now // collect date that user add to Spaced
        val spacedDate = Tempo.now //when user add kanji this is the time that user can do , If u add today user can do the quiz today
        }
    }