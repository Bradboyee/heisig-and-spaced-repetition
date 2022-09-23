package com.example.finalproject.fragment.spaced

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentResultBinding
import com.example.finalproject.epoxy.controller.ControllerResult
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
        initEpoxy()
        setNotification()
        return binding.root
    }

    private fun initEpoxy() {
        val recyclerview = binding.epoxyRecyclerviewResult
        val controller = ControllerResult()
        controller.apply {
            correct = args.correctResult!!.toList()
            wrong = args.wrongResult!!.toList()
        }
        recyclerview.layoutManager = LinearLayoutManager(this.context)
        recyclerview.setController(controller)
        recyclerview.requestModelBuild()
    }

    private fun setNotification() {
        val mergeAnswer = args.correctResult!!.plus(args.wrongResult!!)
        for(item in mergeAnswer){
            lifecycle.coroutineScope.launch{
                spacedViewModel.getSpacedKanji(item.kanji).collect { updatedSpaced ->
                    val updatedTime = updatedSpaced.status.spacedDate
                    val alarm = AlarmManagerCall(requireContext(),updatedSpaced,updatedTime)
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
