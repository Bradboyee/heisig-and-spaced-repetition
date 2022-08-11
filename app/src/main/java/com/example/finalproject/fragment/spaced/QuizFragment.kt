package com.example.finalproject.fragment.spaced

import android.os.Bundle
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
    private var _binding: FragmentQuizBinding?=null
    private val binding get() = _binding!!
    private val args by navArgs<QuizFragmentArgs>()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private var answer:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentQuizBinding.inflate(inflater,container,false)
        sharedViewModel.index.observe(viewLifecycleOwner, { checkEnd() })
        sharedViewModel.answer.observe(viewLifecycleOwner, {})
        initOnClickListener()
        initChoice()
        return binding.root
    }

    private fun checkEnd() {
        if(sharedViewModel.index.value==args.quizKanji.size){
            val test = arrayOf("test2","test12")
            val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(test)
            sharedViewModel.clearViewModel()
            findNavController().navigate(action)
        }else{initChoice()}
    }

    private fun initChoice() {
        val index = sharedViewModel.index.value!!
        binding.tvQuizCurrent.text = index.toString()
//        if (index.plus(1) == args.quizKanji.size) {
//            val action = QuizFragmentDirections.actionQuizFragmentToResultFragment()
//            findNavController().navigate(action)
//        }
        val question = args.quizKanji[index].kanji
        val answer = args.quizKanji[index].kanjiMeaning
        val choice = (Data.kanji).let {
                data ->
            val list = data.map { it.kanjiMeaning }
            val listWithoutAnswer = list.minus(answer)
            val shuffledList = listWithoutAnswer.shuffled()
            val threeChoices = shuffledList.subList(0,3)
            val fourChoices = threeChoices.plus(answer)
            fourChoices.shuffled()
        }
        setQuiz(question,choice)
    }

    private fun setQuiz(question: String, choice: List<String>) {
        binding.tvQuizSize.text = args.quizKanji.size.toString()
        binding.tvAnswerList.text = sharedViewModel.answer.value.toString()
        binding.question.text = question
        binding.button.text = choice[0]
        binding.button2.text = choice[1]
        binding.button3.text = choice[2]
        binding.button4.text = choice[3]
    }

    override fun onClick(buttonView: View?) {
        val index = sharedViewModel.index.value!!
        val clickedBtn = buttonView as Button
        val buttonClickedColor = ContextCompat.getColor(requireContext(), R.color.sky_light_blue)
        binding.button.setBackgroundColor(0)
        binding.button2.setBackgroundColor(0)
        binding.button3.setBackgroundColor(0)
        binding.button4.setBackgroundColor(0)
        when (buttonView.id) {
            R.id.next_fab -> {
                if (answer.isNullOrBlank()) {
                    Toast.makeText(activity, "Please Select Your Answer", Toast.LENGTH_SHORT).show()
                } else {
                    checkAnswer()
                }
            }
            else -> {
                clickedBtn.setBackgroundColor(buttonClickedColor)
                answer = clickedBtn.text.toString()
            }
        }
    }

    private fun checkAnswer() {
        sharedViewModel.addAnswer(answer!!)
        Toast.makeText(context,sharedViewModel.answer.value.toString(),Toast.LENGTH_SHORT).show()
        answer = null
        sharedViewModel.plusIndex()
    }


    private fun initOnClickListener() {
        binding.button.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
        binding.button4.setOnClickListener(this)
        binding.nextFab.setOnClickListener(this)
        //close backButton press
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // With blank your fragment BackPressed will be disabled.
        }
    }
}