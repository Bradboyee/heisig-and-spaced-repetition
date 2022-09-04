package com.example.finalproject.epoxy.controller

import android.widget.TextView
import com.airbnb.epoxy.EpoxyController
import com.example.finalproject.R
import com.example.finalproject.data_kanji.Kanji
import com.example.finalproject.epoxy.model.KotlinModel
import kotlin.properties.Delegates

class ControllerHeisig:EpoxyController() {

    var kanjiController by Delegates.observable(emptyList<Kanji>()){ _, _, _ ->
        requestModelBuild()
    }

    override fun buildModels() {
        for(kanji in kanjiController){
            HeisigKanji(kanji.component1kanji,kanji.component1Meaning,kanji.component1ReadingOn,kanji.component1ReadingKun).id(kanji.kanji).addTo(this)
            HeisigLabel("+").id("PLUS").addTo(this)
            HeisigKanji(kanji.component2kanji,kanji.component2Meaning,kanji.component2ReadingOn,kanji.component2ReadingKun).id(kanji.kanji).addTo(this)
            HeisigLabel("=").id("EQUAL").addTo(this)
            HeisigKanji(kanji.kanji,kanji.kanjiMeaning,kanji.kanjiReadingOn,kanji.kanjiReadingKun).id(kanji.kanji).addTo(this)

        }
    }
    data class HeisigKanji(val kanji:String,val meaning:String,val readingOn:String,val readingKun:String):KotlinModel(R.layout.epoxy_heisig_kanji){
        private val textViewKanji by bind<TextView>(R.id.tvKanji)
        private val textViewMeaning by bind<TextView>(R.id.tvReadingKun)
        private val textViewReadingKun by bind<TextView>(R.id.tvReadingKun)
        private val textViewReadingOn by bind<TextView>(R.id.tvReadingOn)
        override fun bind() {
            textViewKanji.text = kanji
            textViewMeaning.text = meaning
            textViewReadingKun.text = readingKun
            textViewReadingOn.text = readingOn
        }
    }
    data class HeisigLabel(val text:String):KotlinModel(R.layout.epoxy_heisig_label){
        private val textViewText by bind<TextView>(R.id.textView)
        override fun bind() {
            textViewText.text = text
        }

    }
}