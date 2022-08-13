package com.example.finalproject.roomdatabase

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.util.*


class KanjiRepository(private val dao:Dao) {
    val getKanji = dao.getKanji()
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

    fun getItemById(kanji: String): KanjiEntity {
        return dao.getItemById(kanji)
    }

    fun isExist(kanji: String): Int {
        return dao.isExist(kanji)
    }

    fun getSpaced(date: Date): Flow<List<KanjiEntity>> {
        return dao.getSpaced(date)
    }

    fun getArrayListSpaced(date: Date): Flow<Array<KanjiEntity>> {
        return dao.getArrayListSpaced(date)
    }




}