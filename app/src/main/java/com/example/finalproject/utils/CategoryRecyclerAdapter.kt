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


class CategoryRecyclerAdapter(category: List<Category>) : RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {
    val cate = category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = cate[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int = cate.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catetextview = itemView.categorytextview


        fun bind(cate : Category) {
            catetextview.text = cate.categorytitle
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,KanjiListActivity::class.java)
                val value = cate.value
                intent.putExtra("Category_value",value)
                itemView.context.startActivity(intent)
            }
        }

    }
}