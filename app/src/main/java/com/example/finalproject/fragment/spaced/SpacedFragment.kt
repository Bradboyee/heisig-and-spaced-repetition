package com.example.finalproject.fragment.spaced

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentSpacedBinding
import com.example.finalproject.epoxy.Controller
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.utils.KanjiListAdapter
import com.example.finalproject.viewmodel.KanjiViewModelFactory
import com.example.finalproject.viewmodel.KanjiViewModel
import kotlinx.coroutines.launch


class SpacedFragment : Fragment() {
    private var _binding: FragmentSpacedBinding? = null
    private val binding get() = _binding!!
    private lateinit var kanjiViewModel: KanjiViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSpacedBinding.inflate(inflater, container, false)
        val dao = KanjiDatabase.getInstance(requireContext()).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this, factory)[KanjiViewModel::class.java]
        val adapter = KanjiListAdapter()
        val epoxyRecyclerView = binding.epoxyRecyclerview
        lifecycle.coroutineScope.launch {
            kanjiViewModel.allKanji.collect { allKanji ->

                //epoxy
                adapter.submitList(allKanji)
                val controller = Controller().apply {
                    kanjiController = allKanji
                }
                epoxyRecyclerView.layoutManager = LinearLayoutManager(context)
                epoxyRecyclerView.setHasFixedSize(false)
                epoxyRecyclerView.setController(controller)
            }
        }
        lifecycle.coroutineScope.launch {
            kanjiViewModel.spacedKanji().collect { spacedKanji ->
                if (spacedKanji.isNullOrEmpty()) {
                    binding.floatingActionButton.setOnClickListener {
                        Toast.makeText(requireContext(),
                            "You don't have TODO today !",
                            Toast.LENGTH_SHORT).show()
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
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
