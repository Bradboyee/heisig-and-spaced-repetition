package com.example.finalproject.viewmodel

import androidx.lifecycle.*
import com.cesarferreira.tempo.*
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.example.finalproject.roomdatabase.SpacedRepository
import com.example.finalproject.roomdatabase.roomentity.Story
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
    fun story(kanji:String) : Flow<List<Story>> = repository.getStoryByKanji(kanji)

    fun insert(kanji: SpacedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val rowCount = repository.isExist(kanji.kanji)
            if (rowCount == 0) {
                repository.insert(kanji)
            }
        }
    }


    fun insertStory(story: Story){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertStory(story)
        }
    }

    private fun update(kanji: SpacedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(kanji)
        }
    }

    fun updateStory(story: Story){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateStory(story)
        }
    }

    fun delete(kanji: SpacedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(kanji)
        }
    }

    fun delete(story: Story){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteStory(story)
        }
    }

    //Update Spaced function

    fun updateCorrect(ArrayKanji: Array<SpacedEntity>) {
        for (kanji in ArrayKanji) {
            when (kanji.status.spacedStatus) {
                0 -> todoDate = 1.day.forward
                1 -> todoDate = 3.day.forward
                2 -> todoDate = 1.week.forward
                3 -> todoDate = 30.day.forward
                4 -> todoDate = 6.months.forward
            }
            val status = kanji.status.spacedStatus + 1
            val newStatus = kanji.status.copy(spacedStatus = status, spacedDate = todoDate)
            val newSpaced = kanji.copy(status = newStatus)
            update(newSpaced)
        }
    }

    fun updateWrong(ArrayKanji: Array<SpacedEntity>) {
        for (kanji in ArrayKanji) {
            if (kanji.status.spacedStatus > 0) {
                val status = kanji.status.spacedStatus - 1
                val doDate = Tempo.now // if wrong reset to today
                val newStatus = kanji.status.copy(spacedStatus = status, spacedDate = doDate)
                val newSpaced = kanji.copy(status = newStatus)
                update(newSpaced)
            } else if (kanji.status.spacedStatus == 0) {
                val doDate = 1.day.forward // if wrong reset to today
                val newStatus = kanji.status.copy(spacedDate = doDate)
                val newSpaced = kanji.copy(status = newStatus)
                update(newSpaced)
            }
        }
    }

}
