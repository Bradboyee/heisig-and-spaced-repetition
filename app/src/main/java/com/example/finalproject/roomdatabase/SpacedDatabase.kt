package com.example.finalproject.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [SpacedEntity::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class SpacedDatabase : RoomDatabase() {

    abstract fun spacedDao() : SpacedDao

//    companion object {
//        @Volatile
//        private var INSTANCE : KanjiDatabase? = null
//
//        fun getInstance(context: Context):KanjiDatabase {
//            synchronized(this){
//                var instance = INSTANCE
//                if (instance == null){
//                    instance = Room.databaseBuilder(context.applicationContext,
//                        KanjiDatabase::class.java,
//                        "testDatabase19")
//                        .build()
//                }
//                return instance
//            }
//        }
//    }

}