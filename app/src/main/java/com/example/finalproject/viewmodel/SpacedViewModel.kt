package com.example.finalproject.viewmodel

import androidx.lifecycle.*
import com.cesarferreira.tempo.*
import com.example.finalproject.roomdatabase.SpacedEntity
import com.example.finalproject.roomdatabase.SpacedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SpacedViewModel @Inject constructor(private val repository: SpacedRepository) : ViewModel() {
    private lateinit var todoDate: Date
    fun spacedKanji(): Flow<List<SpacedEntity>> = repository.getSpaced(Tempo.now)
    val allKanji: Flow<List<SpacedEntity>> = repository.allKanji
    val spacedNumber: Flow<Int> = repository.getSpacedNumber(Tempo.now)

    fun insert(kanji: SpacedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val rowCount = repository.isExist(kanji.kanji)
            if (rowCount == 0) {
                repository.insert(kanji)
            }
        }
    }

    private fun update(kanji: SpacedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(kanji)
        }
    }

    fun delete(kanji: SpacedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(kanji)
        }
    }

    //Update Spaced function

    fun updateCorrect(ArrayKanji: Array<SpacedEntity>) {
        for (kanji in ArrayKanji) {
            when (kanji.spacedStatus) {
                0 -> todoDate = 1.day.forward
                1 -> todoDate = 3.day.forward
                2 -> todoDate = 1.week.forward
                3 -> todoDate = 30.day.forward
                4 -> todoDate = 6.months.forward
            }
            val status = kanji.spacedStatus + 1
            val newStatus = kanji.copy(spacedStatus = status, spacedDate = todoDate)
            update(newStatus)
        }
    }

    fun updateWrong(ArrayKanji: Array<SpacedEntity>) {
        for (kanji in ArrayKanji) {
            if (kanji.spacedStatus > 0) {
                val status = kanji.spacedStatus - 1
                val doDate = Tempo.now // if wrong reset to today
                val newStatus = kanji.copy(spacedStatus = status, spacedDate = doDate)
                update(newStatus)
            } else if (kanji.spacedStatus == 0) {
                val doDate = 1.day.forward // if wrong reset to today
                val newStatus = kanji.copy(spacedDate = doDate)
                update(newStatus)
            }
        }
    }

}