package com.example.finalproject.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.databinding.FragmentHeisigBinding

class HeisigFragment : Fragment() {
    private var _binding:FragmentHeisigBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentHeisigBinding.inflate(inflater,container,false)
        return binding.root
    }
}