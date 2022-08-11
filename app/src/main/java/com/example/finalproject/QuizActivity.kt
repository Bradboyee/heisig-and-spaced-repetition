package com.example.finalproject

import android.graphics.Color.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.data_kanji.Data
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.viewmodel.KanjiViewModelFactory
import android.content.Intent
import androidx.core.os.bundleOf
import com.example.finalproject.databinding.ActivityQuizBinding
import com.example.finalproject.viewmodel.KanjiViewModel
import java.text.SimpleDateFormat
import java.util.*


class QuizActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var kanjiViewModel: KanjiViewModel
    private var submitAnswerText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initBtn()
    }

    private fun initUI() {
        val dao = KanjiDatabase.getInstance(this).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this, factory)[KanjiViewModel::class.java]
        kanjiViewModel.currentQuestion.observe(this) {
            binding.tvTotal.text = it.toString()
        }

        kanjiViewModel.spacedKanji.observe(this) {
            prepareData()
            val formatDate = SimpleDateFormat("วันที่เพิ่ม EEEE ที่ dd เดือน MMMM พ.ศ. yyyy",
                Locale("th", "TH")).format(it.map { it.addDate }[0])
            binding.tvAddDate.text = formatDate
        }
        kanjiViewModel.submitCorrectAnswer.observe(this) {}
        kanjiViewModel.submitWrongAnswer.observe(this) {}
    }

    private fun prepareData() {
        //data
        val question = kanjiViewModel.getQuestion()
        val answer = kanjiViewModel.getAnswer()
        val currentIndex = kanjiViewModel.currentQuestion.value!!
        // load 3 choice and add the answer then shuffled
        val anotherChoices = (Data.kanji).let { data ->
            val list = data.map { it.kanjiMeaning }
            val listWithoutAnswer = list.minus(answer[currentIndex])
            val shuffledList = listWithoutAnswer.shuffled()
            val threeChoices = shuffledList.subList(0, 3)
            val fourChoices = threeChoices.plus(answer[currentIndex])
            fourChoices.shuffled()
        }
        anotherChoices.shuffled()
        //view
        binding.Question.text = question[currentIndex]

        binding.answer1.text = anotherChoices[0]
        binding.answer2.text = anotherChoices[1]
        binding.answer3.text = anotherChoices[2]
        binding.answer4.text = anotherChoices[3]
        //Date
    }


    private fun initBtn() {
        binding.answer1.setOnClickListener(this)
        binding.answer2.setOnClickListener(this)
        binding.answer3.setOnClickListener(this)
        binding.answer4.setOnClickListener(this)
        binding.submitAnswer.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val clicked = view as Button
        val idView = view.id
        val clickColor = RED
        val defaultButtonColor = ContextCompat.getColor(this, R.color.purple_500)

        binding.answer1.setBackgroundColor(defaultButtonColor)
        binding.answer2.setBackgroundColor(defaultButtonColor)
        binding.answer3.setBackgroundColor(defaultButtonColor)
        binding.answer4.setBackgroundColor(defaultButtonColor)
        when (idView) {
            R.id.submitAnswer -> {
                if (submitAnswerText.isNullOrEmpty()) {
                    Toast.makeText(this, "Please Select Your Answer", Toast.LENGTH_SHORT).show()
                } else {
                    setAnswer()
                }

            }
            else -> {
                clicked.setBackgroundColor(clickColor)
                submitAnswerText = clicked.text.toString()
            }
        }
    }

    private fun setAnswer() {
        //check correct or wrong and add to viewModel
        if (kanjiViewModel.getAnswer()[kanjiViewModel.currentQuestion.value!!] == submitAnswerText) {
            //if correct
            kanjiViewModel.addCorrectAnswer(kanjiViewModel.getQuestion()[kanjiViewModel.currentQuestion.value!!])
        } else {
            kanjiViewModel.addWrongAnswer(kanjiViewModel.getQuestion()[kanjiViewModel.currentQuestion.value!!])
        }
        //check totalQuiz
        kanjiViewModel.currentQuestion.value = kanjiViewModel.currentQuestion.value!!.plus(1)

        //check page
        if (kanjiViewModel.currentQuestion.value!! + 1 > kanjiViewModel.getAnswer().size) {
            //get the list of answer and send to spaced fragment to update data
            val intent = Intent()
            val correctAnswerList = kanjiViewModel.submitCorrectAnswer.value
            val wrongAnswerList = kanjiViewModel.submitWrongAnswer.value
            val correctAnswerBundle = bundleOf("correctBundle" to correctAnswerList)
            val wrongAnswerBundle = bundleOf("wrongBundle" to wrongAnswerList)
            intent.putExtra("correctAnswer", correctAnswerBundle)
            intent.putExtra("wrongAnswer", wrongAnswerBundle)
            setResult(RESULT_OK, intent)
            finish()
        } else {
            submitAnswerText = null // give answer to null and load new quiz
            loadNewQuiz(kanjiViewModel.currentQuestion.value!!)
        }
    }

    private fun loadNewQuiz(currentIndex: Int) {
        val questionKanji = kanjiViewModel.getQuestion()
        val answerKanji = kanjiViewModel.getAnswer()
        // load 3 new choice and add the answer then shuffled
        val anotherChoices = (Data.kanji).let { data ->
            val list = data.map { it.kanjiMeaning }
            val listWithoutAnswer = list.minus(answerKanji[currentIndex])
            val shuffledList = listWithoutAnswer.shuffled()
            val threeChoices = shuffledList.subList(0, 3)
            val fourChoices = threeChoices.plus(answerKanji[currentIndex])
            fourChoices.shuffled()
        }
        binding.Question.text = questionKanji[currentIndex]
        binding.answer1.text = anotherChoices[0]
        binding.answer2.text = anotherChoices[1]
        binding.answer3.text = anotherChoices[2]
        binding.answer4.text = anotherChoices[3]
    }
}