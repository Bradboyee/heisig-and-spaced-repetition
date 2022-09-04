package com.example.finalproject.epoxy.controller

import android.widget.TextView
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
        private val kanjiTextView by bind<TextView>(R.id.textview_epoxy_kanji_list)
        override fun bind() {
            kanjiTextView.text = kanji
        }

    }
}