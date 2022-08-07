package com.example.finalproject.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.KanjiListActivity
import com.example.finalproject.data_kanji.Category
import com.example.finalproject.databinding.ItemCategoryBinding


class CategoryRecyclerAdapter(category: List<Category>) : RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {
    private val categorical = category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = categorical[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int = categorical.size

    class ViewHolder(binding:ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val categoryTextView: TextView = binding.tvCategory


        fun bind(cate : Category) {
            categoryTextView.text = cate.categoryTitle
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,KanjiListActivity::class.java)
                val value = cate.value
                intent.putExtra("Category_value",value)
                itemView.context.startActivity(intent)
            }
        }

    }
}