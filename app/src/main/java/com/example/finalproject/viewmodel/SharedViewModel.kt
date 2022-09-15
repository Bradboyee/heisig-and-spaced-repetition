package com.example.finalproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity

class SharedViewModel:ViewModel() {
    private val _index = MutableLiveData(0)
    val index:LiveData<Int> = _index

    private val _correct = MutableLiveData<MutableList<SpacedEntity>>(mutableListOf())
    val correct:LiveData<MutableList<SpacedEntity>> = _correct

    private val _wrong = MutableLiveData<MutableList<SpacedEntity>>(mutableListOf())
    val wrong:LiveData<MutableList<SpacedEntity>> = _wrong

    fun plusIndex(){
        _index.value = _index.value!!.plus(1)
    }
    fun clearViewModel(){
        _index.value = 0
        _correct.value = mutableListOf()
        _wrong.value = mutableListOf()
    }
    fun addCorrect(spacedEntity: SpacedEntity){
        _correct.value!!.add(spacedEntity)
    }
    fun addWrong(spacedEntity: SpacedEntity){
        _wrong.value!!.add(spacedEntity)
    }


}