package com.example.finalproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.roomdatabase.KanjiEntity

class SharedViewModel:ViewModel() {
    private val _index = MutableLiveData(0)
    val index:LiveData<Int> = _index

    private val _correct = MutableLiveData<MutableList<KanjiEntity>>(mutableListOf())
    val correct:LiveData<MutableList<KanjiEntity>> = _correct

    private val _wrong = MutableLiveData<MutableList<KanjiEntity>>(mutableListOf())
    val wrong:LiveData<MutableList<KanjiEntity>> = _wrong

    fun plusIndex(){
        _index.value = _index.value!!.plus(1)
    }
    fun clearViewModel(){
        _index.value = 0
        _correct.value = mutableListOf()
        _wrong.value = mutableListOf()
    }
    fun addCorrect(kanjiEntity: KanjiEntity){
        _correct.value!!.add(kanjiEntity)
    }
    fun addWrong(kanjiEntity: KanjiEntity){
        _wrong.value!!.add(kanjiEntity)
    }


}