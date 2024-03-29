package com.thepparat.heisigwithspacedrepetition.viewmodel

import androidx.lifecycle.*
import com.cesarferreira.tempo.*
import com.thepparat.heisigwithspacedrepetition.roomdatabase.roomentity.SpacedEntity
import com.thepparat.heisigwithspacedrepetition.roomdatabase.SpacedRepository
import com.thepparat.heisigwithspacedrepetition.roomdatabase.roomentity.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SpacedViewModel @Inject constructor(private val repository: SpacedRepository) : ViewModel() {
    private lateinit var todoDate: Date
    fun getSpacedTodo(): Flow<List<SpacedEntity>> = repository.getSpacedTodo(Tempo.now)
    val allKanji: Flow<List<SpacedEntity>> = repository.allKanji
    val allMeaning: LiveData<List<String>> = repository.allMeaning
    val spacedNumber: Flow<Int> = repository.getSpacedNumber(Tempo.now)
    fun story(kanji: String): Flow<List<Story>> = repository.getStoryByKanji(kanji)
    fun exist(kanji: String): Flow<Boolean> = repository.exist(kanji)
    fun getSpacedKanji(kanji: String):Flow<SpacedEntity> = repository.getSpacedKanji(kanji)
    fun getAllCharacter(grade:Int):Flow<List<String>> = repository.getAllCharacter(grade)

    fun insertSpaced(kanji: SpacedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(kanji)
        }
    }


    fun insertStory(story: Story) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertStory(story)
        }
    }

    private fun updateSpaced(kanji: SpacedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(kanji)
        }
    }

    fun updateStory(story: Story) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStory(story)
        }
    }

    fun deleteSpaced(kanji: SpacedEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(kanji)
        }
    }

    fun deleteStory(story: Story) {
        viewModelScope.launch(Dispatchers.IO) {
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
            val newCorrect = kanji.status.correct + 1
            val newStatus = kanji.status.copy(spacedStatus = status,
                spacedDate = todoDate,
                correct = newCorrect)
            val newSpaced = kanji.copy(status = newStatus)
            updateSpaced(newSpaced)
        }
    }

    fun updateWrong(ArrayKanji: Array<SpacedEntity>) {
        for (kanji in ArrayKanji) {
            val newWrong = kanji.status.wrong + 1
            if (kanji.status.spacedStatus > 0) {
                val status = kanji.status.spacedStatus - 1
                val doDate = Tempo.now // if wrong reset to today
                val newStatus =
                    kanji.status.copy(spacedStatus = status, spacedDate = doDate, wrong = newWrong)
                val newSpaced = kanji.copy(status = newStatus)
                updateSpaced(newSpaced)
            } else if (kanji.status.spacedStatus == 0) {
                val doDate = 1.day.forward // if wrong reset to today
                val newStatus = kanji.status.copy(spacedDate = doDate, wrong = newWrong)
                val newSpaced = kanji.copy(status = newStatus)
                updateSpaced(newSpaced)
            }
        }
    }

}
