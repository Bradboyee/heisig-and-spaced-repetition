package com.example.finalproject.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.KanjiListActivity
import com.example.finalproject.R
import com.example.finalproject.data_kanji.Category
import kotlinx.android.synthetic.main.item_category.view.*


class CategoryRecyclerAdapter(category: List<Category>) : RecyclerView.Adapter<CategoryRecyclerAdapter.viewholder>() {
    val cate = category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val current = cate[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int = cate.size

    class viewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val catetextview = itemview.categorytextview


        fun bind(cate : Category) {
            catetextview.text = cate.Categorytitle
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,KanjiListActivity::class.java)
                val value = cate.value
                intent.putExtra("Category_value",value)
                itemView.context.startActivity(intent)
            }
        }

    }
}