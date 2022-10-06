package com.thepparat.heisigwithspacedrepetition.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thepparat.heisigwithspacedrepetition.retrofit.Resource
import com.thepparat.heisigwithspacedrepetition.retrofit.RepositoryKanjiAlive
import com.thepparat.heisigwithspacedrepetition.retrofit.pojo.PojoSingleKanjiDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelKanjiAlive @Inject constructor(private val diRepository: RepositoryKanjiAlive) :
    ViewModel() {

    val kanjiAlive = MutableLiveData<PojoSingleKanjiDetail?>()
    val errorMessage = MutableLiveData<String>()

    fun fetchRapid(kanji: String) {
        viewModelScope.launch {
            when (val response = diRepository.getSingleDetail(kanji = kanji)) {
                is Resource.Success -> {
                    kanjiAlive.postValue(response.data)
                }
                is Resource.Failure -> {
                    kanjiAlive.postValue(null)
                    errorMessage.postValue(response.message)
                }
            }
        }
    }
}