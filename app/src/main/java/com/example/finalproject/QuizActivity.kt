package com.example.finalproject

import android.graphics.Color.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.data_kanji.Data
import com.example.finalproject.databinding.ActivityQuizBinding
import com.example.finalproject.roomdatabase.KanjiEntity
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity() : AppCompatActivity(),View.OnClickListener {
    lateinit var binding : ActivityQuizBinding
    var submitAnswer:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        binding.answer1.setOnClickListener(this)
        binding.answer2.setOnClickListener(this)
        binding.answer3.setOnClickListener(this)
        binding.answer4.setOnClickListener(this)
        binding.submitAnswer.setOnClickListener(this)

    }

    private fun initData() {
        val kanji = intent.getParcelableExtra<KanjiEntity>("kanjiquiz")
        val question = kanji?.kanji
        val answer = kanji?.kanjiMeaning
        val choices = mutableListOf(answer)
        val choiceslist = Data.kanji.map { it.kanjiMeaning }
        for( i in 0..3){
            choices += choiceslist[i]
        }
        bindingView(question,choiceslist)
    }

    private fun bindingView(question: String?, choiceslist: List<String>) {
        val shuffledlist = choiceslist.shuffled()
        binding.Question.text = question
        //
        binding.answer1.text = shuffledlist[0]
        binding.answer2.text = shuffledlist[1]
        binding.answer3.text = shuffledlist[2]
        binding.answer4.text = shuffledlist[3]
    }

    override fun onClick(view: View?) {
        val clicked = view as Button
        val idView = view.id
        val clickColor = RED
        val defaultButtonColor = BLUE
        answer1.setBackgroundColor(defaultButtonColor)
        answer2.setBackgroundColor(defaultButtonColor)
        answer3.setBackgroundColor(defaultButtonColor)
        answer4.setBackgroundColor(defaultButtonColor)
        when(idView){
            R.id.submitAnswer -> {
                if(submitAnswer.equals(null)){
                    Toast.makeText(this,"Please Select Your Answer",Toast.LENGTH_SHORT).show()
                }
                else {
                    checkAnswer()
                }
            }
            else -> {
                clicked.setBackgroundColor(clickColor)
                submitAnswer = clicked.text.toString()
            }
        }
    }

    private fun checkAnswer() {
    }


}