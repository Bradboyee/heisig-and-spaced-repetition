package com.example.finalproject.ui

import androidx.lifecycle.*
import com.cesarferreira.tempo.*
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class KanjiViewModel(private val repository: KanjiRepository):ViewModel() {
    lateinit var todoDate: Date
    val kanjiList = repository.getKanji
    val spacedKanji = repository.getSpaced(Date())
    val currentQuestion = MutableLiveData<Int>(0)

    val submitCorrectAnswer : MutableLiveData<List<String>> = MutableLiveData(listOf())
    val submitWrongAnswer : MutableLiveData<List<String>> = MutableLiveData(listOf())


    fun insert(kanji:KanjiEntity){
        viewModelScope.launch(Dispatchers.IO){
            val rowCount = repository.isExist(kanji.kanji)
            if (rowCount==0){
                repository.insert(kanji)
            }
        }
    }

    private fun update(kanji:KanjiEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(kanji)
        }
    }

    fun delete(kanji:KanjiEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(kanji)
        }
    }

    //Update Spaced function

    private fun updateCorrect(kanji: KanjiEntity){
        when(kanji.spacedStatus){
            0 -> todoDate = 1.day.forward
            1 -> todoDate = 3.day.forward
            2 -> todoDate = 1.week.forward
        }
        val status = kanji.spacedStatus + 1
        val newStatus = kanji.copy(spacedStatus = status, spacedDate = todoDate)
        update(newStatus)
    }

    private fun updateWrong(kanji: KanjiEntity){
        if(kanji.spacedStatus>0){
            val status = kanji.spacedStatus - 1
            val doDate = Tempo.now // if wrong reset to today
            val newStatus = kanji.copy(spacedStatus = status, spacedDate = doDate)
            update(newStatus)
        }
    }

    fun updateCorrectByList(arrayList: ArrayList<String>){
        for (item in arrayList){
            val updateObject = kanjiList.value!!.find { it.kanji == item }
            updateCorrect(updateObject!!)
        }
    }

    fun updateWrongByList(arrayList: ArrayList<String>){
        for (item in arrayList){
            val updateObject = kanjiList.value!!.find { it.kanji == item }
            updateWrong(updateObject!!)
        }
    }

    // Quiz function
    fun getQuestion(): List<String> {
        val data = spacedKanji.value!!.filter { it.spacedDate.before(Date()) }
        return data.map { it.kanji }
    }
    fun getAnswer(): List<String> {
        val data = spacedKanji.value!!.filter { it.spacedDate.before(Date()) }
        return data.map{ it.kanjiMeaning }
    }

    fun addCorrectAnswer(answer:String){
        submitCorrectAnswer.value = submitCorrectAnswer.value!!.plus(answer)
    }
    fun addWrongAnswer(answer:String){
        submitWrongAnswer.value = submitWrongAnswer.value!!.plus(answer)
    }

}