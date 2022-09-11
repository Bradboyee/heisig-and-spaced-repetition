package com.example.finalproject.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.FragmentHeisigBinding
import com.example.finalproject.epoxy.controller.ControllerHeisig
import com.example.finalproject.json.Kanji2Element
import com.example.finalproject.json.RadicalJson
import com.example.finalproject.json.jsonpojo.PojoRadicalItem
import com.example.finalproject.viewmodel.SpacedViewModel
import com.example.finalproject.viewmodel.ViewModelKanjiAlive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeisigFragment : Fragment() {
    private var _binding: FragmentHeisigBinding? = null
    private val binding get() = _binding!!
    private val spacedViewModel by viewModels<SpacedViewModel>()
    private lateinit var viewModelKanjiAlive: ViewModelKanjiAlive

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHeisigBinding.inflate(inflater, container, false)
        val data = arguments?.getString("chooseKanji")
        initUI(data)
        return binding.root
    }

    private fun initUI(data: String?) {
        viewModelKanjiAlive = ViewModelProvider(this)[ViewModelKanjiAlive::class.java]
        val recyclerView = binding.epoxyRecyclerviewHeisig
        val mapRadical: HashMap<String, PojoRadicalItem?> = HashMap()
        val controller = ControllerHeisig()
        //Json Fetch
        val elementList = Kanji2Element(requireContext()).getElement(data!!)
        Log.i("ELEMENT", elementList.toString())
        val radical = RadicalJson(requireContext())
        if (elementList != null) {
            elementList.map {
                mapRadical.put(it, radical.getRadicalDetail(it))
            }
            Log.i("RADICAL LIST", mapRadical.toString())
            controller.mapRadicalController = mapRadical
        }

        lifecycle.coroutineScope.launch {
            viewModelKanjiAlive.fetchRapid(data)
            viewModelKanjiAlive.kanjiAlive.observe(viewLifecycleOwner) {
                controller.kanjiDetail = it
                binding.floatingActionAdd.setOnClickListener {
                }
            }
            viewModelKanjiAlive.errorMessage.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        //epoxy
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setController(controller)
        controller.requestModelBuild()
    }
}