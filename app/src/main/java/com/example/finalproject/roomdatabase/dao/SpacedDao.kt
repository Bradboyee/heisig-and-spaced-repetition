package com.example.finalproject.roomdatabase.dao

import androidx.room.*
import androidx.room.Dao
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.example.finalproject.roomdatabase.roomentity.Story
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface SpacedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKanji(kanji: SpacedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: Story)

    @Update
    suspend fun updateStory(story: Story)

    @Update
    suspend fun updateKanji(kanji: SpacedEntity)

    @Delete
    suspend fun deleteStory(story: Story)

    @Delete
    suspend fun deleteKanji(kanji: SpacedEntity)

    @Query("SELECT count(*) FROM kanji_table WHERE kanji = :kanji")
    fun exist(kanji: String): Flow<Boolean>

    @Query("SELECT * FROM kanji_table WHERE spacedDate <=:targetDate")
    fun getSpacedTodo(targetDate: Date): Flow<List<SpacedEntity>>

    @Query("SELECT COUNT(*) FROM kanji_table WHERE spacedDate <=:targetDate")
    fun getSpacedNumber(targetDate: Date): Flow<Int>

    @Query("SELECT * FROM kanji_table ORDER BY spacedDate ASC")
    fun getAllKanji(): Flow<List<SpacedEntity>>

    @Query("SELECT kanjiMeaning FROM kanji_table")
    fun getMeaning():Flow<List<String>>

    @Transaction
    @Query("SELECT * FROM story WHERE kanji = :kanji")
    fun getStoryByKanji(kanji: String): Flow<List<Story>>

    @Query("SELECT * FROM kanji_table WHERE kanji = :kanji")
    fun getSpacedKanji(kanji: String):Flow<SpacedEntity>

    @Query("SELECT kanji FROM kanji_table WHERE grade = :grade")
    fun getAllCharacter(grade:Int):Flow<List<String>>

}