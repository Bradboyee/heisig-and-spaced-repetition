package com.example.finalproject.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.cesarferreira.tempo.Tempo
import com.example.finalproject.databinding.FragmentHeisigBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.viewmodel.KanjiViewModel
import com.example.finalproject.viewmodel.KanjiViewModelFactory

class HeisigFragment : Fragment() {
    private var _binding:FragmentHeisigBinding?=null
    private val binding get() = _binding!!
    private val args by navArgs<HeisigFragmentArgs>()
    private lateinit var kanjiViewModel: KanjiViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentHeisigBinding.inflate(inflater,container,false)
        binding.tvKanji.text = args.selectedHeisig.kanji
        binding.tvMeaning.text = args.selectedHeisig.kanjiMeaning
        binding.tvReadingKun.text = args.selectedHeisig.kanjiReadingKun
        binding.tvReadingOn.text = args.selectedHeisig.kanjiReadingOn
        addHeisigToSpaced()
        return binding.root
    }

    private fun addHeisigToSpaced() {
        val dao = KanjiDatabase.getInstance(requireContext()).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]
        val addDate = Tempo.now // collect date that user add to Spaced
        val spacedDate = Tempo.now //when user add kanji this is the time that user can do , If u add today user can do the quiz today
        val changedObject = KanjiEntity(
            0,
            args.selectedHeisig.kanji,
            args.selectedHeisig.Japanese_Language_Proficiency_Test,
            args.selectedHeisig.kanjiReadingKun,
            args.selectedHeisig.kanjiReadingOn,
            args.selectedHeisig.kanjiMeaning,
            args.selectedHeisig.component1,
            args.selectedHeisig.component1ReadingKun,
            args.selectedHeisig.component1ReadingOn,
            args.selectedHeisig.component1Meaning,
            args.selectedHeisig.component2,
            args.selectedHeisig.component2ReadingKun,
            args.selectedHeisig.component2ReadingOn,
            args.selectedHeisig.component2Meaning,
            args.selectedHeisig.story,
            0,
            addDate,
            spacedDate)
        kanjiViewModel.kanjiList.observe(viewLifecycleOwner,{
            binding.addFab.setOnClickListener {
                kanjiViewModel.insert(changedObject)
            }
        })
    }
}