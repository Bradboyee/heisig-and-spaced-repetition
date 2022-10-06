package com.thepparat.heisigwithspacedrepetition.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thepparat.heisigwithspacedrepetition.data.Data
import com.thepparat.heisigwithspacedrepetition.hilt.ApplicationScope
import com.thepparat.heisigwithspacedrepetition.roomdatabase.converter.DateConverters
import com.thepparat.heisigwithspacedrepetition.roomdatabase.dao.SpacedDao
import com.thepparat.heisigwithspacedrepetition.roomdatabase.roomentity.SpacedEntity
import com.thepparat.heisigwithspacedrepetition.roomdatabase.roomentity.Story
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