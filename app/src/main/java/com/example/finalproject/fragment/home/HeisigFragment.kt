package com.example.finalproject.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesarferreira.tempo.Tempo
import com.example.finalproject.databinding.FragmentHeisigBinding
import com.example.finalproject.epoxy.controller.ControllerHeisig
import com.example.finalproject.json.Kanji2Element
import com.example.finalproject.json.RadicalJson
import com.example.finalproject.json.jsonpojo.PojoRadicalItem
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.example.finalproject.roomdatabase.roomentity.Status
import com.example.finalproject.viewmodel.SpacedViewModel
import com.example.finalproject.viewmodel.ViewModelKanjiAlive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeisigFragment : Fragment() {
    private var _binding: FragmentHeisigBinding? = null
    private val binding get() = _binding!!
    private val spacedViewModel by viewModels<SpacedViewModel>()
    private val viewModelKanjiAlive by viewModels<ViewModelKanjiAlive>()
    private lateinit var controller : ControllerHeisig

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHeisigBinding.inflate(inflater, container, false)
        val data = arguments?.getString("chooseKanji")
        initUI(data)
        return binding.root
    }

    private fun initUI(data: String?) {
        val recyclerView = binding.epoxyRecyclerviewHeisig
        controller = ControllerHeisig()
        //Json Fetch
        lifecycle.coroutineScope.launch {
            fetchApi(data)
            spacedViewModel.story(data!!).collect{
                controller.story = it
                fetchApi(data)
            }
        }
        //epoxy
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setController(controller)
        controller.requestModelBuild()
    }

    private fun fetchApi(data: String?) {
        val mapRadical: HashMap<String, PojoRadicalItem?> = HashMap()
        val elementList = Kanji2Element(requireContext()).getElement(data!!)
        Log.i("ELEMENT", elementList.toString())
        val radical = RadicalJson(requireContext())
        if (elementList != null) {
            elementList.map { mapRadical.put(it, radical.getRadicalDetail(it)) }
            Log.i("RADICAL LIST", mapRadical.toString())
            controller.mapRadicalController = mapRadical
        }
            viewModelKanjiAlive.fetchRapid(data)
            viewModelKanjiAlive.kanjiAlive.observe(viewLifecycleOwner) { kanjiAlive ->
                controller.kanjiDetail = kanjiAlive
                binding.floatingActionAdd.setOnClickListener {
                    val status = Status(0, Tempo.now, Tempo.now, 0, 0)
                    val spacedData = SpacedEntity(0,
                        kanji = kanjiAlive!!.kanji.character,
                        Grade = kanjiAlive.references.grade,
                        kanjiMeaning = kanjiAlive.kanji.meaning.english, status)
                    spacedViewModel.insert(spacedData)
                }
            }
            viewModelKanjiAlive.errorMessage.observe(viewLifecycleOwner) { controller.errorMassage = it }
    }
}