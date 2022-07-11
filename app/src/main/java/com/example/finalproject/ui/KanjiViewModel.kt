package com.example.finalproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class KanjiViewModel(private val repository: KanjiRepository):ViewModel() {
    val kanjiList = repository.getkanji

    fun insert(kanji:KanjiEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(kanji)
        }
    }

    fun update(kanji:KanjiEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(kanji)
        }
    }

    fun delete(kanji:KanjiEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(kanji)
        }
    }



}