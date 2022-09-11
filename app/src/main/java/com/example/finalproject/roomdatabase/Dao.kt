package com.example.finalproject.roomdatabase

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import java.util.*
@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKanji(kanji : KanjiEntity)

    @Update
    suspend fun updateKanji(kanji : KanjiEntity)

    @Delete
    suspend fun deleteKanji(kanji : KanjiEntity)

    @Query("SELECT count(*) FROM kanji_table WHERE kanji = :kanji")
    fun isExist(kanji: String): Int

    //Date
    @Query("SELECT * FROM kanji_table WHERE spacedDate <=:targetDate")
    fun getSpaced(targetDate: Date): Flow<List<KanjiEntity>>

    @Query("SELECT COUNT(*) FROM kanji_table WHERE spacedDate <=:targetDate")
    fun getSpacedNumber(targetDate: Date): Flow<Int>

    @Query("SELECT * FROM kanji_table ORDER BY spacedDate ASC")
    fun getAllKanji(): Flow<List<KanjiEntity>>


}