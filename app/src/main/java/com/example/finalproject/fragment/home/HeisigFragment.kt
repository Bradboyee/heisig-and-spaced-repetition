package com.example.finalproject.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesarferreira.tempo.Tempo
import com.example.finalproject.databinding.FragmentHeisigBinding
import com.example.finalproject.epoxy.ControllerHeisig
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.viewmodel.KanjiViewModel
import com.example.finalproject.viewmodel.KanjiViewModelFactory

class HeisigFragment : Fragment() {
    private var _binding: FragmentHeisigBinding?=null
    private val binding get() = _binding!!
    private val args by navArgs<HeisigFragmentArgs>()
    private lateinit var kanjiViewModel: KanjiViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentHeisigBinding.inflate(inflater,container,false)
        initUI()
        addHeisigToSpaced()
        return binding.root
    }

    private fun initUI() {
        val dao = KanjiDatabase.getInstance(requireContext()).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]
        //epoxy
        val epoxyRecyclerView = binding.recyclerviewHeisig
        val controller = ControllerHeisig().apply { kanjiController = listOf(args.selectedHeisig) }
        epoxyRecyclerView.layoutManager = LinearLayoutManager(context)
        epoxyRecyclerView.setHasFixedSize(false)
        epoxyRecyclerView.setController(controller)
    }

    private fun addHeisigToSpaced() {
        val addDate = Tempo.now // collect date that user add to Spaced
        val spacedDate = Tempo.now //when user add kanji this is the time that user can do , If u add today user can do the quiz today
        val changedObject = KanjiEntity(
            0,
            kanji = args.selectedHeisig.kanji,
            Japanese_Language_Proficiency_Test = args.selectedHeisig.Japanese_Language_Proficiency_Test,
            kanjiReadingKun = args.selectedHeisig.kanjiReadingKun,
            kanjiReadingOn = args.selectedHeisig.kanjiReadingOn,
            kanjiMeaning = args.selectedHeisig.kanjiMeaning,
            component1kanji = args.selectedHeisig.component1kanji,
            component1ReadingKun = args.selectedHeisig.component1ReadingKun,
            component1ReadingOn = args.selectedHeisig.component1ReadingOn,
            component1Meaning = args.selectedHeisig.component1Meaning,
            component2kanji = args.selectedHeisig.component2kanji,
            component2ReadingKun = args.selectedHeisig.component2ReadingKun,
            component2ReadingOn = args.selectedHeisig.component2ReadingOn,
            component2Meaning = args.selectedHeisig.component2Meaning,
            story = args.selectedHeisig.story,
            spacedStatus = 0,
            addDate = addDate,
            spacedDate = spacedDate)
            binding.fabAdd.setOnClickListener {
                kanjiViewModel.insert(changedObject)
            }
        }
    }