package com.example.finalproject.roomdatabase

import com.example.finalproject.roomdatabase.dao.SpacedDao
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.example.finalproject.roomdatabase.roomentity.Story
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject


class SpacedRepository@Inject constructor(private val spacedDao: SpacedDao) {
    val allKanji: Flow<List<SpacedEntity>> = spacedDao.getAllKanji()
    val allMeaning: Flow<List<String>> = spacedDao.getMeaning()

    suspend fun insert(kanji: SpacedEntity){
        return spacedDao.insertKanji(kanji)
    }

    suspend fun delete(kanji: SpacedEntity){
        return spacedDao.deleteKanji(kanji)
    }

    suspend fun deleteStory(story: Story){
        return spacedDao.deleteStory(story)
    }

    suspend fun update(kanji: SpacedEntity){
        return spacedDao.updateKanji(kanji)
    }

    fun getSpacedTodo(date: Date): Flow<List<SpacedEntity>> {
        return spacedDao.getSpacedTodo(date)
    }

    fun getSpacedNumber(date: Date): Flow<Int> {
        return spacedDao.getSpacedNumber(date)
    }

    suspend fun insertStory(story: Story){
        return spacedDao.insertStory(story)
    }

    suspend fun updateStory(story: Story){
        return spacedDao.updateStory(story)
    }

    fun getStoryByKanji(kanji: String): Flow<List<Story>> {
        return spacedDao.getStoryByKanji(kanji)
    }

    fun exist(kanji: String): Flow<Boolean> {
        return spacedDao.exist(kanji)
    }

    fun getSpacedKanji(kanji: String): Flow<SpacedEntity> {
        return spacedDao.getSpacedKanji(kanji)
    }

    fun getAllCharacter(grade:Int): Flow<List<String>> {
        return spacedDao.getAllCharacter(grade)
    }
}