package com.example.finalproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.roomdatabase.KanjiRepository

class KanjiViewModelFactory(private val repository: KanjiRepository):ViewModelProvider.Factory { // add kotlinOptions
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KanjiViewModel::class.java)){
            return KanjiViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}