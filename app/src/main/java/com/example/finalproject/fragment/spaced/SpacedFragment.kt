package com.example.finalproject.fragment.spaced

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.databinding.FragmentSpacedBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.utils.KanjiListAdapter
import com.example.finalproject.viewmodel.KanjiViewModelFactory
import com.example.finalproject.viewmodel.KanjiViewModel
import io.github.emusute1212.lifecyclelogger.Logger
import kotlinx.coroutines.launch


class SpacedFragment : Fragment() {
    private var _binding: FragmentSpacedBinding? = null
    private val binding get() = _binding!!
    private lateinit var kanjiViewModel: KanjiViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        Logger.bind(this)
        _binding = FragmentSpacedBinding.inflate(inflater, container, false)
        val dao = KanjiDatabase.getInstance(requireContext()).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this, factory)[KanjiViewModel::class.java]
        val adapter = KanjiListAdapter()
        binding.spacedRecyclerView.adapter = adapter
        binding.spacedRecyclerView.layoutManager = GridLayoutManager(this.context,5)
        lifecycle.coroutineScope.launch{
            kanjiViewModel.spacedKanji().collect {
                adapter.submitList(it)
            }

        }
        kanjiViewModel.allKanji.observe(viewLifecycleOwner,{
            kanji ->
            binding.floatingActionButton.setOnClickListener {
                Toast.makeText(this.context,kanji.map { it.kanji }.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        kanjiViewModel.kanjiList.observe(viewLifecycleOwner,{
            data ->
            binding.floatingActionButton2.setOnClickListener {
                Toast.makeText(this.context,data.map { it.kanji }.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
