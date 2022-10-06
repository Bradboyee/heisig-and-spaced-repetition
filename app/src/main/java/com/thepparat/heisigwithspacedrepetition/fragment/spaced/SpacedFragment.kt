package com.thepparat.heisigwithspacedrepetition.fragment.spaced

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thepparat.heisigwithspacedrepetition.R
import com.thepparat.heisigwithspacedrepetition.databinding.FragmentSpacedBinding
import com.thepparat.heisigwithspacedrepetition.epoxy.controller.ControllerSpaced
import com.thepparat.heisigwithspacedrepetition.viewmodel.SpacedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SpacedFragment : Fragment() {
    private var _binding: FragmentSpacedBinding? = null
    private val binding get() = _binding!!
    private val spacedViewModel by viewModels<SpacedViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSpacedBinding.inflate(inflater, container, false)
        initEpoxy()
        spacedButton()
        return binding.root
    }

    private fun initEpoxy() {
        val epoxyRecyclerView = binding.epoxyRecyclerview
        val controllerSpaced = ControllerSpaced()
        epoxyRecyclerView.setHasFixedSize(false)
        epoxyRecyclerView.setController(controllerSpaced)
        lifecycle.coroutineScope.launch {
            spacedViewModel.allKanji.collect { allKanji ->
                //epoxy
                controllerSpaced.apply { kanjiController = allKanji }
                epoxyRecyclerView.layoutManager = LinearLayoutManager(context)
                epoxyRecyclerView.setHasFixedSize(false)
                epoxyRecyclerView.setController(controllerSpaced)
            }
        }
    }

    private fun spacedButton() {
        lifecycle.coroutineScope.launch {
            spacedViewModel.getSpacedTodo().collect { spacedKanji ->
                if (spacedKanji.isEmpty()) {
                    binding.floatingActionButton.setOnClickListener {
                        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                        Snackbar.make(requireView(),"You don't have Todo.",Snackbar.LENGTH_SHORT).apply { anchorView = bottomNavigationView }
                            .show()
                    }
                } else {
                    binding.floatingActionButton.setOnClickListener {
                        val action =
                            SpacedFragmentDirections.actionSpacedFragmentToQuizFragment(spacedKanji.toTypedArray())
                        findNavController().navigate(action)
                    }
                }

            }
        }
    }

}
