package com.example.finalproject.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemSpacedkanjiBinding
import com.example.finalproject.roomdatabase.KanjiEntity

class SpacedRecyclerAdapter :RecyclerView.Adapter<SpacedRecyclerAdapter.ViewHolder>() {
    private var kanjiList = listOf<KanjiEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSpacedkanjiBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = kanjiList[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int = kanjiList.size

    fun submitList(list: List<KanjiEntity>){
        this.kanjiList = list
    }

    class ViewHolder(binding: ItemSpacedkanjiBinding) :RecyclerView.ViewHolder(binding.root) {
        private var kanjiText: TextView = binding.spaceditem
        fun bind(kanji: KanjiEntity) {
            kanjiText.text = kanji.kanji

//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context,QuizActivity::class.java)
//                intent.putExtra("kanjiQuiz",kanji)
//                itemView.context.startActivity(intent)
//            }
        }

    }
}