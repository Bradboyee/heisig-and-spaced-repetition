package com.example.finalproject.epoxy.model

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.finalproject.R
import com.example.finalproject.roomdatabase.KanjiEntity

@EpoxyModelClass(layout = R.layout.epoxy_kanji_item)
abstract class EpoxyKanjiItem:EpoxyModelWithHolder<Holder>() {
    @EpoxyAttribute lateinit var kanji:KanjiEntity
    override fun bind(holder: Holder) {
        with(holder){
            this.titleView.text = kanji.kanji
        }
    }
}

class Holder : KotlinEpoxyHolder() {
    val titleView by bind<TextView>(R.id.textview_epoxy_kanji)
}
