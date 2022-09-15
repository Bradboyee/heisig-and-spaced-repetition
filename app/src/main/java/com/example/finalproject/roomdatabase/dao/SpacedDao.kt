package com.example.finalproject.roomdatabase.dao

import androidx.room.*
import androidx.room.Dao
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.example.finalproject.roomdatabase.roomentity.Story
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface SpacedDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKanji(kanji: SpacedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: Story)

    @Update
    suspend fun updateStory(story: Story)

    @Delete
    suspend fun deleteStory(story: Story)

    @Update
    suspend fun updateKanji(kanji: SpacedEntity)

    @Delete
    suspend fun deleteKanji(kanji: SpacedEntity)

    @Query("SELECT count(*) FROM kanji_table WHERE kanji = :kanji")
    fun isExist(kanji: String): Int

    //Date
    @Query("SELECT * FROM kanji_table WHERE spacedDate <=:targetDate")
    fun getSpaced(targetDate: Date): Flow<List<SpacedEntity>>

    @Query("SELECT COUNT(*) FROM kanji_table WHERE spacedDate <=:targetDate")
    fun getSpacedNumber(targetDate: Date): Flow<Int>

    @Query("SELECT * FROM kanji_table ORDER BY spacedDate ASC")
    fun getAllKanji(): Flow<List<SpacedEntity>>

    @Transaction
    @Query("SELECT * FROM story WHERE kanji = :kanji")
    fun getStoryByKanji(kanji: String): Flow<List<Story>>


}