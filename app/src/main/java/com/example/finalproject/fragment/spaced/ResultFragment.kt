package com.example.finalproject.fragment.spaced

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import com.example.finalproject.databinding.FragmentResultBinding
import com.example.finalproject.notification.alarm.AlarmManagerCall
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.viewmodel.KanjiViewModel
import com.example.finalproject.viewmodel.KanjiViewModelFactory
import com.example.finalproject.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding?= null
    private val binding get() = _binding!!
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val args by navArgs<ResultFragmentArgs>()
    private lateinit var kanjiViewModel: KanjiViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        sharedViewModel.clearViewModel()
        init()
        bindView()
        setNotification()
        return binding.root
    }

    private fun setNotification() {
        val mergeAnswer = args.correct!!.plus(args.wrong!!)
        for(item in mergeAnswer){
            lifecycle.coroutineScope.launch{
                kanjiViewModel.allKanji.collect { updatedKanji ->
                    val updatedTime = updatedKanji.find { it.kanji == item.kanji }!!.spacedDate
                    val alarm = AlarmManagerCall(requireContext(),item,updatedTime)
                    alarm.startAlarm()
                }
            }
        }
    }

    private fun init() {
        val dao = KanjiDatabase.getInstance(requireContext()).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this, factory)[KanjiViewModel::class.java]
    }

    private fun bindView() {
        val correctTotal = args.correct!!.size
        val wrongTotal = args.wrong!!.size
        binding.textViewTotalCorrect.text = "Point ${correctTotal}/${correctTotal + wrongTotal}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
