package com.example.finalproject.fragment.spaced

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.finalproject.databinding.FragmentResultBinding
import com.example.finalproject.viewmodel.SharedViewModel

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding?= null
    private val binding get() = _binding!!
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val args by navArgs<ResultFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentResultBinding.inflate(inflater,container,false)
        sharedViewModel.clearViewModel()
        val correctTotal = args.correct!!.size
        val wrongTotal = args.wrong!!.size
        binding.textViewTotalCorrect.text = "Point ${correctTotal}/${correctTotal+wrongTotal}"
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
