package com.example.finalproject.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.roomdatabase.KanjiEntity
import kotlinx.android.synthetic.main.item_spacedkanji.view.*

class SpacedRecyclerAdapter :RecyclerView.Adapter<SpacedRecyclerAdapter.ViewHolder>() {
    private var kanjiList = listOf<KanjiEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_spacedkanji,parent,false)
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

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        private var kanjiText: TextView = itemView.spaceditem
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