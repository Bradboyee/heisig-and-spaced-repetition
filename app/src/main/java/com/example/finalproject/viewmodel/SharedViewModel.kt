package com.example.finalproject.viewmodel

import android.widget.Button
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

    private val _quantity = MutableLiveData<Int>(7)
    val quantity: LiveData<Int> = _quantity

    fun plusQuantity(){
        _quantity.value = _quantity.value!!.plus(1)
    }

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