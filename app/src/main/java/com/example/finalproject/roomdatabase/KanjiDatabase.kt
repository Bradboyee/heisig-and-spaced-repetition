package com.example.finalproject.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import java.io.File

@Database(entities = [KanjiEntity::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class KanjiDatabase : RoomDatabase() {

    abstract fun dao() : Dao

    companion object {
        @Volatile
        private var INSTANCE : KanjiDatabase? = null

        fun getInstance(context: Context):KanjiDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        KanjiDatabase::class.java,
                        "testDatabase19")
                        .build()
                }
                return instance
            }
        }
    }

}