package com.thepparat.heisigwithspacedrepetition.epoxy.controller

import android.graphics.Color.WHITE
import android.graphics.Color.parseColor
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.airbnb.epoxy.EpoxyController
import com.thepparat.heisigwithspacedrepetition.R
import com.thepparat.heisigwithspacedrepetition.epoxy.model.KotlinModel
import kotlin.properties.Delegates

class ControllerKanjiList : EpoxyController() {
    var kanjiListController by Delegates.observable(emptyList<String>()) { _, _, _ ->
        requestModelBuild()
    }
    var existKanjiListController by Delegates.observable(emptyList<String>()) { _, _, _ ->
        requestModelBuild()
    }

    override fun buildModels() {
        for (item in kanjiListController) {
            KanjiItemModel(item, existKanjiListController).id(item).addTo(this)
        }
    }

    data class KanjiItemModel(val kanji: String, val exist: List<String>) :
        KotlinModel(R.layout.epoxy_category_kanji) {
        private val cardViewKanji by bind<CardView>(R.id.cardView_kanji)
        private val textViewKanji by bind<TextView>(R.id.textview_epoxy_kanji_list)
        override fun bind() {
            if (exist.contains(kanji)) {
                val colorArgent = parseColor("#C0C0C0")
                cardViewKanji.setCardBackgroundColor(colorArgent)
            } else cardViewKanji.setCardBackgroundColor(WHITE)

            textViewKanji.text = kanji
            textViewKanji.setOnClickListener {
                val bundle = bundleOf("chooseKanji" to kanji)
                it.findNavController()
                    .navigate(R.id.action_kanjiListFragment_to_heisigFragment, bundle)
            }
            cardViewKanji.setOnClickListener {
                val bundle = bundleOf("chooseKanji" to kanji)
                it.findNavController()
                    .navigate(R.id.action_kanjiListFragment_to_heisigFragment, bundle)
            }
        }
    }
}