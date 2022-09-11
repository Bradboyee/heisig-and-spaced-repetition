package com.example.finalproject.fragment.spaced

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.navArgs
import com.example.finalproject.databinding.FragmentResultBinding
import com.example.finalproject.notification.alarm.AlarmManagerCall
import com.example.finalproject.viewmodel.SpacedViewModel
import com.example.finalproject.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding?= null
    private val binding get() = _binding!!
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val args by navArgs<ResultFragmentArgs>()
    private val spacedViewModel by viewModels<SpacedViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        sharedViewModel.clearViewModel()
        setNotification()
        return binding.root
    }

    private fun setNotification() {
        val mergeAnswer = args.correct!!.plus(args.wrong!!)
        for(item in mergeAnswer){
            lifecycle.coroutineScope.launch{
                spacedViewModel.allKanji.collect { updatedKanji ->
                    val updatedTime = updatedKanji.find { it.kanji == item.kanji }!!.spacedDate
                    val alarm = AlarmManagerCall(requireContext(),item,updatedTime)
                    alarm.startAlarm()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
