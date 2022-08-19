package com.example.finalproject.fragment.spaced

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finalproject.R
import com.example.finalproject.data_kanji.Data
import com.example.finalproject.databinding.FragmentQuizBinding
import com.example.finalproject.viewmodel.SharedViewModel

class QuizFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<QuizFragmentArgs>()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var submitAnswer: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        sharedViewModel.index.observe(viewLifecycleOwner, {
            initChoice()
            progressBar()
        })
        initOnClickListener()
        return binding.root
    }

    private fun progressBar() {
        binding.progressBar.max = args.quizKanji.size
        binding.progressBar.step = sharedViewModel.index.value!!
    }

    private fun checkEnd() {
        when (sharedViewModel.index.value) {
            args.quizKanji.size - 1 -> {
                val correct = sharedViewModel.correct.value
                val wrong = sharedViewModel.wrong.value
                val action =
                    QuizFragmentDirections.actionQuizFragmentToResultFragment(correct!!.toTypedArray(), wrong!!.toTypedArray())
                findNavController().navigate(action)
            }
            else -> {
                sharedViewModel.plusIndex()
            }
        }
    }


    private fun initChoice() {
        val index = sharedViewModel.index.value!!
        binding.textViewTotal.text = "${index+1}/${args.quizKanji.size}"
        val question = args.quizKanji[index].kanji
        val answer = args.quizKanji[index].kanjiMeaning
        val choice = (Data.kanji).let { data ->
            val list = data.map { it.kanjiMeaning }
            val listWithoutAnswer = list.minus(answer)
            val shuffledList = listWithoutAnswer.shuffled()
            val threeChoices = shuffledList.subList(0, 3)
            val fourChoices = threeChoices.plus(answer)
            fourChoices.shuffled()
        }
        setQuiz(question, choice)
    }

    private fun setQuiz(question: String, choice: List<String>) {
        binding.question.text = question
        binding.button.text = choice[0]
        binding.button2.text = choice[1]
        binding.button3.text = choice[2]
        binding.button4.text = choice[3]
    }

    override fun onClick(buttonView: View?) {
        val clickedBtn = buttonView as Button
        val buttonClickedColor = ContextCompat.getColor(requireContext(),
            R.color.ripple)
        setDefaultColor()
        when (buttonView.id) {
            R.id.buttonSubmit -> {
                checkNull()
            }
            else -> {
                clickedBtn.setBackgroundColor(buttonClickedColor)
                submitAnswer = clickedBtn.text.toString()
            }
        }
    }

    private fun setDefaultColor() {
        val defaultTextColor = ContextCompat.getColor(requireContext(),
            R.color.purple_500)
        binding.button.setBackgroundColor(0)
        binding.button2.setBackgroundColor(0)
        binding.button3.setBackgroundColor(0)
        binding.button4.setBackgroundColor(0)
        binding.button.setTextColor(defaultTextColor)
        binding.button2.setTextColor(defaultTextColor)
        binding.button3.setTextColor(defaultTextColor)
        binding.button4.setTextColor(defaultTextColor)
    }

    private fun checkNull() {
        if (submitAnswer.isNullOrBlank()) {
            Toast.makeText(activity, "Please Select Your Answer", Toast.LENGTH_SHORT).show()
        } else {
            checkAnswer()
        }
    }

    private fun checkAnswer() {
        val index = sharedViewModel.index.value!!
        when (args.quizKanji[index].kanjiMeaning) {
            submitAnswer -> {
                sharedViewModel.addCorrect(submitAnswer!!)
            }
            else -> {
                sharedViewModel.addWrong(submitAnswer!!)
            }
        }
        Log.i("Selected ",submitAnswer!!)
        submitAnswer = null
        checkEnd()
    }


    private fun initOnClickListener() {
        binding.button.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.buttonSubmit.setOnClickListener(this)
        //close backButton press
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // With blank your fragment BackPressed will be disabled.
        }
    }
}