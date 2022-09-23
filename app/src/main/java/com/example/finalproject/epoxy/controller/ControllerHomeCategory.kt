package com.example.finalproject.epoxy.controller

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.airbnb.epoxy.EpoxyController
import com.example.finalproject.R
import com.example.finalproject.epoxy.model.KotlinModel

class ControllerHomeCategory:EpoxyController() {

    override fun buildModels() {
        CategoryHeaderModel("GRADE 1").id("GRADE1").addTo(this)

        CategoryHeaderModel("GRADE 2").id("GRADE1").addTo(this)

        CategoryHeaderModel("GRADE 3").id("GRADE1").addTo(this)

        CategoryHeaderModel("GRADE 4").id("GRADE1").addTo(this)

        CategoryHeaderModel("GRADE 5").id("GRADE1").addTo(this)

        CategoryHeaderModel("GRADE 6").id("GRADE1").addTo(this)



    }
    data class CategoryHeaderModel(val topic: String):KotlinModel(R.layout.epoxy_category){
        private val textViewTopicCategory by bind<TextView>(R.id.epoxy_category_topic)
        private val cardViewCategory by bind<CardView>(R.id.card_category)
        override fun bind() {
            textViewTopicCategory.text = topic
            textViewTopicCategory.setOnClickListener {
                navigateToListKanji(it,topic)
            }
            cardViewCategory.setOnClickListener{
                navigateToListKanji(it,topic)
            }
        }
        private fun navigateToListKanji(view:View,topic: String){
            val bundle = bundleOf("category" to topic)
            view.findNavController().navigate(R.id.action_homeFragment_to_kanjiListFragment, bundle)
        }
    }
}