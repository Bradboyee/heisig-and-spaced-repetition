package com.example.finalproject.epoxy.controller

import android.graphics.Color.*
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyController
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.toString
import com.example.finalproject.R
import com.example.finalproject.epoxy.model.GridCarouselModel_
import com.example.finalproject.epoxy.model.KotlinModel
import com.example.finalproject.roomdatabase.SpacedEntity
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class ControllerSpaced : EpoxyController() {

    var kanjiController by Delegates.observable(emptyList<SpacedEntity>()) { _, _, _ ->
        requestModelBuild()
    }

    override fun buildModels() {
        val spacedStart = kanjiController.filter { it.spacedStatus == 0 }
        val spaced1Day = kanjiController.filter { it.spacedStatus == 1 }
        val spaced3Days = kanjiController.filter { it.spacedStatus == 2 }
        val spaced1Week = kanjiController.filter { it.spacedStatus == 3 }
        val spaced1Month = kanjiController.filter { it.spacedStatus == 4 }
        val spaced6Months = kanjiController.filter { it.spacedStatus == 5 }

        HeaderModel("START", spacedStart.size).id("HEADER_START").addTo(this)

        GridCarouselModel_()
            .id("START_KANJI")
            .models(spacedStart.map { kanji ->
                KanjiItemModel(kanji).id(kanji.id)
            }).addTo(this)

        HeaderModel("1 DAY", spaced1Day.size).id("HEADER_1DAY").addTo(this)

        GridCarouselModel_()
            .id("1DAY_KANJI")
            .models(spaced1Day.map { kanji ->
                KanjiItemModel(kanji).id(kanji.id)
            }).addTo(this)

        HeaderModel("3 DAYS", spaced3Days.size).id("HEADER_1DAY").addTo(this)

        GridCarouselModel_()
            .id("3DAY_KANJI")
            .models(spaced3Days.map { kanji ->
                KanjiItemModel(kanji).id(kanji.id)
            }).addTo(this)


        HeaderModel("1 WEEK", spaced1Week.size).id("HEADER_3DAYS").addTo(this)

        GridCarouselModel_()
            .id("1WEEK_KANJI")
            .models(spaced1Week.map { kanji ->
                KanjiItemModel(kanji).id(kanji.id)
            }).addTo(this)

        HeaderModel("1 MONTH", spaced1Month.size).id("HEADER_1WEEK").addTo(this)

        GridCarouselModel_()
            .id("1MONTH_KANJI")
            .models(spaced1Month.map { kanji ->
                KanjiItemModel(kanji).id(kanji.id)
            }).addTo(this)

        HeaderModel("6 MONTHS", spaced6Months.size).id("HEADER_1MONTH").addTo(this)

        GridCarouselModel_()
            .id("6MONTHS_KANJI")
            .models(spaced6Months.map { kanji ->
                KanjiItemModel(kanji).id(kanji.id)
            }).addTo(this)


    }

    data class HeaderModel(val topic: String, val total: Int) :
        KotlinModel(R.layout.epoxy_header_kanji_item) {
        private val textViewTopic by bind<TextView>(R.id.textView_topic)
        private val textViewTotal by bind<TextView>(R.id.textView_total_kanji)
        override fun bind() {
            textViewTopic.text = topic
            textViewTotal.text = "TOTAL : $total KANJI"
        }

    }

    data class KanjiItemModel(val kanji: SpacedEntity) : KotlinModel(R.layout.epoxy_kanji_item) {
        private var timer: CountDownTimer? = null
        private val textViewKanji by bind<TextView>(R.id.textview_epoxy_kanji)
        private val textViewSpacedDate by bind<TextView>(R.id.textView_epoxy_spacedDate)
        private val iconCircle by bind<ImageView>(R.id.icon_circle)
        //color
        val softRed = parseColor("#ef4c43")
        val honestGreen = parseColor("#48bd89")
        override fun bind() {
            textViewKanji.text = kanji.kanji
            val spacedDate = kanji.spacedDate
            when {
                spacedDate <= Tempo.now -> {
                    textViewSpacedDate.text = "You can do it !"
                    iconCircle.setColorFilter(honestGreen)
                }
                spacedDate < Tempo.tomorrow -> {
                    val different = spacedDate.time - Tempo.now.time
                    timer = object : CountDownTimer(different, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val hoursUntilFinished =
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                            val minutesUntilFinished =
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                            val secondsUntilFinished =
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                            textViewSpacedDate.text = String.format(
                                "%02d:%02d:%02d",
                                hoursUntilFinished % 24,
                                minutesUntilFinished % 60,
                                secondsUntilFinished % 60
                            )
                            iconCircle.setColorFilter(softRed)
                        }

                        override fun onFinish() {
                            textViewSpacedDate.text = "Do it!"
                            iconCircle.setColorFilter(honestGreen)
                        }
                    }.start()
                }
                else -> {
                    textViewSpacedDate.text = kanji.spacedDate.toString("dd-MM-yyyy")
                }
            }
        }

        override fun unbind(view: View) {
            timer?.cancel()
        }

    }
}

