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
                        "testDatabase18")
                        .build()
                }
                return instance
            }
        }
        fun deleteDatabaseFile(context: Context, databaseName: String) {
            val databases = File(context.applicationInfo.dataDir + "/databases")
            val db = File(databases, databaseName)
            if (db.delete()) println("Database deleted") else println("Failed to delete database")
            val journal = File(databases, "$databaseName-journal")
            if (journal.exists()) {
                if (journal.delete()) println("Database journal deleted") else println("Failed to delete database journal")
            }
        }
    }

}