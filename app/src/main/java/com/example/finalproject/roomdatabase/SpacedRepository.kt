package com.example.finalproject.roomdatabase

import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject


class SpacedRepository@Inject constructor(private val spacedDao:SpacedDao) {
    val allKanji: Flow<List<SpacedEntity>> = spacedDao.getAllKanji()

    suspend fun insert(kanji:SpacedEntity){
        return spacedDao.insertKanji(kanji)
    }

    suspend fun delete(kanji:SpacedEntity){
        return spacedDao.deleteKanji(kanji)
    }

    suspend fun update(kanji: SpacedEntity){
        return spacedDao.updateKanji(kanji)
    }

    fun isExist(kanji: String): Int {
        return spacedDao.isExist(kanji)
    }

    fun getSpaced(date: Date): Flow<List<SpacedEntity>> {
        return spacedDao.getSpaced(date)
    }

    fun getSpacedNumber(date: Date): Flow<Int> {
        return spacedDao.getSpacedNumber(date)
    }

}