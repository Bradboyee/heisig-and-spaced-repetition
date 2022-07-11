package com.example.finalproject.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.HeisigActivity
import com.example.finalproject.R
import com.example.finalproject.data_kanji.Kanji
import kotlinx.android.synthetic.main.item_kanji.view.*

class KanjiRecyclerViewAdapter(private val kanji: List<Kanji>,val value: Int) : RecyclerView.Adapter<KanjiRecyclerViewAdapter.viewholder>() {
    val filteredkanji = kanji.filter { it.JLPT == value }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): KanjiRecyclerViewAdapter.viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kanji,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: KanjiRecyclerViewAdapter.viewholder, position: Int) {
        val current = filteredkanji[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int = filteredkanji.size


    class viewholder(itemview: View):RecyclerView.ViewHolder(itemview) {
        private val kanjitextview = itemview.textview
        fun bind(kanji: Kanji) {
            kanjitextview.text = kanji.Kanji

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, HeisigActivity::class.java)
                val kanjiobject = kanji
                intent.putExtra("selectedKanji",kanjiobject)
                itemView.context.startActivity(intent)
            }
        }


    }
}