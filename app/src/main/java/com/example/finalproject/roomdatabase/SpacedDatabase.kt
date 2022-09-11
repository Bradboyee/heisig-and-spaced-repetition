package com.example.finalproject.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalproject.roomdatabase.converter.DateConverters

@Database(entities = [SpacedEntity::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class SpacedDatabase : RoomDatabase() {

    abstract fun spacedDao() : SpacedDao

}