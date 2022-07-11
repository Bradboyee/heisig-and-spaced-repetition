package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import com.example.finalproject.databinding.ActivityQuizBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.ui.KanjiViewModel
import com.example.finalproject.ui.KanjiViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizActivity() : AppCompatActivity() {
    lateinit var kanjiviewmodel : KanjiViewModel
    lateinit var binding : ActivityQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val kanji = intent.getParcelableExtra<KanjiEntity>("kanjiquiz")
        val questionkanji = kanji?.kanji
        val answer = kanji?.KanjiMeaning
        initUI()
        var choices = listOf(answer,"bbb","ccc","xxx")
        choices = choices.shuffled()
        binding.kanjiquestiontextview.setText(questionkanji)
        //
        binding.answer1.setText(choices[0])
        binding.answer2.setText(choices[1])
        binding.answer3.setText(choices[2])
        binding.answer4.setText(choices[3])
    }

    private fun initUI() {
        val dao = KanjiDatabase.getInstance(this).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiviewmodel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]
        getData()
    }

    private fun getData() {

    }
}