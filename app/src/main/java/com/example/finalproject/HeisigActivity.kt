package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.data_kanji.Kanji
import com.example.finalproject.databinding.ActivityHeisigBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.ui.KanjiViewModel
import com.example.finalproject.ui.KanjiViewModelFactory
import io.islandtime.Date
import io.islandtime.clock.now

class HeisigActivity:AppCompatActivity() {
    lateinit var binding : ActivityHeisigBinding
    lateinit var kanjiViewModel: KanjiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeisigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }


    private fun initUI() {
        val dao = KanjiDatabase.getInstance(this).dao() // dao() is in Kanjidatabase that implement from Dao
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]

        //
        val selectedkanji = intent.getParcelableExtra<Kanji>("selectedKanji")
        binding.component1kanji.text = selectedkanji?.Component1
        binding.component1meaning.text = selectedkanji?.Component1Meaning
        binding.component1readingkun.text = selectedkanji?.Component1ReadingKun
        binding.component1readingon.text = selectedkanji?.Component1ReadingOn
        //
        binding.component2kanji.text = selectedkanji?.Component2
        binding.component2meaning.text = selectedkanji?.Component2Meaning
        binding.component2readingkun.text = selectedkanji?.Component2ReadingKun
        binding.component2readingon.text = selectedkanji?.Component2ReadingOn
        //
        binding.kanji.text = selectedkanji?.Kanji
        binding.kanjimeaning.text = selectedkanji?.KanjiMeaning
        binding.kanjireadingkun.text = selectedkanji?.KanjiReadingKun
        binding.kanjireadingon.text = selectedkanji?.KanjiReadingOn
        //
        binding.story.text = selectedkanji?.Story

        binding.addbutton.setOnClickListener {
            val date = Date.now()
            val addkanjiobject = KanjiEntity(0,selectedkanji!!.Kanji,selectedkanji!!.JLPT,selectedkanji.KanjiReadingKun,selectedkanji.KanjiReadingOn,selectedkanji.KanjiMeaning,selectedkanji.Component1,selectedkanji.Component1ReadingKun,selectedkanji.Component1ReadingOn,selectedkanji.Component1Meaning,selectedkanji.Component2,selectedkanji.Component2ReadingKun,selectedkanji.Component2ReadingOn,selectedkanji.Component2Meaning,selectedkanji?.Story)
            Log.i("item",date.toString())
            if (addkanjiobject != null) {
                kanjiViewModel.insert(addkanjiobject)
            }
            Toast.makeText(this,"added",Toast.LENGTH_SHORT).show()
        }
    }


}