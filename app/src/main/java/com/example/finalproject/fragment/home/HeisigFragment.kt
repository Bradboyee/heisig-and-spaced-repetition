package com.example.finalproject.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesarferreira.tempo.Tempo
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHeisigBinding
import com.example.finalproject.epoxy.controller.ControllerHeisig
import com.example.finalproject.json.Kanji2Element
import com.example.finalproject.json.RadicalJson
import com.example.finalproject.json.jsonpojo.PojoRadicalItem
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.example.finalproject.roomdatabase.roomentity.Status
import com.example.finalproject.viewmodel.SpacedViewModel
import com.example.finalproject.viewmodel.ViewModelKanjiAlive
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HeisigFragment : Fragment() {
    private var _binding: FragmentHeisigBinding? = null
    private val binding get() = _binding!!
    private val spacedViewModel by viewModels<SpacedViewModel>()
    private val viewModelKanjiAlive by viewModels<ViewModelKanjiAlive>()
    private lateinit var controller: ControllerHeisig
    private lateinit var argsValue: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHeisigBinding.inflate(inflater, container, false)
        argsValue = arguments?.getString("chooseKanji").toString()
        initUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_heisig, menu)
                setIcon(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_add -> {
                        insertOrDelete()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setIcon(menu: Menu) {
        lifecycle.coroutineScope.launch {
            spacedViewModel.exist(argsValue).collect { isExist ->
                if (isExist) menu[0].setIcon(R.drawable.ic_baseline_favorite_24)
                else menu[0].setIcon(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }

    private fun insertOrDelete() {
        lifecycle.coroutineScope.launch {
            spacedViewModel.exist(argsValue).collect { isExist ->
                if (isExist) {
                    val bottomNavView: BottomNavigationView = activity?.findViewById(R.id.bottomNavigationView)!!
                    Snackbar.make(requireView(),"$argsValue : You have already added :D",Snackbar.LENGTH_SHORT).apply { anchorView = bottomNavView }.show()
                } else {
                    //insert
                    viewModelKanjiAlive.kanjiAlive.observe(viewLifecycleOwner) {
                        val status = Status(spacedStatus = 0,
                            addDate = Tempo.now,
                            spacedDate = Tempo.now,
                            correct = 0,
                            wrong = 0)
                        val spaced = SpacedEntity(id = 0,
                            kanji = it!!.kanji.character,
                            Grade = it.references.grade,
                            kanjiMeaning = it.kanji.meaning.english,
                            status)
                        spacedViewModel.insertSpaced(spaced)
                    }
                    val heartAnim = binding.heartFall
                    heartAnim.playAnimation()
                }
            }
        }
    }

    private fun initUI() {
        val recyclerView = binding.epoxyRecyclerviewHeisig
        controller = ControllerHeisig()
        //Json Fetch
        lifecycle.coroutineScope.launch {
            fetchApi()
            spacedViewModel.story(argsValue).collect { controller.story = it; fetchApi() }
        }
        //epoxy
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setController(controller)
        controller.requestModelBuild()
    }

    private fun fetchApi() {
        val mapRadical: HashMap<String, PojoRadicalItem?> = HashMap()
        val elementList = Kanji2Element(requireContext()).getElement(argsValue)
        Log.i("ELEMENT", elementList.toString())
        val radical = RadicalJson(requireContext())
        if (elementList != null) {
            elementList.map { mapRadical.put(it, radical.getRadicalDetail(it)) }
            Log.i("RADICAL LIST", mapRadical.toString())
            controller.mapRadicalController = mapRadical
        }
        viewModelKanjiAlive.fetchRapid(argsValue)
        viewModelKanjiAlive.kanjiAlive.observe(viewLifecycleOwner) { controller.kanjiDetail = it }
        viewModelKanjiAlive.errorMessage.observe(viewLifecycleOwner) {
            controller.errorMassage = it
        }
    }
}
