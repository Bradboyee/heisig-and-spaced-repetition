package com.example.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.data_kanji.Data
import com.example.finalproject.databinding.ActivityKanjiBinding
import com.example.finalproject.utils.KanjiRecyclerAdapter

class KanjiListActivity:AppCompatActivity() {
    lateinit var binding: ActivityKanjiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKanjiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val value = intent.getIntExtra("Category_value",0)
        binding.kanjirecyclerview.layoutManager = LinearLayoutManager(this)
        binding.kanjirecyclerview.adapter = KanjiRecyclerAdapter(Data.kanji,value)

    }
}