package com.thepparat.heisigwithspacedrepetition.epoxy.controller

import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyController
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.toString
import com.thepparat.heisigwithspacedrepetition.Constant.malachite
import com.thepparat.heisigwithspacedrepetition.Constant.pastel_orange
import com.thepparat.heisigwithspacedrepetition.Constant.sunset_orange
import com.thepparat.heisigwithspacedrepetition.R
import com.thepparat.heisigwithspacedrepetition.epoxy.model.GridCarouselModel_
import com.thepparat.heisigwithspacedrepetition.epoxy.model.KotlinModel
import com.thepparat.heisigwithspacedrepetition.epoxy.model.NoDataModel
import com.thepparat.heisigwithspacedrepetition.roomdatabase.roomentity.SpacedEntity
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class ControllerSpaced : EpoxyController() {

    var kanjiController by Delegates.observable(emptyList<SpacedEntity>()) { _, _, _ ->
        requestModelBuild()
    }

    override fun buildModels() {
        if (kanjiController.isEmpty()){
            NoDataModel().id("No Data").addTo(this)
        }
        val spacedStart = kanjiController.filter { it.status.spacedStatus == 0 }
        val spaced1Day = kanjiController.filter { it.status.spacedStatus == 1 }
        val spaced3Days = kanjiController.filter { it.status.spacedStatus == 2 }
        val spaced1Week = kanjiController.filter { it.status.spacedStatus == 3 }
        val spaced1Month = kanjiController.filter { it.status.spacedStatus == 4 }
        val spaced6Months = kanjiController.filter { it.status.spacedStatus == 5 }
        HeaderModel("START", spacedStart.size).id("HEADER_START").addTo(this)

        GridCarouselModel_()
            .id("START_KANJI")
            .models(spacedStart.map { kanji ->
                KanjiItemModel(kanji).id(kanji.kanji)
            }).addTo(this)

        HeaderModel("1 DAY", spaced1Day.size).id("HEADER_1DAY").addIf(spaced1Day.isNotEmpty(),this)

        GridCarouselModel_()
            .id("1DAY_KANJI")
            .models(spaced1Day.map { kanji ->
                KanjiItemModel(kanji).id(kanji.kanji)
            }).addTo(this)

        HeaderModel("3 DAYS", spaced3Days.size).id("HEADER_1DAY").addIf(spaced3Days.isNotEmpty(),this)

        GridCarouselModel_()
            .id("3DAY_KANJI")
            .models(spaced3Days.map { kanji ->
                KanjiItemModel(kanji).id(kanji.kanji)
            }).addTo(this)


        HeaderModel("1 WEEK", spaced1Week.size).id("HEADER_3DAYS").addIf(spaced1Week.isNotEmpty(),this)

        GridCarouselModel_()
            .id("1WEEK_KANJI")
            .models(spaced1Week.map { kanji ->
                KanjiItemModel(kanji).id(kanji.kanji)
            }).addTo(this)

        HeaderModel("1 MONTH", spaced1Month.size).id("HEADER_1WEEK").addIf(spaced1Month.isNotEmpty(),this)

        GridCarouselModel_()
            .id("1MONTH_KANJI")
            .models(spaced1Month.map { kanji ->
                KanjiItemModel(kanji).id(kanji.kanji)
            }).addTo(this)

        HeaderModel("6 MONTHS", spaced6Months.size).id("HEADER_1MONTH").addIf(spaced6Months.isNotEmpty(),this)

        GridCarouselModel_()
            .id("6MONTHS_KANJI")
            .models(spaced6Months.map { kanji ->
                KanjiItemModel(kanji).id(kanji.kanji)
            }).addTo(this)
    }

    data class HeaderModel(val topic: String, val total: Int) :
        KotlinModel(R.layout.epoxy_header_kanji_item) {
        private val textViewTopic by bind<TextView>(R.id.textView_topic)
        private val textViewTotal by bind<TextView>(R.id.textView_total_kanji)
        override fun bind() {
            textViewTopic.text = topic
            textViewTotal.text = textViewTotal.context.getString(R.string.total_kanji_messages,total)
        }

    }

    data class KanjiItemModel(val kanji: SpacedEntity) : KotlinModel(R.layout.epoxy_spaced_kanji) {
        private var timer: CountDownTimer? = null
        private val textViewKanji by bind<TextView>(R.id.textview_epoxy_kanji)
        private val textViewSpacedDate by bind<TextView>(R.id.textView_epoxy_spacedDate)
        private val iconCircle by bind<ImageView>(R.id.icon_circle)
        override fun bind() {
            textViewKanji.text = kanji.kanji
            val spacedDate = kanji.status.spacedDate
            when {
                spacedDate <= Tempo.now -> {
                    textViewSpacedDate.text = textViewSpacedDate.context.getString(R.string.spaced_do_it)
                    iconCircle.setColorFilter(malachite)
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
                            iconCircle.setColorFilter(pastel_orange)
                        }

                        override fun onFinish() {
                            textViewSpacedDate.text = textViewSpacedDate.context.getString(R.string.do_it)
                            iconCircle.setColorFilter(malachite)
                        }
                    }.start()
                }
                else -> {
                    textViewSpacedDate.text = kanji.status.spacedDate.toString("dd-MM-yyyy")
                    iconCircle.setColorFilter(sunset_orange)
                }
            }
        }

        override fun unbind(view: View) {
            timer?.cancel()
        }

    }
}

