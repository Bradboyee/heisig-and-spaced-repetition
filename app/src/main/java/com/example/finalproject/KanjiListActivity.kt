package com.example.finalproject

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.data_kanji.Data
import com.example.finalproject.databinding.ActivityKanjiBinding
import com.example.finalproject.recyclerview.KanjiRecyclerViewAdapter

class KanjiListActivity:AppCompatActivity() {
    lateinit var binding: ActivityKanjiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_kanji,)
        val value = intent.getIntExtra("Category_value",0)
        binding.kanjirecyclerview.layoutManager = LinearLayoutManager(this)
        binding.kanjirecyclerview.adapter = KanjiRecyclerViewAdapter(Data.kanji,value)

    }
}