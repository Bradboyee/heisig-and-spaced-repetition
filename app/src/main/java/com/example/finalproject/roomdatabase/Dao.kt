package com.example.finalproject.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import java.util.*

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKanji(kanji : KanjiEntity)

    @Update
    suspend fun updateKanji(kanji : KanjiEntity)

    @Delete
    suspend fun deleteKanji(kanji : KanjiEntity)

    @Query("SELECT * FROM kanji_table")
    fun getKanji() : LiveData<List<KanjiEntity>>

    //

    @Query("SELECT * FROM kanji_table WHERE kanji = :kanji")
    fun getItemById(kanji: String): KanjiEntity

    @Query("SELECT count(*) FROM kanji_table WHERE kanji = :kanji")
    fun isExist(kanji: String): Int

    //Date
    @Query("SELECT * FROM kanji_table WHERE spacedDate <=:targetDate")
    fun getSpaced(targetDate: Date): LiveData<List<KanjiEntity>>


}