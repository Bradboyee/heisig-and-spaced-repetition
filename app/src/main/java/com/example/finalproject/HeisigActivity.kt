package com.example.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.data_kanji.Kanji
import com.example.finalproject.databinding.ActivityHeisigBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.viewmodel.KanjiViewModel
import com.example.finalproject.viewmodel.KanjiViewModelFactory
import java.util.*

class HeisigActivity:AppCompatActivity() {
    private lateinit var binding : ActivityHeisigBinding
    private lateinit var kanjiViewModel: KanjiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeisigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }


    private fun initUI() {
        val dao = KanjiDatabase.getInstance(this).dao() // dao() is in KanjiDatabase that implement from Dao
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]

        //
        val selectedKanji = intent.getParcelableExtra<Kanji>("selectedKanji")
        binding.component1kanji.text = selectedKanji?.component1
        binding.component1meaning.text = selectedKanji?.component1Meaning
        binding.component1readingkun.text = selectedKanji?.component1ReadingKun
        binding.component1readingon.text = selectedKanji?.component1ReadingOn
        //
        binding.component2kanji.text = selectedKanji?.component2
        binding.component2meaning.text = selectedKanji?.component2Meaning
        binding.component2readingkun.text = selectedKanji?.component2ReadingKun
        binding.component2readingon.text = selectedKanji?.component2ReadingOn
        //
        binding.kanji.text = selectedKanji?.kanji
        binding.kanjimeaning.text = selectedKanji?.kanjiMeaning
        binding.kanjireadingkun.text = selectedKanji?.kanjiReadingKun
        binding.kanjireadingon.text = selectedKanji?.kanjiReadingOn
        //
        binding.story.text = selectedKanji?.story

        kanjiViewModel.kanjiList.observe(this){
                data ->
            val addDate = data.find { it.kanji == selectedKanji!!.kanji }
            if (addDate != null) {
                binding.tvAddDate.text = addDate.spacedDate.toString()
            }
        }

        binding.addbutton.setOnClickListener {
            val addKanjiObject = KanjiEntity(0,selectedKanji!!.kanji,
                selectedKanji.Japanese_Language_Proficiency_Test,
                selectedKanji.kanjiReadingKun,
                selectedKanji.kanjiReadingOn,
                selectedKanji.kanjiMeaning,
                selectedKanji.component1,
                selectedKanji.component1ReadingKun,
                selectedKanji.component1ReadingOn,
                selectedKanji.component1Meaning,
                selectedKanji.component2,
                selectedKanji.component2ReadingKun,
                selectedKanji.component2ReadingOn,
                selectedKanji.component2Meaning,
                selectedKanji.story,
                0,
                Date(),Date())
            kanjiViewModel.insert(addKanjiObject)
            finish()
        }
    }


}