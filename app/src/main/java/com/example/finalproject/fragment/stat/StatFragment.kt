package com.example.finalproject.fragment.stat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.finalproject.databinding.FragmentStatBinding
import com.example.finalproject.epoxy.controller.ControllerStat
import com.example.finalproject.viewmodel.SpacedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatFragment : Fragment() {
    private var _binding: FragmentStatBinding? = null
    private val binding get() = _binding!!
    private val spacedViewModel by viewModels<SpacedViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStatBinding.inflate(inflater, container, false)
        initEpoxy()
        return binding.root
    }

    private fun initEpoxy() {
        val recyclerView = binding.epoxyStat
        val controller = ControllerStat()
        lifecycle.coroutineScope.launch {
            spacedViewModel.allKanji.collect {
                controller.apply { spacedKanji = it }
                recyclerView.setController(controller)
                controller.requestModelBuild()
            }
        }
    }


}
