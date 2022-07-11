package com.example.finalproject.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [KanjiEntity::class], version = 1)
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
                        "testdatabase4").build()
                }
                return instance
            }
        }
    }
}