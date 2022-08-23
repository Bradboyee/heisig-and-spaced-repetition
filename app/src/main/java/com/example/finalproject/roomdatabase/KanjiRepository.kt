package com.example.finalproject.roomdatabase

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.util.*


class KanjiRepository(private val dao:Dao) {
    val allKanji: Flow<List<KanjiEntity>> = dao.getAllKanji()

    suspend fun insert(kanji:KanjiEntity){
        return dao.insertKanji(kanji)
    }

    suspend fun delete(kanji:KanjiEntity){
        return dao.deleteKanji(kanji)
    }

    suspend fun update(kanji: KanjiEntity){
        return dao.updateKanji(kanji)
    }

    fun isExist(kanji: String): Int {
        return dao.isExist(kanji)
    }

    fun getSpaced(date: Date): Flow<List<KanjiEntity>> {
        return dao.getSpaced(date)
    }

    fun getSpacedNumber(date: Date): Flow<Int> {
        return dao.getSpacedNumber(date)
    }

}