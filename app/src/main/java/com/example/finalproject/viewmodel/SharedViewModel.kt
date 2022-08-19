package com.example.finalproject.viewmodel

import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {
    private val _index = MutableLiveData(0)
    val index:LiveData<Int> = _index

    private val _correct = MutableLiveData<MutableList<String>>(mutableListOf())
    val correct:LiveData<MutableList<String>> = _correct

    private val _wrong = MutableLiveData<MutableList<String>>(mutableListOf())
    val wrong:LiveData<MutableList<String>> = _wrong


    fun plusIndex(){
        _index.value = _index.value!!.plus(1)
    }
    fun clearViewModel(){
        _index.value = 0
        _correct.value = mutableListOf()
        _wrong.value = mutableListOf()
    }
    fun addCorrect(string: String){
        _correct.value!!.add(string)
    }
    fun addWrong(string: String){
        _wrong.value!!.add(string)
    }


}