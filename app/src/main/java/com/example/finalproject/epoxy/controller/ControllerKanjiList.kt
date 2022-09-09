package com.example.finalproject.epoxy.controller

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.airbnb.epoxy.EpoxyController
import com.example.finalproject.R
import com.example.finalproject.epoxy.model.KotlinModel
import kotlin.properties.Delegates

class ControllerKanjiList:EpoxyController() {
    var kanjiListController by Delegates.observable(emptyList<String>()){_,_,_ ->
        requestModelBuild()
    }
    override fun buildModels() {
        for(item in kanjiListController){
            KanjiItemModel(item).id(item).addTo(this)
        }
    }
    data class KanjiItemModel(val kanji:String): KotlinModel(R.layout.epoxy_kanji_list_item){
        private val cardViewKanji by bind<CardView>(R.id.cardView_kanji)
        private val TextViewkanji by bind<TextView>(R.id.textview_epoxy_kanji_list)
        override fun bind() {
            TextViewkanji.text = kanji
            TextViewkanji.setOnClickListener {
                val bundle = bundleOf("chooseKanji" to kanji)
                it.findNavController().navigate(R.id.action_kanjiListFragment_to_heisigFragment,bundle)
            }
            cardViewKanji.setOnClickListener {
                val bundle = bundleOf("chooseKanji" to kanji)
                it.findNavController().navigate(R.id.action_kanjiListFragment_to_heisigFragment,bundle)
            }
        }
    }
}