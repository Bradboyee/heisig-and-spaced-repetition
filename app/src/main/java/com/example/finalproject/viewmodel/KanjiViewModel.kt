package com.example.finalproject.viewmodel

import androidx.lifecycle.*
import com.cesarferreira.tempo.*
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class KanjiViewModel(private val repository: KanjiRepository):ViewModel() {
    private lateinit var todoDate: Date
    fun spacedKanji(): Flow<List<KanjiEntity>> = repository.getSpaced(Date())
    val allKanji: Flow<List<KanjiEntity>> = repository.allKanji




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

    fun updateCorrect(ArrayKanji: Array<KanjiEntity>){
        for(kanji in ArrayKanji){
            when(kanji.spacedStatus){
                0 -> todoDate = 1.day.forward
                1 -> todoDate = 3.day.forward
                2 -> todoDate = 1.week.forward
                3 -> todoDate = 1.month.forward
                4 -> todoDate = 6.months.forward
            }
            val status = kanji.spacedStatus + 1
            val newStatus = kanji.copy(spacedStatus = status, spacedDate = todoDate)
            update(newStatus)
        }
    }

    fun updateWrong(ArrayKanji: Array<KanjiEntity>){
        for(kanji in ArrayKanji){
            if(kanji.spacedStatus>0){
                val status = kanji.spacedStatus - 1
                val doDate = Tempo.now // if wrong reset to today
                val newStatus = kanji.copy(spacedStatus = status, spacedDate = doDate)
                update(newStatus)
            }
        }
    }

}