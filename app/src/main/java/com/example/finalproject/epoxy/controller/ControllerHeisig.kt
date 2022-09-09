package com.example.finalproject.epoxy.controller

import android.net.Uri
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.airbnb.epoxy.*
import com.example.finalproject.Constant.errorImage
import com.example.finalproject.Constant.loadingImage
import com.example.finalproject.MediaPlayerService
import com.example.finalproject.R
import com.example.finalproject.epoxy.model.*
import com.example.finalproject.json.jsonpojo.PojoRadicalItem
import com.example.finalproject.retrofit.pojo.*
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class ControllerHeisig : EpoxyController() {
    var kanjiDetail: PojoSingleKanjiDetail? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }
    private var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var mapRadicalController: HashMap<String, PojoRadicalItem?> = HashMap()

    override fun buildModels() {
        if (isLoading) {
            LoadingModel().id("Load").addTo(this)
            return
        }
        if (kanjiDetail == null) {
            ErrorModel().id("Error").addTo(this)
            return
        }
        val orderWriting = kanjiDetail!!.kanji.strokes.images.map {
            ImageModel(it).id(it)
        }
        TopicKanjiModel(kanjiDetail!!.kanji).id("HEADER").addTo(this)
        GridOrderWritingCarouselModel_().models(orderWriting).id("ORDER WRITING").addTo(this)
        SplitLineModel("My story").id("Story").addTo(this)
        SplitLineModel("Info").id("Info").addTo(this)
        SplitLineModel("Radical").id("Radical").addTo(this)
        RadicalModel(kanjiDetail!!.radical).id(kanjiDetail!!.radical.character).addTo(this)
        //animation
        val radicalAnimation = kanjiDetail!!.radical.animation.map {
            RadicalAnimationImage(it).id(it)
        }
        carousel {
            id("Animation")
            numViewsToShowOnScreen(3f)
            models(radicalAnimation)
        }

        SplitLineModel("Component").id("Component").addTo(this)
        if (mapRadicalController.isNotEmpty()) {
            mapRadicalController.values.forEach {
                if (it != null) {
                    ComponentModel(it).id(it.radical).addTo(this)
                }else{
                    //use diff Model
                }
            }
        }
        SplitLineModel("Example").id("Example").addTo(this)
        kanjiDetail!!.examples.map {
            ExampleModel(it).id(it.japanese).addTo(this)
        }
    }

    data class TopicKanjiModel(private val kanji: Kanji) :
        KotlinModel(R.layout.epoxy_heisig_topic_kanji) {
        private val imageView by bind<ImageView>(R.id.imageView_kanji)
        private val meaningTextView by bind<TextView>(R.id.textView_meaning)
        private val readingTextView by bind<TextView>(R.id.textView_reading)
        private val uri: Uri = Uri.parse(kanji.video.poster)
        override fun bind() {
            GlideToVectorYou.init().with(this.imageView.context)
                .setPlaceHolder(loadingImage, errorImage).load(uri, imageView)
            meaningTextView.text = kanji.meaning.english
            readingTextView.text = "kun : ${kanji.kunyomi.hiragana}\nOn : ${kanji.onyomi.katakana}"
        }
    }

    data class ImageModel(private val imageURL: String) :
        KotlinModel(R.layout.epoxy_heisig_image) {

        private val imageView by bind<ImageView>(R.id.imageView_kanji)
        private val uri: Uri = Uri.parse(imageURL)
        override fun bind() {
            GlideToVectorYou.init().with(this.imageView.context)
                .setPlaceHolder(loadingImage, errorImage).load(uri, imageView)
        }
    }

    data class InfoModel(private val references: References)

    data class RadicalModel(private val radical: Radical) :
        KotlinModel(R.layout.epoxy_heisig_radical) {
        private val imageViewRadical by bind<ImageView>(R.id.imageView_radical)
        private val textViewRadical by bind<TextView>(R.id.textView_radical)
        private val textViewRadicalName by bind<TextView>(R.id.textView_name_radical)
        private val textViewRadicalMeaning by bind<TextView>(R.id.textView_radical_meaning)
        private val textViewRadicalPosition by bind<TextView>(R.id.textView_position)
        private val imageViewPosition by bind<ImageView>(R.id.imageView_position)
        private val uriRadical: Uri = Uri.parse(radical.image)
        private val uriPosition: Uri = Uri.parse(radical.position.icon)
        override fun bind() {
            GlideToVectorYou.init().with(this.imageViewRadical.context)
                .setPlaceHolder(loadingImage, errorImage).load(uriRadical, imageViewRadical)
            textViewRadical.text = radical.character
            textViewRadicalName.text = "${radical.name.hiragana},${radical.name.romaji}"
            textViewRadicalMeaning.text = radical.meaning.english
            //position
            if (radical.position.icon.isNotEmpty()) {
                GlideToVectorYou.init().with(this.imageViewPosition.context)
                    .setPlaceHolder(loadingImage, errorImage).load(uriPosition, imageViewPosition)
                textViewRadicalPosition.text =
                    "${radical.position.hiragana},${radical.position.romaji}"
            } else {
                imageViewPosition.isVisible = false
                textViewRadicalPosition.text = "n/a"
            }
        }
    }

    data class RadicalAnimationImage(val animationsURL: String) :
        KotlinModel(R.layout.epoxy_heisig_radical_animation) {
        private val imageViewAnimation by bind<ImageView>(R.id.imageView_radical_animations)
        private val uri: Uri = Uri.parse(animationsURL)
        override fun bind() {
            GlideToVectorYou.init().with(this.imageViewAnimation.context)
                .setPlaceHolder(loadingImage,
                    errorImage).load(uri, imageViewAnimation)
        }
    }

    data class ComponentModel(private val radical: PojoRadicalItem) :
        KotlinModel(R.layout.epoxy_heisig_component) {
        private val textViewComponent by bind<TextView>(R.id.textView_component)
        private val textViewReading by bind<TextView>(R.id.textView_component_reading)
        private val textViewMeaning by bind<TextView>(R.id.textView_component_meaning)
        private val textViewStroke by bind<TextView>(R.id.textView_component_stroke)
        private val textViewPosition by bind<TextView>(R.id.textView_component_position)
        override fun bind() {
            textViewComponent.text = radical.radical
            textViewReading.text = radical.hiragana_romaji
            textViewMeaning.text = radical.meaning
            textViewStroke.text = radical.stroke_count.toString()
            textViewPosition.text = radical.position
        }
    }

    data class ExampleModel(private val example: Example) :
        KotlinModel(R.layout.epoxy_heisig_example) {
        private val textViewExample by bind<TextView>(R.id.textView_example)
        private val textViewExampleMeaning by bind<TextView>(R.id.textView_example_meaning)
        private val imageViewPlay by bind<ImageButton>(R.id.buttonImageView_play)
        override fun bind() {
            textViewExample.text = example.japanese
            textViewExampleMeaning.text = example.meaning.english
            imageViewPlay.setOnClickListener {
                MediaPlayerService(example.audio.mp3).mediaPlayer.start()
            }
        }
    }

    class SplitLineModel(private val text: String) : KotlinModel(R.layout.split_line) {
        private val spitLineTextView by bind<TextView>(R.id.textView_line)
        override fun bind() {
            spitLineTextView.text = text
        }

    }

    class LoadingModel : KotlinModel(R.layout.loading) {
        override fun bind() {
        }
    }

    class ErrorModel : KotlinModel(R.layout.error) {
        override fun bind() {
        }

    }
}