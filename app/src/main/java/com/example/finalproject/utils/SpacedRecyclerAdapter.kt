package com.example.finalproject.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.QuizActivity
import com.example.finalproject.R
import com.example.finalproject.roomdatabase.KanjiEntity
import kotlinx.android.synthetic.main.item_spacedkanji.view.*

class SpacedRecyclerAdapter:RecyclerView.Adapter<SpacedRecyclerAdapter.ViewHolder>() {
    var kanjilist = listOf<KanjiEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_spacedkanji,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = kanjilist[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int = kanjilist.size

    fun submitlist(list: List<KanjiEntity>){
        this.kanjilist = list
    }

    class ViewHolder(itemview: View) :RecyclerView.ViewHolder(itemview) {
        var kanjitext = itemview.spaceditem
        fun bind(kanji: KanjiEntity) {
            kanjitext.text = kanji.kanji

            itemView.setOnClickListener {
                val intent = Intent(itemView.context,QuizActivity::class.java)
                intent.putExtra("kanjiquiz",kanji)
                itemView.context.startActivity(intent)
            }
        }

    }
}