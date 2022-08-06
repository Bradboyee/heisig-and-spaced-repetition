package com.example.finalproject.roomdatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import java.util.*


class KanjiRepository(private val dao:Dao) {
    val getKanji = dao.getKanji()

    suspend fun insert(kanji:KanjiEntity){
        return dao.insertKanji(kanji)
    }

    suspend fun delete(kanji:KanjiEntity){
        return dao.deleteKanji(kanji)
    }

    suspend fun update(kanji: KanjiEntity){
        return dao.updateKanji(kanji)
    }

    fun getItemById(kanji: String): KanjiEntity {
        return dao.getItemById(kanji)
    }

    fun isExist(kanji: String): Int {
        return dao.isExist(kanji)
    }

    fun getSpaced(date: Date): LiveData<List<KanjiEntity>> {
        return dao.getSpaced(date)
    }




}