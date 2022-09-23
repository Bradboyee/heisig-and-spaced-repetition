package com.example.finalproject.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.finalproject.data.Data
import com.example.finalproject.hilt.ApplicationScope
import com.example.finalproject.roomdatabase.converter.DateConverters
import com.example.finalproject.roomdatabase.dao.SpacedDao
import com.example.finalproject.roomdatabase.roomentity.SpacedEntity
import com.example.finalproject.roomdatabase.roomentity.Story
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [SpacedEntity::class,Story::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class SpacedDatabase : RoomDatabase() {

    abstract fun spacedDao() : SpacedDao

    class Callback @Inject constructor(private val spacedDatabase: Provider<SpacedDatabase>,@ApplicationScope private val applicationScope:CoroutineScope):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val spacedDao = spacedDatabase.get().spacedDao()
            val dummyStory = Data.dummyStory
            applicationScope.launch {
                dummyStory.map { spacedDao.insertStory(it) }
            }
        }
    }
}