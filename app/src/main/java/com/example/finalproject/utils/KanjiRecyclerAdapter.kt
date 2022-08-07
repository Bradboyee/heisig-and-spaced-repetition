package com.example.finalproject.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.HeisigActivity
import com.example.finalproject.data_kanji.Kanji
import com.example.finalproject.databinding.ItemKanjiBinding

class KanjiRecyclerAdapter(kanji: List<Kanji>, private val value: Int) : RecyclerView.Adapter<KanjiRecyclerAdapter.ViewHolder>() {
    private val filteredKanji = kanji.filter { it.Japanese_Language_Proficiency_Test == value }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): ViewHolder {
        val view = ItemKanjiBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = filteredKanji[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int = filteredKanji.size


    class ViewHolder(binding: ItemKanjiBinding):RecyclerView.ViewHolder(binding.root) {
        private val kanjiTextview = binding.textview
        fun bind(kanji: Kanji) {
            kanjiTextview.text = kanji.kanji
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, HeisigActivity::class.java)
                intent.putExtra("selectedKanji",kanji)
                itemView.context.startActivity(intent)
            }
        }


    }
}