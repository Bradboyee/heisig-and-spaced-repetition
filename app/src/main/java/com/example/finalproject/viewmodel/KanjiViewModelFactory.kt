package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.roomdatabase.SpacedRepository

@Suppress("UNCHECKED_CAST")
class KanjiViewModelFactory(private val repository: SpacedRepository) :
    ViewModelProvider.Factory { // add kotlinOptions
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpacedViewModel::class.java)) {
            return SpacedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}