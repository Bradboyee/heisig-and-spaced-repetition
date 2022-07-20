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
        binding.component1kanji.text = selectedkanji?.component1
        binding.component1meaning.text = selectedkanji?.component1Meaning
        binding.component1readingkun.text = selectedkanji?.component1ReadingKun
        binding.component1readingon.text = selectedkanji?.component1ReadingOn
        //
        binding.component2kanji.text = selectedkanji?.component2
        binding.component2meaning.text = selectedkanji?.component2Meaning
        binding.component2readingkun.text = selectedkanji?.component2ReadingKun
        binding.component2readingon.text = selectedkanji?.component2ReadingOn
        //
        binding.kanji.text = selectedkanji?.kanji
        binding.kanjimeaning.text = selectedkanji?.kanjiMeaning
        binding.kanjireadingkun.text = selectedkanji?.kanjiReadingKun
        binding.kanjireadingon.text = selectedkanji?.kanjiReadingOn
        //
        binding.story.text = selectedkanji?.story

        binding.addbutton.setOnClickListener {
            val addkanjiobject = KanjiEntity(0,selectedkanji!!.kanji,selectedkanji!!.Japanese_Language_Proficiency_Test,selectedkanji.kanjiReadingKun,selectedkanji.kanjiReadingOn,selectedkanji.kanjiMeaning,selectedkanji.component1,selectedkanji.component1ReadingKun,selectedkanji.component1ReadingOn,selectedkanji.component1Meaning,selectedkanji.component2,selectedkanji.component2ReadingKun,selectedkanji.component2ReadingOn,selectedkanji.component2Meaning,selectedkanji?.story,0)
            kanjiViewModel.insert(addkanjiobject)
            Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
            finish()
        }
    }


}