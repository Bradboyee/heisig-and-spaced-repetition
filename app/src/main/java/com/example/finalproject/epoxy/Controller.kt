package com.example.finalproject.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyController
import com.example.finalproject.R
import com.example.finalproject.epoxy.model.EpoxyKanjiItem_
import com.example.finalproject.epoxy.model.GridCarouselModel_
import com.example.finalproject.epoxy.model.KotlinModel
import com.example.finalproject.roomdatabase.KanjiEntity
import kotlin.properties.Delegates

class Controller:EpoxyController() {

    var kanjiController by Delegates.observable(emptyList<KanjiEntity>()){ _, _, _ ->
        requestModelBuild()
    }

    override fun buildModels() {
        val spacedStart = kanjiController.filter { it.spacedStatus == 0 }
        val spaced1Day = kanjiController.filter { it.spacedStatus == 1 }
        val spaced3Days = kanjiController.filter { it.spacedStatus == 2 }
        val spaced1Week = kanjiController.filter { it.spacedStatus == 3 }
        val spaced1Month = kanjiController.filter { it.spacedStatus == 4 }
        val spaced6Months = kanjiController.filter { it.spacedStatus == 5 }

        HeaderModel("START",spacedStart.size).id("HEADER_START").addTo(this)

        GridCarouselModel_()
            .id("CATEGORY_LIST")
//            .numViewsToShowOnScreen(3.2f)
            .models(spacedStart.map { kanji ->
                EpoxyKanjiItem_()
                    .id(kanji.id)
                    .kanji(kanji)
            })
            .addTo(this)

        HeaderModel("1 DAY",spaced1Day.size).id("HEADER_1DAY").addTo(this)

        GridCarouselModel_()
            .id("1DAY_KANJI")
            .models(spaced1Day.map { kanji ->
                KanjiItemModel(kanji.kanji).id(kanji.id)
            }).addTo(this)

        HeaderModel("1 DAY",spaced3Days.size).id("HEADER_1DAY").addTo(this)

        GridCarouselModel_()
            .id("1DAY_KANJI")
            .models(spaced3Days.map { kanji ->
                KanjiItemModel(kanji.kanji).id(kanji.id)
            }).addTo(this)


        HeaderModel("3 DAYS",spaced1Week.size).id("HEADER_3DAYS").addTo(this)

        GridCarouselModel_()
            .id("1DAY_KANJI")
            .models(spaced1Week.map { kanji ->
                KanjiItemModel(kanji.kanji).id(kanji.id)
            }).addTo(this)

        HeaderModel("1 WEEK",spaced1Month.size).id("HEADER_1WEEK").addTo(this)

        GridCarouselModel_()
            .id("1DAY_KANJI")
            .models(spaced1Month.map { kanji ->
                KanjiItemModel(kanji.kanji).id(kanji.id)
            }).addTo(this)

        HeaderModel("1 MONTH",spaced6Months.size).id("HEADER_1MONTH").addTo(this)

        GridCarouselModel_()
            .id("1DAY_KANJI")
            .models(spaced6Months.map { kanji ->
                KanjiItemModel(kanji.kanji).id(kanji.id)
            }).addTo(this)



    }
    data class HeaderModel(val topic : String, val total :Int):KotlinModel(R.layout.epoxy_header_kanji_item){
        private val textViewTopic by bind<TextView>(R.id.textView_topic)
        private val textViewTotal by bind<TextView>(R.id.textView_total_kanji)
        override fun bind() {
            textViewTopic.text = topic
            textViewTotal.text = "TOTAL : $total KANJI"
        }

    }
    data class KanjiItemModel(val kanji:String):KotlinModel(R.layout.epoxy_kanji_item){
        private val textViewKanji by bind<TextView>(R.id.textview_epoxy_kanji)
        override fun bind() {
            textViewKanji.text = kanji
        }

    }
}