package com.example.finalproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {
    private val _index = MutableLiveData(0)
    val index:LiveData<Int> = _index

    private val _answer = MutableLiveData<MutableList<String>>(mutableListOf())
    val answer:LiveData<MutableList<String>> = _answer


    fun plusIndex(){
        _index.value = _index.value!!.plus(1)
    }
    fun clearViewModel(){
        _index.value = 0
        _answer.value = mutableListOf()
    }
    fun addAnswer(string: String){
        _answer.value!!.add(string)
    }


}