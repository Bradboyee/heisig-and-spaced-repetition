package com.example.finalproject.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Insert
    suspend fun insertkanji(kanji : KanjiEntity)

    @Update
    suspend fun updatekanji(kanji : KanjiEntity)

    @Delete
    suspend fun deletekanji(kanji : KanjiEntity)

    @Query("SELECT * FROM kanji_table")
    fun getkanji() : LiveData<List<KanjiEntity>>

    @Query("SELECT KanjiMeaning FROM kanji_table")
    fun getAllMeaning(): List<String>
}