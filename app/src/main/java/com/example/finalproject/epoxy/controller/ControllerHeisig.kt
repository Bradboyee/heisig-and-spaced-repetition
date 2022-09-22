package com.example.finalproject.epoxy.controller

import android.net.Uri
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.findFragment
import com.airbnb.epoxy.*
import com.airbnb.lottie.LottieAnimationView
import com.example.finalproject.Constant.errorImage
import com.example.finalproject.Constant.loadingImage
import com.example.finalproject.mediaplayer.MediaPlayerService
import com.example.finalproject.R
import com.example.finalproject.dialog.InsertStoryDialog
import com.example.finalproject.dialog.DeleteStoryDialog
import com.example.finalproject.dialog.UpdateStoryDialog
import com.example.finalproject.epoxy.model.*
import com.example.finalproject.fragment.home.HeisigFragment
import com.example.finalproject.json.jsonpojo.PojoRadicalItem
import com.example.finalproject.retrofit.pojo.*
import com.example.finalproject.roomdatabase.roomentity.Story
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class ControllerHeisig : EpoxyController() {
    var story: List<Story> = listOf()
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
    var errorMassage: String? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    var mapRadicalController: HashMap<String, PojoRadicalItem?> = HashMap()

    override fun buildModels() {
        if (isLoading) {
            LoadingModel().id("Load").addTo(this)
            return
        }
        if (errorMassage != null) {
            ErrorModel(errorMassage!!).id(errorMassage).addTo(this)
            return
        }
        val orderWriting = kanjiDetail!!.kanji.strokes.images.map {
            ImageModel(it).id(it)
        }
        TopicKanjiModel(kanjiDetail!!.kanji).id("HEADER").addTo(this)
        GridOrderWritingCarouselModel_().models(orderWriting).id("ORDER WRITING").addTo(this)
        SplitLineStoryModel("Story", kanjiDetail!!.kanji.character).id("STORY").addTo(this)
        if (story.isNotEmpty()) {
            story.map { StoryModel(it).id(it.kanji).addTo(this) }
        }
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
                } else {
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
            readingTextView.text = readingTextView.context.getString(R.string.newline,
                kanji.kunyomi.hiragana,
                kanji.onyomi.katakana)
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

    class SplitLineStoryModel(private val text: String, private val character: String) :
        KotlinModel(R.layout.epoxy_splitline_story) {
        private val spitLineTextView by bind<TextView>(R.id.textView_line_story)
        private val cardAddStory by bind<ImageButton>(R.id.card_add_story)
        override fun bind() {
            cardAddStory.setOnClickListener {
                val dialog = InsertStoryDialog(character)
                val support = this.cardAddStory.findFragment<HeisigFragment>()
                dialog.show(support.parentFragmentManager, "Insert")
            }
            spitLineTextView.text = text
        }

    }

    data class StoryModel(private val story: Story) : KotlinModel(R.layout.epoxy_story) {
        private val storyTextView by bind<TextView>(R.id.textView_story)
        private val deleteButton by bind<ImageButton>(R.id.button_delete)
        private val updateButton by bind<ImageButton>(R.id.button_update)
        override fun bind() {
            storyTextView.text = story.story
            deleteButton.setOnClickListener {
                val dialog = DeleteStoryDialog(story)
                val fragmentManager =
                    this.deleteButton.findFragment<HeisigFragment>().parentFragmentManager
                dialog.show(fragmentManager, "Delete")
            }
            updateButton.setOnClickListener {
                val dialog = UpdateStoryDialog(story)
                val fragmentManager =
                    this.updateButton.findFragment<HeisigFragment>().parentFragmentManager
                dialog.show(fragmentManager, "Update")
            }
        }
    }


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
            textViewRadicalName.text = textViewRadicalName.context.getString(R.string.comma,
                radical.name.hiragana,
                radical.name.romaji)
            textViewRadicalMeaning.text = radical.meaning.english
            //position
            if (radical.position.icon.isNotEmpty()) {
                GlideToVectorYou.init().with(this.imageViewPosition.context)
                    .setPlaceHolder(loadingImage, errorImage).load(uriPosition, imageViewPosition)
                textViewRadicalPosition.text =
                    textViewRadicalPosition.context.getString(R.string.comma,
                        radical.position.hiragana,
                        radical.position.romaji)
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
        private val audioAnimation by bind<LottieAnimationView>(R.id.buttonImageView_play)
        override fun bind() {
            textViewExample.text = example.japanese
            textViewExampleMeaning.text = example.meaning.english
            audioAnimation.setOnClickListener {
                audioAnimation.playAnimation()
                MediaPlayerService(example.audio.mp3).mediaPlayer.start()
            }
        }
    }

    class SplitLineModel(private val text: String) : KotlinModel(R.layout.epoxy_splitline) {
        private val spitLineTextView by bind<TextView>(R.id.textView_line)
        override fun bind() {
            spitLineTextView.text = text
        }

    }

    class LoadingModel : KotlinModel(R.layout.lottie_loading) {
        override fun bind() {
        }
    }

    data class ErrorModel(private val errorMassage: String) : KotlinModel(R.layout.lottie_error) {
        private val textViewError by bind<TextView>(R.id.textViewError)
        override fun bind() {
            textViewError.text = errorMassage
        }

    }
}