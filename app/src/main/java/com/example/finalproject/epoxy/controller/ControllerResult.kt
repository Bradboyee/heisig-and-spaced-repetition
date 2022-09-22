package com.example.finalproject.epoxy.controller

import android.widget.TextView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.lottie.LottieAnimationView
import com.example.finalproject.R
import com.example.finalproject.epoxy.model.KotlinModel
import com.example.finalproject.viewmodel.QuizResult

class ControllerResult : EpoxyController() {
    var correct = listOf<QuizResult>()
    var wrong = listOf<QuizResult>()
    override fun buildModels() {
        HeaderModel(correct.size,correct.size.plus(wrong.size)).id("HEADER").addTo(this)
        correct.map { DetailCorrectModel(it).id(it.kanji).addTo(this) }
        wrong.map { DetailWrongModel(it).id(it.kanji).addTo(this) }
        TrophyAnimationModel().id("ANIMATE").addTo(this)
    }
    data class HeaderModel(val score:Int,val total:Int):KotlinModel(R.layout.epoxy_result_header){
        private val textViewScore by bind<TextView>(R.id.textView_score)
        override fun bind() {
            textViewScore.text = textViewScore.context.getString(R.string.score,score,total)
        }
    }
    class TrophyAnimationModel :KotlinModel(R.layout.lottie_trophy){
        private val anim by bind<LottieAnimationView>(R.id.anim_trophy)
        override fun bind() {
            anim.playAnimation()
            anim.setOnClickListener{
                anim.playAnimation()
            }
        }
    }
    data class DetailCorrectModel(private val correct : QuizResult):KotlinModel(R.layout.epoxy_result_correct){
        private val textViewKanji by bind<TextView>(R.id.textView_kanji)
        private val textViewAnswer by bind<TextView>(R.id.textView_answer)
        private val anim by bind<LottieAnimationView>(R.id.result_anim)
        override fun bind() {
            textViewKanji.text = correct.kanji
            textViewAnswer.text = correct.answer
            anim.playAnimation()
            anim.setOnClickListener{
                anim.playAnimation()
            }
        }
    }
    data class DetailWrongModel(private val wrong : QuizResult):KotlinModel(R.layout.epoxy_result_wrong){
        private val textViewKanji by bind<TextView>(R.id.textView_kanji)
        private val textViewAnswer by bind<TextView>(R.id.textView_answer)
        private val textViewSubmit by bind<TextView>(R.id.textView_submit)
        private val anim by bind<LottieAnimationView>(R.id.result_anim)
        override fun bind() {
            textViewKanji.text = wrong.kanji
            textViewAnswer.text = wrong.answer
            textViewSubmit.text = wrong.submit
            anim.playAnimation()
            anim.setOnClickListener{
                anim.playAnimation()
            }
        }
    }
}