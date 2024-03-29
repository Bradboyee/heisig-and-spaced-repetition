package com.thepparat.heisigwithspacedrepetition.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.thepparat.heisigwithspacedrepetition.databinding.FragmentHomeBinding
import com.thepparat.heisigwithspacedrepetition.epoxy.controller.ControllerHomeCategory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initEpoxy()
        return binding.root
    }

    private fun initEpoxy() {
        val epoxyRecyclerView = binding.homeFragRecyclerview
        val controller = ControllerHomeCategory()
        epoxyRecyclerView.layoutManager = LinearLayoutManager(context)
        epoxyRecyclerView.setHasFixedSize(false)
        epoxyRecyclerView.setController(controller)
        controller.requestModelBuild()
    }

}